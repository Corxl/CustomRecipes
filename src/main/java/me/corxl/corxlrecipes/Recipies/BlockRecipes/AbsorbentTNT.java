package me.corxl.corxlrecipes.Recipies.BlockRecipes;

import me.corxl.corxlrecipes.CorxlRecipes;
import me.corxl.corxlrecipes.Recipies.EnchantmentBooks.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class AbsorbentTNT implements CustomRecipe {

    public static final String ITEM_TITLE = ChatColor.translateAlternateColorCodes('&', "&b&lAbsorbent Dynamite");

    @Override
    public ItemStack getItem() {
        ItemStack tnt = new ItemStack(Material.TNT);
        ItemMeta meta = tnt.getItemMeta();
        meta.setLore(getLore());
        meta.setDisplayName(ITEM_TITLE);
        tnt.setItemMeta(meta);
        tnt.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        tnt.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        tnt.setAmount(4);
        return tnt;
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getNSK(CorxlRecipes.getPlugin(CorxlRecipes.class)), this.getItem());
        recipe.shape(" G ", "GPG", " G ");
        recipe.setIngredient('G', new RecipeChoice.ExactChoice(new ItemStack(Material.GUNPOWDER)));
        recipe.setIngredient('P', new RecipeChoice.ExactChoice(new ItemStack(Material.BUCKET)));
        return recipe;
    }

    public static List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&4&lWARNING &bThis TNT will insantly ignite!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&6Drains liquid in a radius around the explosion."));
        return lore;
    }

    public static NamespacedKey getNSK(Plugin plugin) {
        return new NamespacedKey(plugin, "absorbent_tnt");
    }
}
