package me.corxl.corxlrecipes.Recipies.ElytraRecipes;

import me.corxl.corxlrecipes.CorxlRecipes;
import me.corxl.corxlrecipes.Recipies.EnchantmentBooks.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class BoosterElytra implements CustomRecipe {
    private static final CorxlRecipes plugin = CorxlRecipes.getPlugin(CorxlRecipes.class);

    @Override
    public ItemStack getItem() {
        ItemStack elytra = new DragonWings().getItem();
        ItemMeta meta = elytra.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(plugin, "boosted_elytra"), PersistentDataType.INTEGER, 0);
        meta.setLore(getLore());
        elytra.setItemMeta(meta);
        plugin.getRecipes().put("boosted_elytra", this);
        return elytra;
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "boosted_elytra_recipe"), this.getItem());
        recipe.shape("IGI", "NEN", "IGI");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('G', Material.GUNPOWDER);
        recipe.setIngredient('E', new RecipeChoice.ExactChoice(new DragonWings().getItem()));
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        return recipe;
    }

    public static List<String> getLore(ItemMeta meta) {
        int fuel = meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "boosted_elytra"), PersistentDataType.INTEGER);
        List<String> lore = new ArrayList<>();
        String color;
        if (fuel > 75)
            color = "&2";
        else if (fuel > 50)
            color = "&e";
        else if (fuel > 25)
            color = "&6";
        else
            color = "&4";
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bBoost Canister: " + color + fuel + "%"));
        return lore;
    }
    public static List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bBoost Canister: &40%"));
        return lore;
    }
}
