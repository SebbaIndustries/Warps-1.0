package com.sebbaindustries.warps.warp;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.warp.components.WarpLocation;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

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
    Returns a String value based on the input (true/false)
     */
    public static String getBooleanString(final boolean value) {
        return value ? "public" : "private";
    }

    /*
    Returns a boolean value based on the input (private/public)
     */
    public static boolean getBooleanValue(final String value) {
        return value.equalsIgnoreCase("public");
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
        if (values.isEmpty()) {
            return "/";
        }

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
            case REALESTATE:
                return "Nepremicnina";
            default:
                return "Nedoloceno";
        }
    }

    /*
    Returns a list of non-formatted categories
     */
    public static List<String> getCategories() {
        final List<String> categories = new ArrayList<>();
        final Warp.Category[] values = Warp.Category.values();

        for (Warp.Category category : values) {
            categories.add(category.name());
        }

        return categories;
    }

    /*
    Returns a list of publicly available warps
     */
    public static List<String> getPublicWarps() {
        final List<String> warps = new ArrayList<>();
        final Map<String, Warp> warpMap = Core.gCore.warpStorage.getWarpHashMap();

        for (String warp : warpMap.keySet()) {
            if (warpMap.get(warp).getAccessibility()) warps.add(warp);
        }
        return warps;
    }

    /*
    Returns a list of warp owners
     */
    public static List<String> getWarpOwners() {
        final List<String> owners = new ArrayList<>();
        final Map<String, Warp> warpMap = Core.gCore.warpStorage.getWarpHashMap();

        warpMap.forEach((key, value) -> owners.add(warpMap.get(key).getOwner()));
        return owners;
    }

    /*
    Returns a list of specified players warps
     */
    public static List<String> getPlayersWarps(final Player player) {
        final List<String> warps = new ArrayList<>();
        final Map<String, Warp> warpMap = Core.gCore.warpStorage.getWarpHashMap();

        warpMap.forEach((key, value) -> {
            if (warpMap.get(key).getOwner().equalsIgnoreCase(player.getName())) warps.add(key);
        });

        return warps;
    }
}
