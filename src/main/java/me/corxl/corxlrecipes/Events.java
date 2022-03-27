package me.corxl.corxlrecipes;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Collections;

public class Events implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasDiscoveredRecipe(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "empty_book"))){
            e.getPlayer().discoverRecipes(Collections.singleton(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "empty_book")));
            e.getPlayer().discoverRecipes(Collections.singleton(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "mending_page")));
            e.getPlayer().discoverRecipes(Collections.singleton(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "mending_book")));
        }
    }
}
