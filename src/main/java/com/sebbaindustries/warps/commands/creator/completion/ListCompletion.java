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

public class ListCompletion implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return Collections.emptyList();
        }

        if (strings.length == 1) {
            return StringUtil.copyPartialMatches(strings[0], WarpUtils.getWarpOwners(), new ArrayList<>(WarpUtils.getWarpOwners().size()));
        }

        return Collections.emptyList();
    }
}
