package com.sebbaindustries.warps.utils;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

/**
 * <b>This class is for translating colors into strings that display them</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public final class Color {

    /**
     * Chat color translator
     * @param s String that needs translating
     * @return Translated string
     */
    public static String chat(final @NotNull String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * Method for translating hex color codes (for minecraft 1.16)
     * @param s String that needs translating
     * @return Translated string
     */
    @Deprecated
    public static String hex(final @NotNull String s) {
        return null;
    }

}
