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
    TODO: add config list
     */
    private static final List<Material> blacklisted = new ArrayList<>(Arrays.asList(Material.POLISHED_GRANITE, Material.STONE));

    /*
    TODO: add config option
    */
    private static final int radius = 1;

    /*
    Performs necessary safety checks around the warp, to ensure it's safe
    for teleportation and creation
     */
    public static boolean isLocationSafe(final WarpLocation location) {
        if (location == null) {
            return false;
        }

        final Location loc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());

        for (Material m : blacklisted) {
            for (int i = -1; i <= 2; i++) {
                Block b;
                switch (i) {
                    case -1:
                        b = loc.clone().subtract(0, 1, 1).getBlock();
                        if (b.getType().equals(m)) {
                            return false;
                        }
                        break;
                    case 2:
                        b = loc.clone().add(0, 2, 0).getBlock();
                        if (b.getType().equals(m)) {
                            return false;
                        }
                    case 1:
                    case 0:
                        b = loc.clone().add(0, i, 0).getBlock();
                        if (!b.getType().equals(Material.AIR)) {
                            return false;
                        }
                }
            }
        }

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    final Block check = loc.clone().add(x, y, z).getBlock();

                    if (blacklisted.stream().anyMatch(m -> m.equals(check.getType()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
