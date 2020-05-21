package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WarpCategory extends ICommand {

    public WarpCategory() {
        super("category", "category [category/set] (warp) <category>", 0);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.CATEGORY);
        setPlayerOnly();
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final String argument = args.length >= 1 ? args[0].toLowerCase() : null;

        if (argument == null) {
            // Invalid input
            return;
        }

        if (argument.equalsIgnoreCase("set")) {
            final String name = args.length >= 2 ? args[1] : player.getName();
            final Warp.Category category = args.length == 3 ? Warp.Category.valueOf(args[2].toUpperCase()) : Warp.Category.UNDEFINED;

            final Warp warp = Core.gCore.warpStorage.getWarp(name);

            if (warp == null) {
                player.sendMessage(Core.gCore.message.get(EMessage.INVALID_WARP));
                return;
            }

            if (!warp.getOwner().equals(player)) {
                player.sendMessage(Core.gCore.message.get(EMessage.NOT_WARP_OWNER));
                return;
            }

            warp.setCategory(category);
            player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SET_WARP_CATEGORY), "{warp-category}", WarpUtils.getFormattedCategory(category), "{warp-name}", name));
            return;
        }

        final Warp.Category category = args.length == 2 ? Warp.Category.valueOf(args[1].toUpperCase()) : Warp.Category.UNDEFINED;
        final List<String> categoryWarps = new ArrayList<>();
        final Map<String, Warp> warpMap = Core.gCore.warpStorage.getWarpHashMap();

        warpMap.forEach((name, warp) -> {
            if (warp.getCategory().equals(category)) {
                categoryWarps.add(name);
            }
        });

        // Add category menu
        player.sendMessage(WarpUtils.getStringLister(categoryWarps));
    }
}
