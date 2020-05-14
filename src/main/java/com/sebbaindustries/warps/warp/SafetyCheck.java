package com.sebbaindustries.warps.warp;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SafetyCheck {

    /*
    List containing blacklisted materials
     */
    private static final List<Material> blacklisted = new ArrayList<>(Arrays.asList(Material.POLISHED_GRANITE, Material.STONE));

    /*
    Performs necessary safety checks around the warp, to ensure it's safe
    for teleportation and creation
     */
    public static boolean isLocationSafe(final WarpLocation location) {
        if (location == null) {
            return false;
        }

        boolean safe = true;
        for (int x = (int) location.getX() - 1; x < location.getX() + 1; x++) {
            for (int y = (int) location.getY() - 1; y < location.getY() + 2; y++) {
                for (int z = (int) location.getZ() - 1; z < location.getZ() + 1; z++) {
                    final Location l = new Location(location.getWorld(), x, y, z);
                    final Block block = location.getWorld().getBlockAt(l);

                    for (Material mat : blacklisted) {
                        if (block.getType() == mat) {
                            safe = false;
                        }
                    }
                }
            }
        }
        return safe;
    }
}
