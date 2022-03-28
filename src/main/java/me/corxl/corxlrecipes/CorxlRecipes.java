package me.corxl.corxlrecipes;

import me.corxl.corxlrecipes.Commands.GiveItemCommand;
import me.corxl.corxlrecipes.Commands.GiveItemTabCompleter;
import me.corxl.corxlrecipes.Events.Events;
import me.corxl.corxlrecipes.Recipies.BlockRecipes.DyamiteRecipe;
import me.corxl.corxlrecipes.Recipies.EnchantmentBooks.CustomRecipe;
import me.corxl.corxlrecipes.Recipies.EnchantmentBooks.EmptyBook;
import me.corxl.corxlrecipes.Recipies.EnchantmentBooks.EnchantmentPage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class CorxlRecipes extends JavaPlugin {

    private final HashMap<String, CustomRecipe> recipes = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        this.getCommand("crgiveitem").setExecutor(new GiveItemCommand());
        this.getCommand("crgiveitem").setTabCompleter(new GiveItemTabCompleter());
        EmptyBook eBook = new EmptyBook();
        this.getServer().addRecipe(eBook.getRecipe());
        recipes.put("empty_book", eBook);

        EnchantmentPage fortunePage = new EnchantmentPage(Enchantment.LOOT_BONUS_BLOCKS, recipe -> {
            recipe.shape(" I ", "DPD", " I ");
            recipe.setIngredient('I', Material.IRON_INGOT);
            recipe.setIngredient('D', Material.DIAMOND);
            recipe.setIngredient('P', Material.PAPER);
            return recipe;
        });
        recipes.put("fortune_page", fortunePage);

        EnchantmentPage mendingPage = new EnchantmentPage(Enchantment.MENDING, recipe -> {
            recipe.shape(" E ", "DND", " E ");
            recipe.setIngredient('E', Material.EMERALD);
            recipe.setIngredient('D', Material.DIAMOND_BLOCK);
            recipe.setIngredient('N', Material.NETHER_STAR);
            return recipe;
        });
        recipes.put("mending_page", mendingPage);

        EnchantmentPage unbreakingPage = new EnchantmentPage(Enchantment.DURABILITY, recipe -> {
            recipe.shape(" I ", "GNG", " I ");
            recipe.setIngredient('I', Material.IRON_INGOT);
            recipe.setIngredient('G', Material.GOLD_INGOT);
            recipe.setIngredient('N', Material.NETHERITE_SCRAP);
            return recipe;
        });
        recipes.put("unbreaking_page", unbreakingPage);
        DyamiteRecipe dynamite = new DyamiteRecipe();
        this.getServer().addRecipe(dynamite.getRecipe());
        recipes.put("dynamite_recipe", dynamite);
    }

    public HashMap<String, CustomRecipe> getRecipes() {
        return recipes;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    
}
