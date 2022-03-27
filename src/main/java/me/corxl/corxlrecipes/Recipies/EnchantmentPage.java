package me.corxl.corxlrecipes.Recipies;

import me.corxl.corxlrecipes.CorxlRecipes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentPage implements CustomRecipe {

    private final Enchantment enchantment;
    private ShapedRecipe recipe;
    private final int enchantmentLevel, highestEnchantmentLevel;
    private final CorxlRecipes plugin;

    public EnchantmentPage(Enchantment enchantment, Recipe recipe) {
        this.enchantment = enchantment;
        this.enchantmentLevel = 1;
        this.highestEnchantmentLevel = enchantment.getMaxLevel();
        this.plugin = CorxlRecipes.getPlugin(CorxlRecipes.class);
        this.setRecipe(recipe);
    }
    private EnchantmentPage(Enchantment enchantment, int enchantmentLevel) {
        this.enchantment = enchantment;
        this.enchantmentLevel = enchantmentLevel;
        this.highestEnchantmentLevel = enchantment.getMaxLevel();
        this.plugin = CorxlRecipes.getPlugin(CorxlRecipes.class);
    }
    public ItemStack getPage() {
        List<String> lore = new ArrayList<>();
        ItemStack item = new ItemStack(Material.PAPER);
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bPage used to craft an enchantment book."));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCraft with &6Empty Book."));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',  "&6" + enchantment.getKey().getKey().replaceFirst(String.valueOf(enchantment.getKey().getKey().toCharArray()[0]), String.valueOf(enchantment.getKey().getKey().toCharArray()[0]).toUpperCase()) +
                " [&e" + this.enchantmentLevel + "&6]" + "&d Enchantment Page"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        item.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        return item;
    }

    private ItemStack getBook() {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        meta.addStoredEnchant(this.getEnchantment(), this.getEnchantmentLevel(), false);
        book.setItemMeta(meta);
        return book;
    }

    private ShapedRecipe getBookRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this.plugin, this.getEnchantment().getName() + "_book_" + this.getEnchantmentLevel()), this.getBook());
        recipe.shape("EP");
        EnchantmentPage page = new EnchantmentPage(this.getEnchantment(), this.getEnchantmentLevel());
        recipe.setIngredient('E', new RecipeChoice.ExactChoice(this.plugin.getRecipes().get("empty_book").getItem()));
        recipe.setIngredient('P', new RecipeChoice.ExactChoice(page.getPage()));
        return recipe;
    }

    private void setRecipe(Recipe recipe) {
        ShapedRecipe sRecipe = new ShapedRecipe(new NamespacedKey(this.plugin, this.getEnchantment().getName()+"_page"), this.getPage());
        this.recipe = recipe.getRecipe(sRecipe);
        for (int i = 2; i < highestEnchantmentLevel+1; i++) {
            EnchantmentPage page = new EnchantmentPage(this.getEnchantment(), i-1);
            EnchantmentPage result = new EnchantmentPage(this.getEnchantment(), i);
            ShapedRecipe rec = new ShapedRecipe(new NamespacedKey(this.plugin, result.getEnchantment().getName() + result.getEnchantmentLevel()), result.getPage());
            rec.shape("PP");
            rec.setIngredient('P', new RecipeChoice.ExactChoice(page.getPage()));
            this.plugin.getServer().addRecipe(rec);

            if (i+1<highestEnchantmentLevel)
                this.plugin.getServer().addRecipe(page.getBookRecipe());
            else
                this.plugin.getServer().addRecipe(result.getBookRecipe());
        }
        this.plugin.getServer().addRecipe(this.recipe);
        this.plugin.getServer().addRecipe(this.getBookRecipe());
    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getEnchantmentLevel() {
        return enchantmentLevel;
    }

    @Override
    public ItemStack getItem() {
        return this.getPage();
    }

    public interface Recipe {
        ShapedRecipe getRecipe(ShapedRecipe recipe);
    }
}
