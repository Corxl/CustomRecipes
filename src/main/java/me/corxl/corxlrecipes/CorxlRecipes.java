package me.corxl.corxlrecipes;

import me.corxl.corxlrecipes.Commands.GiveItemCommand;
import me.corxl.corxlrecipes.Commands.GiveItemTabCompleter;
import me.corxl.corxlrecipes.Events.Events;
import me.corxl.corxlrecipes.Recipies.BlockRecipes.DyamiteRecipe;
import me.corxl.corxlrecipes.Recipies.ElytraRecipes.BoosterElytra;
import me.corxl.corxlrecipes.Recipies.ElytraRecipes.Canister;
import me.corxl.corxlrecipes.Recipies.ElytraRecipes.DragonFeather;
import me.corxl.corxlrecipes.Recipies.ElytraRecipes.DragonWings;
import me.corxl.corxlrecipes.Recipies.EnchantmentBooks.CustomRecipe;
import me.corxl.corxlrecipes.Recipies.EnchantmentBooks.EmptyBook;
import me.corxl.corxlrecipes.Recipies.EnchantmentBooks.EnchantmentPage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;

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

        Iterator<Recipe> recipeIterator = this.getServer().recipeIterator();
        Recipe shulkerRecipe;
        while (recipeIterator.hasNext()) {
            shulkerRecipe = recipeIterator.next();
            if (shulkerRecipe.getResult().getType().equals(Material.SHULKER_BOX))
                recipeIterator.remove();
        }

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

        DragonWings wings = new DragonWings();
        this.recipes.put("dragon_wings", wings);
        Canister canister = new Canister();
        this.recipes.put("canister", canister);
        this.getServer().addRecipe(canister.getRecipe());

        this.recipes.put("dragon_feather", new DragonFeather());

        BoosterElytra boosterElytra = new BoosterElytra();
        this.getServer().addRecipe(boosterElytra.getRecipe());

        this.getServer().addRecipe(wings.getRecipe());
        this.getServer().addRecipe(this.shulkerRecipe());
    }

    public HashMap<String, CustomRecipe> getRecipes() {
        return recipes;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private ShapedRecipe shulkerRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "shulker_box_recipe"), new ItemStack(Material.SHULKER_BOX));
        recipe.shape("DSD", "RER", "DSD");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('S', Material.SHULKER_SHELL);
        recipe.setIngredient('R', Material.REDSTONE_BLOCK);
        recipe.setIngredient('E', Material.ENDER_CHEST);
        return recipe;
    }




    
}
