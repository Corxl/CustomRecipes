package me.corxl.corxlrecipes.Commands;

import me.corxl.corxlrecipes.CorxlRecipes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiveItemTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("crgiveitem") && sender.hasPermission("corxlrecipes.giveitem") && args.length==1) {
            List<String> tab = new ArrayList<>();
            CorxlRecipes.getPlugin(CorxlRecipes.class).getRecipes().forEach((key, value) -> {
                tab.add(key);
            });
            return tab;
        }
        return null;
    }
}
