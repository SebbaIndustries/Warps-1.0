package com.sebbaindustries.warps.warp;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.warp.components.WarpLocation;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.*;

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

    /*
    Returns a split description with \n (for lore purposes)
     */
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

    /*
    Returns a certain type of warp map (ie. SERVER/PLAYER)
     */
    public static SortedMap<String, Warp> getFilteredWarps(final Map<String, Warp> warpMap, final String type) {
        final SortedMap<String, Warp> filteredWarps = new TreeMap<>();

        for (String name : warpMap.keySet()) {
            final Warp warp = Core.gCore.warpStorage.getWarp(name);

            if (warp.getType().toString().equalsIgnoreCase(type)) {
                filteredWarps.put(name, warp);
            }
        }

        return filteredWarps;
    }

    /*
    Returns a map of specified category warps
     */
    public static SortedMap<String, Warp> getCategoryFilteredWarps(final Map<String, Warp> warpMap, final Warp.Category category) {
        final SortedMap<String, Warp> categoryFilteredWarps = new TreeMap<>();

        for (String name : warpMap.keySet()) {
            final Warp warp = Core.gCore.warpStorage.getWarp(name);

            if (warp.getCategory() == category) {
                categoryFilteredWarps.put(name, warp);
            }
        }

        return categoryFilteredWarps;
    }

    /*
    Returns a string separated with ,
     */
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

    /*
    Returns a category string
    TODO: Add config option to customize each one
     */
    public static String getFormattedCategory(final Warp.Category category) {
        switch (category) {
            case PVP:
                return "PvP";
            case SHOP:
                return "Trgovina";
            case MINIGAME:
                return "Miniigre";
            case OTHER:
                return "Ostalo";
            default:
                return "Nedoloceno";
        }
    }
}
