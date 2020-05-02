package io.github.thebusybiscuit.slimefun4.core.recipes.contexts;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeContext;
import io.github.thebusybiscuit.slimefun4.api.recipes.SlimefunRecipe;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;

/**
 * This {@link RecipeContext} represents a {@link Player} browsing through his
 * {@link SlimefunGuide} to view a {@link SlimefunRecipe}.
 * 
 * @author TheBusyBiscuit
 * 
 * @see RecipeContext
 * @see SlimefunRecipe
 *
 */
public class SlimefunGuideContext implements RecipeContext {

    private final NamespacedKey key;
    private final Player player;

    /**
     * This constructs a new {@link SlimefunGuideContext} for the given {@link Player}.
     * 
     * @param p
     *            The {@link Player} trying to view a {@link SlimefunRecipe}
     */
    public SlimefunGuideContext(Player p) {
        this.key = new NamespacedKey(SlimefunPlugin.instance, "slimefun_guide");
        this.player = p;
    }

    /**
     * This method returns the {@link Player} who is trying to view a {@link SlimefunRecipe}
     * in this {@link SlimefunGuideContext}.
     * 
     * @return The {@link Player} who is trying to view a {@link SlimefunRecipe}
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

}
