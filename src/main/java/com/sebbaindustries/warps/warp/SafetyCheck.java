package com.sebbaindustries.warps.warp;

import org.bukkit.Location;

public class SafetyCheck {

    /*
    Performs necessary safety checks around the warp, to ensure it's safe
    for teleportation and creation
     */

    public static boolean isLocationSafe(final Location location) {
        if (location == null) {
            return false;
        }


        return true;
    }
}
