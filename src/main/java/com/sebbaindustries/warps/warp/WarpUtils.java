package com.sebbaindustries.warps.warp;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.warp.components.WarpLocation;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static String getSplitDescription(final String description) {
        String[] text = description.split(" ");

        if (text.length < 4) {
            return description;
        }

        String newDescription = "";

        for (int i = 0; i < text.length; i++) {
            if (i == 5) {
                newDescription = newDescription.concat(text[i] + "\\n ");
                continue;
            }
            newDescription = newDescription.concat(text[i] + " ");
        }

        return newDescription;
    }

    public static Map<String, Warp> getFilteredWarps(final Map<String, Warp> warpMap, final String type) {
        final Map<String, Warp> filteredWarps = new HashMap<>();

        for (String name : warpMap.keySet()) {
            final Warp warp = Core.gCore.warpStorage.getWarp(name);

            if (warp.getType().toString().equalsIgnoreCase(type)) {
                filteredWarps.put(name, warp);
            }
        }

        return filteredWarps;
    }

    public static String getStringLister(final List<String> values) {
        String list = "";
        for (int i = 0; i < values.size(); i++) {
            if (i == values.size() - 1) {
                list = list.concat(values.get(i));
                continue;
            }
            list = list.concat(values.get(i) + ", ");
        }

        return list;
    }
}
