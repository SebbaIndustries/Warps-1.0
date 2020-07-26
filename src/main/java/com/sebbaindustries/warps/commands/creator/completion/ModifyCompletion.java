package com.sebbaindustries.warps.commands.creator.completion;

import com.sebbaindustries.warps.warp.WarpUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ModifyCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return Collections.emptyList();
        }
        final Player player = (Player) commandSender;

        if (strings.length == 1) {
            return StringUtil.copyPartialMatches(strings[0], WarpUtils.getAllWarps(), new ArrayList<>(WarpUtils.getAllWarps().size()));
        }
        if (strings.length == 2) {
            final List<String> completions = new ArrayList<>(Arrays.asList("description", "category"));
            if (player.hasPermission("warps.command.modifywarp.official")) completions.add("-o");
            if (player.hasPermission("warps.command.modifywarp.admin")) completions.add("-a");

            return StringUtil.copyPartialMatches(strings[1], completions, new ArrayList<>(completions.size()));
        }
        if (strings.length == 3) {
            if (strings[1].equalsIgnoreCase("-o")) {
                return StringUtil.copyPartialMatches(strings[2], Arrays.asList("description", "category"), new ArrayList<>(2));
            }
            if (strings[1].equalsIgnoreCase("-a")) {
                return StringUtil.copyPartialMatches(strings[2], Arrays.asList("description", "category"), new ArrayList<>(4));
            }
            final List<String> completions = new ArrayList<>(Arrays.asList("shop", "pvp", "minigame", "other", "realestate", "undefined"));
            return StringUtil.copyPartialMatches(strings[2], completions, new ArrayList<>(completions.size()));
        }
        if (strings.length == 4) {
            if (strings[1].equalsIgnoreCase("-o")) {
                final List<String> completions = new ArrayList<>(Arrays.asList("shop", "pvp", "minigame", "other", "realestate", "undefined"));
                return StringUtil.copyPartialMatches(strings[3], completions, new ArrayList<>(completions.size()));
            }
            if (strings[1].equalsIgnoreCase("-a")) {
                final List<String> completions = new ArrayList<>(Arrays.asList("shop", "pvp", "minigame", "other", "realestate", "undefined"));
                return StringUtil.copyPartialMatches(strings[3], completions, new ArrayList<>(completions.size()));
            }
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }
}
