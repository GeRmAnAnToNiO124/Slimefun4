package io.github.thebusybiscuit.slimefun4.api.recipes;

import java.util.function.Predicate;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

class ItemPredicate implements Predicate<ItemStack> {

    private final ItemStack[] items;

    ItemPredicate(ItemStack[] items) {
        this.items = new ItemStack[items.length];

        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];

            if (item instanceof SlimefunItemStack) {
                // SlimefunItemStacks are already optimized and carry a Slimefun item id.
                // So we just store them as they are.
                this.items[i] = item;
            }
            else {
                // Normal ItemStacks are unoptimized so we create a wrapper for them which
                // speeds up .getItemMeta() calls by caching the ItemMeta.
                this.items[i] = new ItemStackWrapper(item);
            }
        }
    }

    @Override
    public boolean test(ItemStack subject) {
        if (subject == null || subject.getType() == Material.AIR) {
            return false;
        }

        for (ItemStack item : items) {
            if (SlimefunUtils.isItemSimilar(subject, item, true, false)) {
                // Return true if any of the items matched
                return true;
            }
        }

        return false;
    }

}
