package io.github.thebusybiscuit.slimefun4.api.recipes;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.core.recipes.contexts.CraftingContext;

/**
 * This is the super class for all Slimefun Recipes.
 * It is an extension of {@link RecipePredicate} and has one abstract method: {@link #getResult(ItemStack[])}
 * which determines the result of this {@link SlimefunRecipe} which may change based on the provided context.
 * 
 * @author TheBusyBiscuit
 * 
 * @see RecipePredicate
 * @see Ingredient
 *
 */
public abstract class SlimefunRecipe extends RecipePredicate {

    /**
     * This constructs a new {@link SlimefunRecipe} from the given {@link Ingredient Ingredients}.
     * 
     * @param ingredients
     *            The {@link Ingredient} Array to use for this {@link SlimefunRecipe}
     */
    protected SlimefunRecipe(Ingredient... ingredients) {
        super(ingredients);
    }

    /**
     * This method returns a mutable array of the {@link Ingredient Ingredients} used in
     * this {@link SlimefunRecipe}.
     * 
     * @return The {@link Ingredient Ingredients} used in this recipe
     */
    public Ingredient[] getIngredients() {
        return ingredients;
    }

    /**
     * This method returns the result of this crafting operation.
     * It also takes the input items as a parameter in case the result may dependent on
     * what items were used (e.g. Backpacks).
     * 
     * This method should never return null. If the {@link RecipeContext} is not a {@link CraftingContext}
     * then a default ItemStack must be returned.
     * 
     * @param context
     *            The {@link RecipeContext} in which the result was requested,
     *            whether it was just a query or an actual crafting process
     * 
     * @return The resulting {@link ItemStack ItemStacks}
     */
    public abstract ItemStack getResult(RecipeContext context);

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + ingredients.length + " Ingredients)";
    }

}
