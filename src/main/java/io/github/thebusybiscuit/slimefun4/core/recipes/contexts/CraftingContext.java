package io.github.thebusybiscuit.slimefun4.core.recipes.contexts;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeContext;
import io.github.thebusybiscuit.slimefun4.api.recipes.SlimefunRecipe;

/**
 * This class represents a {@link RecipeContext} in which an {@link ItemStack} was crafted
 * using a machine.
 * 
 * @author TheBusyBiscuit
 * 
 * @see SlimefunRecipe
 * @see RecipeContext
 *
 */
public class CraftingContext implements RecipeContext {

    private final NamespacedKey key;
    private final ItemStack[] inputs;

    /**
     * This constructs a new {@link CraftingContext} with the given parameters.
     * 
     * @param machineKey
     *            The {@link NamespacedKey} of the machine that was used to craft the item
     * @param inputs
     *            The {@link ItemStack ItemStacks} used in the crafting process
     */
    public CraftingContext(NamespacedKey machineKey, ItemStack... inputs) {
        this.key = machineKey;
        this.inputs = inputs;
    }

    /**
     * This method returns the {@link ItemStack ItemStacks} that were used as the recipe inputs
     * in this {@link CraftingContext}.
     * 
     * @return The used items in this crafting operation
     */
    public ItemStack[] getInputs() {
        return inputs;
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

}
