package me.corxl.corxlrecipes.Events;

import me.corxl.corxlrecipes.CorxlRecipes;
import me.corxl.corxlrecipes.Recipies.BlockRecipes.AbsorbentTNT;
import me.corxl.corxlrecipes.Utils.ShapeUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class AbsorbentTNTListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onTnTPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        ItemStack block = event.getItemInHand();
        ItemMeta meta = block.getItemMeta();
        if (!block.getType().equals(Material.TNT)) return;
        if (!(meta.getDisplayName().equals(AbsorbentTNT.ITEM_TITLE) && meta.getLore().equals(AbsorbentTNT.getLore()))) return;
        Block tnt = event.getBlock();
        tnt.setType(Material.AIR);
        Location l = tnt.getLocation();
        World world = l.getWorld();
        Entity entity = world.spawnEntity(l.add(0.5, 0, 0.5), EntityType.PRIMED_TNT);
        PersistentDataContainer container = entity.getPersistentDataContainer();
        container.set(getNSK(CorxlRecipes.getPlugin(CorxlRecipes.class)), PersistentDataType.INTEGER, 1);
    }

    @EventHandler
    public void onTNTExplode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof TNTPrimed)) return;
        PersistentDataContainer container = event.getEntity().getPersistentDataContainer();
        if (container.has(getNSK(CorxlRecipes.getPlugin(CorxlRecipes.class)))) {
            event.blockList().clear();
            for (Location l : ShapeUtils.generateSphere(event.getEntity().getLocation(), 12, false)){
                Block block = l.getBlock();
                if (block.isLiquid())
                    block.setType(Material.AIR);
            }
        }
    }

    public static NamespacedKey getNSK(Plugin plugin) {
        return new NamespacedKey(plugin, "absorbent_tnt_entity");
    }
}
