package me.corxl.corxlrecipes.Recipies.ElytraRecipes;

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

import java.util.ArrayList;
import java.util.List;

public class DragonWings implements CustomRecipe {

    CorxlRecipes plugin = CorxlRecipes.getPlugin(CorxlRecipes.class);

    @Override
    public ItemStack getItem() {
        ItemStack elytra = new ItemStack(Material.ELYTRA);
        elytra.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        ItemMeta meta = elytra.getItemMeta();
        List<String> elytraLore = new ArrayList<>();
        elytraLore.add(ChatColor.translateAlternateColorCodes('&', "&bBoost Canister: &4Not Installed"));
        meta.setLore(elytraLore);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lDragon Wings"));
        meta.setUnbreakable(true);
        elytra.setItemMeta(meta);
        elytra.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        elytra.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return elytra;
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe elytraRecipe = new ShapedRecipe(new NamespacedKey(plugin, "elytra_recipe"), this.getItem());
        elytraRecipe.shape("PDP", "FPF", "P P");
        elytraRecipe.setIngredient('P', new RecipeChoice.ExactChoice(new ItemStack(Material.DRAGON_BREATH)));
        elytraRecipe.setIngredient('D', Material.DRAGON_HEAD);
        elytraRecipe.setIngredient('F', new RecipeChoice.ExactChoice(new DragonFeather().getItem()));
        return elytraRecipe;
    }
}
