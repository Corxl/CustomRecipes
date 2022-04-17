package me.corxl.corxlrecipes.Events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class AnvilPrepareListener implements Listener {
    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        if (event.getResult()==null) return;
        if (!event.getResult().getType().equals(Material.ELYTRA)) return;
        event.setResult(null);
    }
}
