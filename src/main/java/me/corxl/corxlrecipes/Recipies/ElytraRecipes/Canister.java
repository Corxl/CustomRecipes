package me.corxl.corxlrecipes.Recipies.ElytraRecipes;

import me.corxl.corxlrecipes.CorxlRecipes;
import me.corxl.corxlrecipes.Recipies.EnchantmentBooks.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Canister implements CustomRecipe {

    public static final String ITEM_TITLE = ChatColor.translateAlternateColorCodes('&', "&7&lFuel Canister");

    public static List<String> getItemLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&6&lUsed to replenish fuel in &5Dragon_Wings"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bAmount replenished: &220%"));
        return lore;
    }

    @Override
    public ItemStack getItem() {
        ItemStack canister = new ItemStack(Material.DRAGON_BREATH, 2);
        ItemMeta meta = canister.getItemMeta();
        meta.setCustomModelData(1);
        meta.setLore(getItemLore());
        meta.setDisplayName(ITEM_TITLE);
        canister.setItemMeta(meta);
        canister.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return canister;
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "canister_recipe"), this.getItem());
        recipe.shape("CGC", "IGI", "CGC");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('G', Material.GUNPOWDER);
        recipe.setIngredient('C', Material.COPPER_INGOT);
        return recipe;
    }
}
