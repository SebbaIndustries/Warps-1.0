package com.sebbaindustries.warps.commands.creator.completion;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class TabCompletion implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return Collections.emptyList();
        }
        final Player player = (Player) commandSender;

        if (strings.length == 1) {
            return StringUtil.copyPartialMatches(strings[0].toLowerCase(), getCommand0Arguments(), new ArrayList<>(getCommand0Arguments().size()));
        }
        if (strings.length == 2) {
            return StringUtil.copyPartialMatches(strings[1].toLowerCase(), getCommand1Arguments(player, strings[0]), new ArrayList<>(getCommand1Arguments(player, strings[0].toLowerCase()).size()));
        }
        if (strings.length == 3) {
            return StringUtil.copyPartialMatches(strings[2].toLowerCase(), getCommand2Arguments(player, strings[0], strings[1]), new ArrayList<>(getCommand2Arguments(player, strings[0], strings[1]).size()));
        }
        if (strings.length == 4) {
            return StringUtil.copyPartialMatches(strings[3].toLowerCase(), getCommand3Arguments(strings[0], strings[1], strings[2]), new ArrayList<>(getCommand3Arguments(strings[0], strings[1], strings[2]).size()));
        }
        return Collections.emptyList();
    }

    private List<String> getCommand0Arguments() {
        final List<String> result = new ArrayList<>();

        result.add("create");
        result.add("delete");
        result.add("change");
        result.add("description");
        result.add("list");
        result.add("rate");
        result.add("teleport");
        result.add("category");

        final Map<String, Warp> warpMap = Core.gCore.warpStorage.getWarpHashMap();
        warpMap.forEach((key, value) -> {
            if (warpMap.get(key).getAccessibility()) result.add(key);
        });

        return result;
    }

    private List<String> getCommand1Arguments(final Player player, final String argument) {
        List<String> result;
        switch (argument) {
            case "delete":
            case "change":
                return WarpUtils.getPlayersWarps(player);
            case "description":
                return Arrays.asList("set", "remove");
            case "list":
                return WarpUtils.getWarpOwners();
            case "rate":
            case "teleport":
                return WarpUtils.getPublicWarps();
            case "category":
                result = WarpUtils.getCategories();
                result.add("set");
                return result;
            default:
                return Collections.emptyList();
        }
    }

    private List<String> getCommand2Arguments(final Player player, final String argument1, final String argument2) {
        switch (argument1) {
            case "rate":
                return Collections.singletonList("[<1 - 10>]");
            case "category":
                if (argument2.equalsIgnoreCase("set")) {
                    return WarpUtils.getPlayersWarps(player);
                }
            case "description":
                return WarpUtils.getPlayersWarps(player);
            case "change":
                return Arrays.asList("location", "owner", "status");
        }
        return Collections.emptyList();
    }

    private List<String> getCommand3Arguments(final String argument1, final String argument2, final String argument3) {
        switch (argument1) {
            case "category":
                if (argument2.equalsIgnoreCase("set")) {
                    return WarpUtils.getCategories();
                }
            case "change":
                switch (argument3) {
                    case "owner":
                        return Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
                    case "location":
                        return Arrays.asList("x", "y", "z", "pitch", "yaw");
                    case "status":
                        return Arrays.asList(WarpUtils.getBooleanString(true), WarpUtils.getBooleanString(false));
                }

        }
        return Collections.emptyList();
    }
}
