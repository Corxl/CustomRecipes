package me.corxl.corxlrecipes.Recipies.ElytraRecipes;

import me.corxl.corxlrecipes.Recipies.EnchantmentBooks.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DragonFeather implements CustomRecipe {
    @Override
    public ItemStack getItem() {
        ItemStack dragonFeather = new ItemStack(Material.FEATHER);
        ItemMeta meta = dragonFeather.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&6Crafting ingredient used to craft an &dElytra"));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lDragon Feather"));
        meta.setLore(lore);

        dragonFeather.setItemMeta(meta);
        dragonFeather.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        dragonFeather.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return dragonFeather;
    }

    @Override
    public ShapedRecipe getRecipe() {
        return null;
    }
}
