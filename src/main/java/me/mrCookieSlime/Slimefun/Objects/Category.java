package me.mrCookieSlime.Slimefun.Objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.cscorelib2.item.CustomItem;
import io.github.thebusybiscuit.slimefun4.core.categories.SeasonalCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.Slimefun;

/**
 * Represents a category, which structure multiple {@link SlimefunItem} in the {@link SlimefunGuide}.
 * 
 * @author TheBusyBiscuit
 *
 * @see LockedCategory
 * @see SeasonalCategory
 * 
 */
public class Category implements Keyed {

    protected final List<SlimefunItem> items = new ArrayList<>();
    protected final NamespacedKey key;
    protected final ItemStack item;
    protected final int tier;

    /**
     * Constructs a new {@link Category} with the given {@link NamespacedKey} as an identifier
     * and the given {@link ItemStack} as its display item.
     * The tier is set to a default value of {@code 3}.
     * 
     * @param key
     *            The {@link NamespacedKey} that is used to identify this {@link Category}
     * @param item
     *            The {@link ItemStack} that is used to display this {@link Category}
     */
    public Category(NamespacedKey key, ItemStack item) {
        this(key, item, 3);
    }

    /**
     * Constructs a new {@link Category} with the given {@link NamespacedKey} as an identifier
     * and the given {@link ItemStack} as its display item.
     * 
     * @param key
     *            The {@link NamespacedKey} that is used to identify this {@link Category}
     * @param item
     *            The {@link ItemStack} that is used to display this {@link Category}
     * @param tier
     *            The tier of this {@link Category}, higher tiers will make this {@link Category} appear further down in
     *            the {@link SlimefunGuide}
     */
    public Category(NamespacedKey key, ItemStack item, int tier) {
        this.item = item;
        this.key = key;

        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.item.setItemMeta(meta);
        this.tier = tier;
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

    /**
     * Registers this category.
     * <p>
     * By default, a category is automatically registered when a {@link SlimefunItem} was added to it.
     */
    public void register() {
        SlimefunPlugin.getRegistry().getCategories().add(this);
        Collections.sort(SlimefunPlugin.getRegistry().getCategories(), Comparator.comparingInt(Category::getTier));
    }

    /**
     * Adds the given {@link SlimefunItem} to this {@link Category}.
     * 
     * @param item
     *            the {@link SlimefunItem} that should be added to this {@link Category}
     */
    public void add(SlimefunItem item) {
        items.add(item);
    }

    /**
     * Removes the given {@link SlimefunItem} from this {@link Category}.
     * 
     * @param item
     *            the {@link SlimefunItem} that should be removed from this {@link Category}
     */
    public void remove(SlimefunItem item) {
        items.remove(item);
    }

    /**
     * This method returns a localized display item of this {@link Category}
     * for the specified {@link Player}.
     * 
     * @param p
     *            The Player to create this {@link ItemStack} for
     * @return A localized display item for this {@link Category}
     */
    public ItemStack getItem(Player p) {
        return new CustomItem(item, meta -> {
            String name = SlimefunPlugin.getLocal().getCategoryName(p, getKey());
            if (name == null) name = item.getItemMeta().getDisplayName();

            if (this instanceof SeasonalCategory) {
                meta.setDisplayName(ChatColor.GOLD + name);
            }
            else {
                meta.setDisplayName(ChatColor.YELLOW + name);
            }

            meta.setLore(Arrays.asList("", ChatColor.GRAY + "\u21E8 " + ChatColor.GREEN + SlimefunPlugin.getLocal().getMessage(p, "guide.tooltips.open-category")));
        });
    }

    /**
     * This method makes Walshy happy.
     * It adds a way to get the name of a {@link Category} without localization nor coloring.
     * 
     * @return The unlocalized name of this {@link Category}
     */
    public String getUnlocalizedName() {
        return ChatColor.stripColor(item.getItemMeta().getDisplayName());
    }

    /**
     * Returns all instances of {@link SlimefunItem} bound to this {@link Category}.
     * 
     * @return the list of SlimefunItems bound to this category
     */
    public List<SlimefunItem> getItems() {
        return items;
    }

    /**
     * Returns the tier of this {@link Category}.
     * The tier determines the position of this {@link Category} in the {@link SlimefunGuide}.
     * 
     * @return the tier of this {@link Category}
     */
    public int getTier() {
        return tier;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" + key + ",tier=" + tier + "}";
    }

    /**
     * This method checks whether this {@link Category} will be hidden for the specified
     * {@link Player}.
     * 
     * Categories are hidden if all of their items have been disabled.
     * 
     * @param p
     *            The {@link Player} to check for
     * @return Whether this {@link Category} will be hidden to the given {@link Player}
     */
    public boolean isHidden(Player p) {
        for (SlimefunItem slimefunItem : getItems()) {
            if (!slimefunItem.isHidden() && Slimefun.isEnabled(p, slimefunItem, false)) {
                return false;
            }
        }

        return true;
    }

}
