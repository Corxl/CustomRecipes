package me.corxl.corxlrecipes.Recipies.EnchantmentBooks;

import me.corxl.corxlrecipes.CorxlRecipes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EmptyBook implements CustomRecipe {
    public ItemStack getItem() {
        ItemStack book = new ItemStack(Material.BOOK);
        book.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        book.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ItemMeta meta = book.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Empty Book"));
        book.setItemMeta(meta);
        List<String> lore = book.getLore() == null ? new ArrayList<>() : book.getLore();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&6A book with it's pages missing..."));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bUse with enchantment pages to craft enchantment books."));
        book.setLore(lore);
        return book;
    }

    public ShapedRecipe getRecipe() {
        ItemStack stack = new ItemStack(getItem());
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "empty_book"), stack);
        recipe.shape("L");
        recipe.setIngredient('L', Material.LEATHER);
        return recipe;
    }
}
