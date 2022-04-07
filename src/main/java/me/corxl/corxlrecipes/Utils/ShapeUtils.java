package me.corxl.corxlrecipes.Utils;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ShapeUtils {
    /**
     * @param centerBlock Block at the center of the sphere.
     * @param radius      Radius of the sphere
     * @param isHollow    Determines whether the sphere is hollow or not.
     * @return List of locations of the sphere.
     * @author TheBCBroz -> https://www.youtube.com/channel/UCOBqSqnLO7Z42Y8JwgfhpRQ
     */
    public static List<Location> generateSphere(Location centerBlock, int radius, boolean isHollow) {
        List<Location> circleBlocks = new ArrayList<>();
        int bX = centerBlock.getBlockX();
        int bY = centerBlock.getBlockY();
        int bZ = centerBlock.getBlockZ();
        for (int x = bX - radius; x <= bX + radius; x++) {
            for (int y = bY - radius; y <= bY + radius; y++) {
                for (int z = bZ - radius; z <= bZ + radius; z++) {
                    double distance = Math.pow((bX - x), 2.0) +
                            Math.pow(bZ - z, 2.0) +
                            Math.pow(bY - y, 2.0);
                    if (distance < Math.pow(radius, 2) && !(isHollow && distance < Math.pow(radius - 1, 2))) {
                        Location l = new Location(centerBlock.getWorld(), x, y, z);
                        circleBlocks.add(l);
                    }
                }
            }
        }
        return circleBlocks;
    }

    /**
     * @param centerBlock Block at the center of the sphere.
     * @param radius      Radius of the sphere
     * @param isHollow    Determines whether the sphere is hollow or not.
     * @param blockType   Determines what block the area will be set to.
     * @return List of locations of the sphere.
     * @author TheBCBroz -> https://www.youtube.com/channel/UCOBqSqnLO7Z42Y8JwgfhpRQ
     */
    public static void generateSphere(Location centerBlock, int radius, boolean isHollow, Material blockType) {
        List<Location> circleBlocks = new ArrayList<>();
        int bX = centerBlock.getBlockX();
        int bY = centerBlock.getBlockY();
        int bZ = centerBlock.getBlockZ();
        for (int x = bX - radius; x <= bX + radius; x++) {
            for (int y = bY - radius; y <= bY + radius; y++) {
                for (int z = bZ - radius; z <= bZ + radius; z++) {
                    double distance = Math.pow((bX - x), 2.0) +
                            Math.pow(bZ - z, 2.0) +
                            Math.pow(bY - y, 2.0);
                    if (distance < Math.pow(radius, 2) && !(isHollow && distance < Math.pow(radius - 1, 2))) {
                        Location l = new Location(centerBlock.getWorld(), x, y, z);
                        centerBlock.getWorld().getBlockAt(l).setType(blockType);

                    }
                }
            }
        }
    }
}
