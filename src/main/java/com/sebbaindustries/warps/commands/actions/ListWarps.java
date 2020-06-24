package com.sebbaindustries.warps.commands.actions;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListWarps extends ICommand {

    public ListWarps() {
        super("listwarps", "usage", 0);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.LIST);
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        if (args.length == 0) {
            listAllWarps(player);
            return;
        }

        for (String arg : args) {
            if (arg.equalsIgnoreCase("-o")) {
                player.sendMessage(Core.gCore.message.get(EMessage.INVALID_COMMAND_ARGUMENT));
                return;
            }
            if (arg.equalsIgnoreCase("-a")) {
                listAllWarps(player);
                return;
            }
        }

        listOwnerWarps(player, args);
    }

    private void listOwnerWarps(final Player p, final String[] args) {
        final Map<String, Warp> warpMap = Core.gCore.warpStorage.getWarpHashMap();
        final List<String> warpOwnerList = new ArrayList<>();
        Player t = args.length == 1 ? Bukkit.getPlayer(args[0]) : p;

        if (!p.hasPermission(EPermission.LIST_OTHERS.permission)) {
            t = p;
        }

        final Player target = t;

        if (target == null) {
            p.sendMessage(Core.gCore.message.get(EMessage.INVALID_PLAYER));
            return;
        }

        warpMap.forEach((name, warp) -> {
            if (warp.getOwner().equalsIgnoreCase(target.getName())) {
                warpOwnerList.add(name);
            }
        });

        if (warpOwnerList.isEmpty()) {
            p.sendMessage(Core.gCore.message.get(EMessage.TARGET_IS_WITHOUT_WARPS));
            return;
        }

        p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.LISTED_WARPS), "{target-name}", target.getName(), "{warp-names}", WarpUtils.getStringLister(warpOwnerList)));
    }

    private void listAllWarps(final Player p) {
        final Map<String, Warp> warpMap = Core.gCore.warpStorage.getWarpHashMap();
        final List<String> playerWarpList = new ArrayList<>();
        final List<String> officialWarpList = new ArrayList<>();

        warpMap.forEach((name, warp) -> {
            if (warp.getType() == Warp.Type.SERVER) {
                officialWarpList.add(name);
            } else {
                playerWarpList.add(name);
            }
        });

        p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.ALL_LISTED_WARPS)
                , "{player-warp-names}", WarpUtils.getStringLister(playerWarpList)
                , "{official-warp-names}", WarpUtils.getStringLister(officialWarpList)));
    }
}
