package me.corxl.corxlrecipes.Events;

import me.corxl.corxlrecipes.CorxlRecipes;
import me.corxl.corxlrecipes.Recipies.ElytraRecipes.BoosterElytra;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class PlayerGlideListener implements Listener {

    private final static CorxlRecipes p = CorxlRecipes.getPlugin(CorxlRecipes.class);

    private HashMap<String, Long> gliders = new HashMap<>();

    @EventHandler
    public void onGlide(PlayerMoveEvent event) {
        if (!event.getPlayer().isGliding()) return;
        if (gliders.containsKey(event.getPlayer().getUniqueId().toString())) return;
        if (event.getPlayer().getInventory().getChestplate()==null) return;
        if (!event.getPlayer().getInventory().getChestplate().getType().equals(Material.ELYTRA)) return;
        ItemStack elytra = event.getPlayer().getInventory().getChestplate();
        ItemMeta meta = elytra.getItemMeta();
        Player player = event.getPlayer();
        if (!meta.getPersistentDataContainer().has(new NamespacedKey(p, "boosted_elytra"), PersistentDataType.INTEGER)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', "&bFuel: [ &4Not Installed &b]")));
            return;
        }
        if (!meta.getPersistentDataContainer().has(BoosterElytra.getBoostModeKey(), PersistentDataType.INTEGER)) {
            meta.getPersistentDataContainer().set(BoosterElytra.getBoostModeKey(), PersistentDataType.INTEGER, 1);
            elytra.setItemMeta(meta);
        }
        gliders.put(event.getPlayer().getUniqueId().toString(), System.currentTimeMillis() + 1000L);
        Bukkit.getScheduler().runTaskLater(CorxlRecipes.getPlugin(CorxlRecipes.class), ()-> {
            gliders.remove(event.getPlayer().getUniqueId().toString());
        }, 10L);

        showFuelToPlayer(meta, player);
    }

    public static void showFuelToPlayer(ItemMeta meta, Player player) {
        PersistentDataContainer c = meta.getPersistentDataContainer();
        int fuel = c.get(new NamespacedKey(p, "boosted_elytra"), PersistentDataType.INTEGER);
        int mode = c.get(BoosterElytra.getBoostModeKey(), PersistentDataType.INTEGER);
        String color;
        if (fuel > 75)
            color = "&2";
        else if (fuel > 50)
            color = "&e";
        else if (fuel > 25)
            color = "&6";
        else
            color = "&4";
        String boostMode = "";
        if (mode==1) {
            boostMode = "&2Low";
        } else if (mode==2) {
            boostMode = "&eMedium";
        } else if (mode==3) {
            boostMode = "&4High";
        }
        StringBuilder builder = new StringBuilder(ChatColor.AQUA + "Fuel: [");
        for (int i = 1; i <= 20; i++) {
            if (fuel/(i*5) > 0)
                builder.append(ChatColor.translateAlternateColorCodes('&', color + "|"));
            else if (i%2==0)
                builder.append(ChatColor.GRAY + "-");
        }
        builder.append(ChatColor.AQUA + "] ");
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&',  builder.toString() + color + fuel + "% " + boostMode)));
    }
}
