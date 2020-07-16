package com.sebbaindustries.warps.commands.creator.completion;

import com.sebbaindustries.warps.warp.WarpUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeleteCompletion implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return Collections.emptyList();
        }
        final Player player = (Player) commandSender;

        if (strings.length == 1) {
            return StringUtil.copyPartialMatches(strings[0], WarpUtils.getAllWarps(), new ArrayList<>(WarpUtils.getAllWarps().size()));
        }
        if (strings.length == 2) {
            final List<String> completions = new ArrayList<>();
            if (player.hasPermission("warps.command.modifywarp.official")) completions.add("-o");
            if (player.hasPermission("warps.command.modifywarp.admin")) completions.add("-a");

            return StringUtil.copyPartialMatches(strings[1], completions, new ArrayList<>(completions.size()));
        }

        return Collections.emptyList();
    }
}
