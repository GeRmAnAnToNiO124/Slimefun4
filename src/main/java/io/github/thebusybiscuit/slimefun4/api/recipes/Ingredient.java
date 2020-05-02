package io.github.thebusybiscuit.slimefun4.api.recipes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;
import org.bukkit.inventory.meta.ItemMeta;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * An {@link Ingredient} is a form of {@link Predicate} that tests an {@link ItemStack}.
 * It can be created from a {@link MaterialChoice} or a Set of {@link ItemStack ItemStacks},
 * this {@link Ingredient} will compile the given arguments into a fast {@link Predicate} which
 * also checks for a matching {@link SlimefunItem} if given a {@link SlimefunItemStack}.
 * 
 * Objects of this class are used for the {@link RecipePredicate}.
 * 
 * @author TheBusyBiscuit
 * 
 * @see RecipePredicate
 *
 */
public class Ingredient implements Predicate<ItemStack> {

    private static final Ingredient emptyIngredient = new Ingredient();

    private final Predicate<ItemStack> predicate;
    private final Collection<ItemStack> variations;

    /**
     * Private constructor used for initializing the emptyIngredient field.
     */
    private Ingredient() {
        this.predicate = item -> item == null || item.getType() == Material.AIR;
        this.variations = Collections.emptyList();
    }

    /**
     * This {@link Ingredient} will compile an appropriate {@link Predicate} from the given
     * {@link MaterialChoice}.
     * 
     * @param choice
     *            The {@link MaterialChoice} to use
     */
    public Ingredient(MaterialChoice choice) {
        Validate.notNull(choice, "MaterialChoice may not be null!");

        this.predicate = item -> {
            if (item == null || item.getType() == Material.AIR) {
                return false;
            }
            else if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();

                if (meta.hasDisplayName() || meta.hasLore()) {
                    return false;
                }
            }

            return choice.test(item);
        };

        this.variations = choice.getChoices().stream().map(ItemStack::new).collect(Collectors.toSet());
    }

    /**
     * This {@link Ingredient} will compile a {@link Predicate} from the given {@link Material} array.
     * This implementation however will just simply create a {@link MaterialChoice} and pass it to the other
     * constructor.
     * 
     * @param materials
     *            An Array of {@link Material Materials} to check
     */
    public Ingredient(Material... materials) {
        this(new MaterialChoice(materials));
    }

    /**
     * This takes an Array of {@link ItemStack ItemStacks} to create a {@link Predicate}.
     * Should this Array contain any {@link SlimefunItemStack} the {@link Predicate} will check
     * for the item id.
     * 
     * @param items
     *            The items to use
     */
    public Ingredient(ItemStack... items) {
        Validate.notEmpty(items, "An Ingredient must not be empty, use 'Ingredient.empty()' instead!");

        this.predicate = new ItemPredicate(items);
        this.variations = Arrays.asList(items);
    }

    @Override
    public boolean test(ItemStack stack) {
        return predicate.test(stack);
    }

    /**
     * This method returns a {@link Collection} of every possible {@link ItemStack} this
     * {@link Predicate} may accept.
     * 
     * @return A {@link Collection} of all acceptable {@link ItemStack ItemStacks}
     */
    public Collection<ItemStack> getVariations() {
        return variations;
    }

    /**
     * This returns an empty {@link Ingredient}.
     * The {@link Predicate} will match if the given {@link ItemStack} is either null or Air.
     */
    public static Ingredient empty() {
        return emptyIngredient;
    }

}
