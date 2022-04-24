package me.corxl.corxlrecipes.Events;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import me.corxl.corxlrecipes.CorxlRecipes;
import me.corxl.corxlrecipes.Recipies.BlockRecipes.DyamiteRecipe;
import me.corxl.corxlrecipes.Recipies.ElytraRecipes.BoosterElytra;
import me.corxl.corxlrecipes.Recipies.ElytraRecipes.Canister;
import me.corxl.corxlrecipes.Recipies.ElytraRecipes.DragonFeather;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.Random;

public class Events implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasDiscoveredRecipe(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "empty_book"))){
            e.getPlayer().discoverRecipes(Collections.singleton(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "empty_book")));
            e.getPlayer().discoverRecipes(Collections.singleton(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "mending_page")));
            e.getPlayer().discoverRecipes(Collections.singleton(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "mending_book")));
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onTnTPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        if (!event.getBlock().getType().equals(Material.TNT)) return;
        ItemStack tnt = event.getItemInHand();
        if (tnt.getItemMeta().getDisplayName().equals(DyamiteRecipe.TNT_DISPLAYNAME)&&tnt.getItemMeta().hasLore()) {
            Location location = event.getBlock().getLocation().toBlockLocation();
            event.getBlock().setType(Material.AIR);
            World world = event.getBlock().getWorld();
            double displacement = 2.0;
            double correction = 0.5;
            TNTPrimed tntEntity = (TNTPrimed) world.spawnEntity(location.add(correction, 0, correction), EntityType.PRIMED_TNT);
            Bukkit.getScheduler().runTaskLater(CorxlRecipes.getPlugin(CorxlRecipes.class), () -> {
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

    @EventHandler
    public void onChunkLoad(EntitiesLoadEvent event) {
        if (!event.getWorld().getEnvironment().equals(World.Environment.THE_END)) return;
        for (Entity e : event.getEntities()) {
            if (!e.getType().equals(EntityType.ITEM_FRAME)) continue;
            ItemFrame iframe = (ItemFrame) e;
            if (iframe.getItem().getType().equals(Material.ELYTRA)) {
                if (!iframe.getItem().getItemMeta().hasLore())
                    iframe.setItem(new DragonFeather().getItem());
            }
        }
    }

    @EventHandler
    public void onEnderDragonChangePhase(EnderDragonChangePhaseEvent event) {
        if (!event.getNewPhase().equals(EnderDragon.Phase.DYING)) return;
        int delay = 200;
        if (new Random().nextDouble()<=0.75)
            Bukkit.getScheduler().runTaskLater(CorxlRecipes.getPlugin(CorxlRecipes.class), () -> {
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new DragonFeather().getItem());
            }, delay);
        if (new Random().nextDouble()<=0.50)
            Bukkit.getScheduler().runTaskLater(CorxlRecipes.getPlugin(CorxlRecipes.class), () -> {
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.DRAGON_HEAD));
            }, delay);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().isLeftClick()) return;
        if (event.getClickedBlock()!=null) return;
        if (event.getItem()==null) return;
        if (!event.getItem().getType().equals(Material.DRAGON_BREATH)) return;
        ItemStack canister = event.getPlayer().getInventory().getItemInMainHand();
        if (!canister.getItemMeta().hasLore()) return;
        if (!canister.getItemMeta().getLore().equals(Canister.getItemLore())) return;
        if (!canister.getItemMeta().getDisplayName().equals(Canister.ITEM_TITLE)) return;
        if (event.getPlayer().getInventory().getChestplate()==null) return;
        if (!event.getPlayer().getInventory().getChestplate().getType().equals(Material.ELYTRA)) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not wearing Dragon Wings!"));
            return;
        }
        ItemStack elytra = event.getPlayer().getInventory().getChestplate();
        ItemMeta  meta = elytra.getItemMeta();
        if (!meta.getPersistentDataContainer().has(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "boosted_elytra"), PersistentDataType.INTEGER)) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour Dragon Wings are not fitted with a boost canister!"));
            return;
        }
        if (!meta.getPersistentDataContainer().has(BoosterElytra.getBoostModeKey(), PersistentDataType.INTEGER)) {
            meta.getPersistentDataContainer().set(BoosterElytra.getBoostModeKey(), PersistentDataType.INTEGER, 1);
            elytra.setItemMeta(meta);
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();
        int fuel = container.get(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "boosted_elytra"), PersistentDataType.INTEGER);
        if (fuel==100) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour canister is already full!"));
            PlayerGlideListener.showFuelToPlayer(meta, event.getPlayer());
            return;
        }
        fuel +=20;
        if (fuel > 100)
            fuel = 100;
        container.set(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "boosted_elytra"), PersistentDataType.INTEGER, fuel);
        meta.setLore(BoosterElytra.getLore(meta));
        elytra.setItemMeta(meta);
        canister.setAmount(canister.getAmount()-1);
        event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 3.0F, 0.5F);
        PlayerGlideListener.showFuelToPlayer(elytra.getItemMeta(), event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSwapItems(PlayerItemHeldEvent event) {
        Bukkit.getScheduler().runTaskLater(CorxlRecipes.getPlugin(CorxlRecipes.class), () -> {
            if (!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DRAGON_BREATH)) return;
            ItemStack dragonsBreath = event.getPlayer().getInventory().getItemInMainHand();
            if (!dragonsBreath.getItemMeta().hasLore()) return;
            if (!dragonsBreath.getItemMeta().getDisplayName().equals(Canister.ITEM_TITLE)) return;
            if (event.getPlayer().getInventory().getChestplate()==null || !event.getPlayer().getInventory().getChestplate().getType().equals(Material.ELYTRA)) return;
            ItemStack elytra = event.getPlayer().getInventory().getChestplate();
            if (!elytra.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "boosted_elytra"), PersistentDataType.INTEGER)) return;
            PlayerGlideListener.showFuelToPlayer(elytra.getItemMeta(), event.getPlayer());
        }, 1);
    }

    @EventHandler
    public void onPotionBrew(BrewEvent event) {
        if (!event.getContents().getIngredient().getItemMeta().getDisplayName().equals(Canister.ITEM_TITLE)) return;
        event.setCancelled(true);
        ItemStack newCanister = new Canister().getItem();
        newCanister.setAmount(event.getContents().getIngredient().getAmount());
        event.getContents().getIngredient().setAmount(0);
        World world = event.getBlock().getWorld();
        world.dropItemNaturally(event.getBlock().getLocation(), newCanister);
        world.playSound(event.getBlock().getLocation(), Sound.AMBIENT_UNDERWATER_ENTER, 3.0F, 0.5F);
    }

    private void boostPlayer(Player player, ItemStack elytra) {
        ItemMeta meta = elytra.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        int fuel = container.get(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "boosted_elytra"), PersistentDataType.INTEGER);
        int mode = container.get(BoosterElytra.getBoostModeKey(), PersistentDataType.INTEGER);
        int newFuel = fuel - (2*mode);
        if (newFuel<0)
            return;
        if (newFuel==0) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 3.0F, 0.5F);
        } else {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 3.0F, 0.5F);
        }
        container.set(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "boosted_elytra"), PersistentDataType.INTEGER, newFuel);
        meta.setLore(BoosterElytra.getLore(meta));
        elytra.setItemMeta(meta);
        double boostAmount = 1.0 + ((double)mode * 0.5);
        player.setVelocity(player.getLocation().getDirection().multiply(boostAmount)); // 1.5
        PlayerGlideListener.showFuelToPlayer(elytra.getItemMeta(), player);
    }

    @EventHandler
    public void onFireWorkUse(PlayerElytraBoostEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerCrouch(PlayerToggleSneakEvent event) {
        if (!event.getPlayer().isGliding()) return;
        if (event.getPlayer().isSneaking()) return;
        if (event.getPlayer().getInventory().getChestplate()==null) return;
        if (!event.getPlayer().getInventory().getChestplate().getType().equals(Material.ELYTRA)) return;
        ItemStack elytra = event.getPlayer().getInventory().getChestplate();
        if (!elytra.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(CorxlRecipes.getPlugin(CorxlRecipes.class), "boosted_elytra"), PersistentDataType.INTEGER)) return;
        boostPlayer(event.getPlayer(), elytra);
    }
}
