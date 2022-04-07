package me.corxl.corxlrecipes;

import me.corxl.corxlrecipes.Commands.GiveItemCommand;
import me.corxl.corxlrecipes.Commands.GiveItemTabCompleter;
import me.corxl.corxlrecipes.Events.AbsorbentTNTListener;
import me.corxl.corxlrecipes.Events.Events;
import me.corxl.corxlrecipes.Recipies.BlockRecipes.AbsorbentTNT;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class CorxlRecipes extends JavaPlugin {

    private final HashMap<String, CustomRecipe> recipes = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        this.getServer().getPluginManager().registerEvents(new AbsorbentTNTListener(), this);
        this.getCommand("crgiveitem").setExecutor(new GiveItemCommand());
        this.getCommand("crgiveitem").setTabCompleter(new GiveItemTabCompleter());
        recipes.put("empty_book", new EmptyBook());

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
        this.recipes.put("unbreaking_page", unbreakingPage);
        this.recipes.put("dynamite_recipe", new DyamiteRecipe());
        this.recipes.put("absorbent_tnt", new AbsorbentTNT());
        this.recipes.put("dragon_wings", new DragonWings());
        this.recipes.put("canister", new Canister());
        this.recipes.put("dragon_feather", new DragonFeather());
        this.recipes.put("booster_elytra", new BoosterElytra());
        this.getServer().addRecipe(this.recipes.get("dynamite_recipe").getRecipe());
        this.getServer().addRecipe(this.recipes.get("absorbent_tnt").getRecipe());
        this.getServer().addRecipe(this.recipes.get("dragon_wings").getRecipe());
        this.getServer().addRecipe(this.recipes.get("canister").getRecipe());
        this.getServer().addRecipe(this.recipes.get("dragon_feather").getRecipe());
        this.getServer().addRecipe(this.recipes.get("booster_elytra").getRecipe());
        this.getServer().addRecipe(this.recipes.get("empty_book").getRecipe());

        for (ShapedRecipe recipe : this.shulkerRecipes()) {
            this.getServer().addRecipe(recipe);
        }
        this.getServer().addRecipe(this.gravelRecipe());
    }

    public HashMap<String, CustomRecipe> getRecipes() {
        return recipes;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private ArrayList<ShapedRecipe> shulkerRecipes() {
        ArrayList<ShapedRecipe> recipes = new ArrayList<>();

        ShapedRecipe shulkerBoxWithShell = new ShapedRecipe(new NamespacedKey(this, "shulker_box_shell_recipe"), new ItemStack(Material.SHULKER_BOX));
        shulkerBoxWithShell.shape("DSD", "RER", "DSD");
        shulkerBoxWithShell.setIngredient('D', Material.DIAMOND);
        shulkerBoxWithShell.setIngredient('S', Material.SHULKER_SHELL);
        shulkerBoxWithShell.setIngredient('R', Material.REDSTONE_BLOCK);
        shulkerBoxWithShell.setIngredient('E', Material.ENDER_CHEST);

        ShapedRecipe shulkerBoxRecipe = new ShapedRecipe(new NamespacedKey(this, "shulker_box_recipe"), new ItemStack(Material.SHULKER_BOX));
        shulkerBoxRecipe.shape(" D ", "RER", " D ");
        shulkerBoxRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
        shulkerBoxRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        shulkerBoxRecipe.setIngredient('E', Material.ENDER_CHEST);

        recipes.add(shulkerBoxWithShell);
        recipes.add(shulkerBoxRecipe);

        return recipes;
    }

    private ShapedRecipe gravelRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "gravel_recipe"), new ItemStack(Material.GRAVEL, 2));
        recipe.shape("C", "C");
        recipe.setIngredient('C', Material.COBBLESTONE);
        return recipe;
    }


    
}
