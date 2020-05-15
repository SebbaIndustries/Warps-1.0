package com.sebbaindustries.warps.warp;

import org.bukkit.Location;

public class WarpUtils {

    /*
    Add some methods, idk -Frcsty
     */

    /*
    TODO: add config formatter
     */
    public static String getLocationString(final WarpLocation location) {
        return "Location -> X: " + Math.floor(location.getX()) + ", Y: " + Math.floor(location.getY()) + ", Z: " + Math.floor(location.getZ());
    }

    /*
    TODO: add config formatter
     */
    public static String getBooleanString(final boolean value) {
        return value ? "True" : "False";
    }

    /*
    Converts a WarpLocation to a usable Bukkit Location
     */
    public static Location convertWarpLocation(final WarpLocation location) {
        return new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }
}
