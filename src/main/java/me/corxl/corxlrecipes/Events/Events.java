package me.corxl.corxlrecipes.Events;

import me.corxl.corxlrecipes.CorxlRecipes;
import me.corxl.corxlrecipes.Recipies.BlockRecipes.DyamiteRecipe;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

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

    @EventHandler
    public void onTnTPlace(BlockPlaceEvent event) {
        if (!event.getBlock().getType().equals(Material.TNT)) return;
        ItemStack tnt = event.getItemInHand();
        if (tnt.getItemMeta().getDisplayName().equals(DyamiteRecipe.TNT_DISPLAYNAME)&&tnt.getItemMeta().hasLore()) {
            Location location = event.getBlock().getLocation().toBlockLocation();
            event.getBlock().setType(Material.AIR);
            World world = event.getBlock().getWorld();
            TNTPrimed tntEntity = (TNTPrimed) world.spawnEntity(location, EntityType.PRIMED_TNT);
            Bukkit.getScheduler().runTaskLater(CorxlRecipes.getPlugin(CorxlRecipes.class), () -> {
                int displacement = 2;
                TNTPrimed[] primedTnt = new TNTPrimed[]{
                        (TNTPrimed) world.spawnEntity(location.add(displacement, -displacement, displacement), EntityType.PRIMED_TNT),
                        (TNTPrimed) world.spawnEntity(location.add(-displacement, -displacement, displacement), EntityType.PRIMED_TNT),
                        (TNTPrimed) world.spawnEntity(location.add(displacement, -displacement, -displacement), EntityType.PRIMED_TNT),
                        (TNTPrimed) world.spawnEntity(location.add(-displacement, -displacement, -displacement), EntityType.PRIMED_TNT),
                        (TNTPrimed) world.spawnEntity(location.add(displacement, displacement, displacement), EntityType.PRIMED_TNT),
                        (TNTPrimed) world.spawnEntity(location.add(-displacement, displacement, displacement), EntityType.PRIMED_TNT),
                        (TNTPrimed) world.spawnEntity(location.add(displacement, displacement, -displacement), EntityType.PRIMED_TNT),
                        (TNTPrimed) world.spawnEntity(location.add(-displacement, displacement, -displacement), EntityType.PRIMED_TNT)
                };
                for (TNTPrimed prime : primedTnt) {
                    prime.setFuseTicks(0);
                }
            },tntEntity.getFuseTicks());
        }
    }
}
