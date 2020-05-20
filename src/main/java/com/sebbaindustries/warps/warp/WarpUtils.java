package com.sebbaindustries.warps.warp;

import com.sebbaindustries.warps.warp.components.WarpLocation;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class WarpUtils {

    /*
    Add some methods, idk -Frcsty
     */

    /*
    TODO: add config formatter
     */
    public static String getLocationString(final WarpLocation location) {
        return "X: " + Math.floor(location.getX()) + ", Y: " + Math.floor(location.getY()) + ", Z: " + Math.floor(location.getZ());
    }

    /*
    TODO: add config formatter
     */
    public static String getBooleanString(final boolean value) {
        return value ? "&aPublic" : "&cPrivate";
    }

    /*
    Converts a WarpLocation to a usable Bukkit Location
     */
    public static Location convertWarpLocation(final WarpLocation location) {
        return new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    /*
    Gets the warps average rating
     */
    public static int getWarpAverageRating(final Warp warp) {
        if (warp.getRatings().size() == 0) return 0;

        int rating = 0;
        for (UUID rater : warp.getRatings().keySet()) {
            final int rate = warp.getRatings().get(rater);

            rating = rating + rate;
        }

        return rating / warp.getRatings().size();
    }

    /*
    Beautifies world name
     */
    public static String getWorldString(final World world) {
        return StringUtils.capitalize(world.getName().toLowerCase());
    }
}
