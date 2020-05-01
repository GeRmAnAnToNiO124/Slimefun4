package io.github.thebusybiscuit.slimefun4.api.recipes;

import java.util.function.Predicate;

import org.bukkit.inventory.ItemStack;

/**
 * A {@link RecipePredicate} is a {@link Predicate} that checks an Array of {@link ItemStack ItemStacks} against
 * an Array of {@link Ingredient Ingredients}.
 * 
 * It is used to check whether a given {@link ItemStack} Array matches the Recipe this {@link Predicate}
 * represents.
 * 
 * @author TheBusyBiscuit
 * 
 * @see Ingredient
 *
 */
public class RecipePredicate implements Predicate<ItemStack[]> {

    private final Predicate<ItemStack>[] predicates;

    /**
     * This constructs a new {@link RecipePredicate} from the given {@link Ingredient Ingredients}.
     * 
     * @param ingredients
     *            The {@link Ingredient} Array to use for this {@link RecipePredicate}
     */
    public RecipePredicate(Ingredient... ingredients) {
        this.predicates = ingredients;
    }

    @Override
    public boolean test(ItemStack[] items) {
        if (items.length != predicates.length) {
            return false;
        }

        for (int i = 0; i < items.length; i++) {
            if (!predicates[i].test(items[i])) {
                return false;
            }
        }

        return true;
    }

}
