package me.corxl.corxlrecipes.Recipies.BlockRecipes;

import me.corxl.corxlrecipes.CorxlRecipes;
import me.corxl.corxlrecipes.Recipies.EnchantmentBooks.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DyamiteRecipe implements CustomRecipe {

    public static final String TNT_DISPLAYNAME = ChatColor.translateAlternateColorCodes('&', "&4&lDynamite");
    public static final List<String> LORE = initializeLore();
    @Override
    public ItemStack getItem() {
        ItemStack tnt = new ItemStack(Material.TNT);
        ItemMeta meta = tnt.getItemMeta();
        meta.setDisplayName(TNT_DISPLAYNAME);

        meta.setLore(LORE);
        tnt.setItemMeta(meta);
        return tnt;
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "dynamite_recipe"), this.getItem());
        sr.shape(" T ", "TIT", " T ");
        sr.setIngredient('T', Material.TNT);
        sr.setIngredient('I', Material.IRON_INGOT);
        return sr;
    }

    public static List<String> getLore() {
        return LORE;
    }

    private static List<String> initializeLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&c&lWARNING &bThis TNT will instantly ignite!"));
        return Collections.unmodifiableList(lore);
    }
}
