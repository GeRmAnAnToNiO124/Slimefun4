package io.github.thebusybiscuit.slimefun4.api.recipes;

import java.util.function.Predicate;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

class ItemPredicate implements Predicate<ItemStack> {

    private final ItemStack[] items;

    public ItemPredicate(ItemStack[] items) {
        this.items = new ItemStack[items.length];

        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];

            if (item instanceof SlimefunItemStack) {
                this.items[i] = item;
            }
            else {
                this.items[i] = new ItemStackWrapper(item);
            }
        }
    }

    @Override
    public boolean test(ItemStack subject) {
        for (ItemStack item : items) {
            if (SlimefunUtils.isItemSimilar(subject, item, true, false)) {
                return true;
            }
        }

        return false;
    }

}
