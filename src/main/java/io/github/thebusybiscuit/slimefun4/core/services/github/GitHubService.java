package io.github.thebusybiscuit.slimefun4.core.services.github;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.github.thebusybiscuit.slimefun4.core.services.localization.Translators;
import io.github.thebusybiscuit.slimefun4.utils.NumberUtils;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;

/**
 * This Service is responsible for grabbing every {@link Contributor} to this project
 * from GitHub and holding data associated to the project repository, such
 * as open issues or pending pull requests.
 * 
 * @author TheBusyBiscuit
 *
 */
public class GitHubService {

    private final String repository;
    private final Set<GitHubConnector> connectors;
    private final ConcurrentMap<String, Contributor> contributors;

    private boolean logging = false;

    private int issues = 0;
    private int pullRequests = 0;
    private int forks = 0;
    private int stars = 0;
    private LocalDateTime lastUpdate = LocalDateTime.now();

    public GitHubService(String repository) {
        this.repository = repository;

        connectors = new HashSet<>();
        contributors = new ConcurrentHashMap<>();
        loadConnectors(false);
    }

    public void start(SlimefunPlugin plugin) {
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new GitHubTask(this), 80L, 60 * 60 * 20L);
    }

    private void addDefaultContributors() {
        addContributor("Fuffles_", "&dArtist");
        addContributor("IMS_Art", "&dArtist");
        addContributor("nahkd123", "&aWinner of the 2020 Addon Jam");

        new Translators(contributors);
    }

    private void addContributor(String name, String role) {
        Contributor contributor = new Contributor(name);
        contributor.setContribution(role, 0);
        contributors.put(name, contributor);
    }

    private void loadConnectors(boolean logging) {
        this.logging = logging;
        addDefaultContributors();

        // TheBusyBiscuit/Slimefun4 (twice because there may me multiple pages)
        connectors.add(new ContributionsConnector(this, "code", 1, repository, "developer"));
        connectors.add(new ContributionsConnector(this, "code2", 2, repository, "developer"));

        // TheBusyBiscuit/Slimefun4-Wiki
        connectors.add(new ContributionsConnector(this, "wiki", 1, "TheBusyBiscuit/Slimefun4-wiki", "wiki"));

        // TheBusyBiscuit/Slimefun4-Resourcepack
        connectors.add(new ContributionsConnector(this, "resourcepack", 1, "TheBusyBiscuit/Slimefun4-Resourcepack", "resourcepack"));

        // Issues and Pull Requests
        connectors.add(new GitHubIssuesTracker(this, repository, (issues, pullRequests) -> {
            this.issues = issues;
            this.pullRequests = pullRequests;
        }));

        connectors.add(new GitHubConnector(this, repository) {

            @Override
            public void onSuccess(JsonElement element) {
                JsonObject object = element.getAsJsonObject();
                forks = object.get("forks").getAsInt();
                stars = object.get("stargazers_count").getAsInt();
                lastUpdate = NumberUtils.parseGitHubDate(object.get("pushed_at").getAsString());
            }

            @Override
            public String getFileName() {
                return "repo";
            }

            @Override
            public String getURLSuffix() {
                return "";
            }
        });
    }

    public Set<GitHubConnector> getConnectors() {
        return connectors;
    }

    public ConcurrentMap<String, Contributor> getContributors() {
        return contributors;
    }

    public int getForks() {
        return forks;
    }

    public int getStars() {
        return stars;
    }

    public int getIssues() {
        return issues;
    }

    public int getPullRequests() {
        return pullRequests;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public boolean isLoggingEnabled() {
        return logging;
    }
}
