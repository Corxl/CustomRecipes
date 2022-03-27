package me.corxl.corxlrecipes.Commands;

import me.corxl.corxlrecipes.CorxlRecipes;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GiveItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        if (!command.getName().equalsIgnoreCase("crgiveitem")) return false;
        Player p = (Player) sender;
        if (!p.hasPermission("corxlrecipes.giveitem")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You do not have permission to use that command."));
            return false;
        }
        if (args.length!=1) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Incorrect Syntax..."));
            return false;
        }
        if (!CorxlRecipes.getPlugin(CorxlRecipes.class).getRecipes().containsKey(args[0])) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Invalid item ID..."));
            return false;
        }
        ItemStack item = CorxlRecipes.getPlugin(CorxlRecipes.class).getRecipes().get(args[0]).getItem();
        if (p.getInventory().firstEmpty()==-1) {
            p.getWorld().dropItemNaturally( p.getLocation(), item);
        } else {
            p.getInventory().addItem(item);
        }
        p.sendMessage(ChatColor.GREEN + "You have been given: " + item.getItemMeta().getDisplayName());
        return false;
    }
}
