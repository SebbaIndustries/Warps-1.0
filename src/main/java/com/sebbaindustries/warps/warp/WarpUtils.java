package com.sebbaindustries.warps.warp;

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
}
