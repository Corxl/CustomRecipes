package me.corxl.corxlrecipes.Events;

import me.corxl.corxlrecipes.Recipies.ElytraRecipes.BoosterElytra;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ElytraModeSwitchListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem()!=null) return;
        if (!event.getPlayer().isGliding()) return;
        if (!event.getAction().isLeftClick()) return;
        if (event.getClickedBlock()!=null) return;
        ItemStack elytra = event.getPlayer().getInventory().getChestplate();
        ItemMeta meta = elytra.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(BoosterElytra.getBoostModeKey())) return;
        changeMode(elytra, event.getPlayer());
        PlayerGlideListener.showFuelToPlayer(meta, event.getPlayer());
    }

    public static void changeMode(ItemStack elytra, Player p) {
        ItemMeta meta = elytra.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        int mode = container.get(BoosterElytra.getBoostModeKey(), PersistentDataType.INTEGER) + 1;
        if (mode > 3)
            mode = 1;
        container.set(BoosterElytra.getBoostModeKey(), PersistentDataType.INTEGER, mode);
        elytra.setItemMeta(meta);
        StringBuilder b = new StringBuilder();
        for (int i = 1; i <= 3; i++) {
            String color;
            if (i==2) {
                color = "&e";
            } else if (i==3) {
                color = "&4";
            } else {
                color = "&2";
            }
            b.append(color);
            if (i==mode)
                b.append("■");
            else
                b.append("⬚");
        }
        p.sendTitle(" ", ChatColor.translateAlternateColorCodes('&', "&bMode: " + b.toString()), 1, 50, 2);
    }
}
