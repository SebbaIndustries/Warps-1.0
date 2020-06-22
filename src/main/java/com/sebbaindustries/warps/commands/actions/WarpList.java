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

public class WarpList extends ICommand {

    public WarpList() {
        super("listwarps", "list [owner]", 0);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.LIST);
        setPlayerOnly();
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final Map<String, Warp> warpMap = Core.gCore.warpStorage.getWarpHashMap();
        final List<String> warpOwnerList = new ArrayList<>();
        Player t = args.length == 1 ? Bukkit.getPlayer(args[0]) : player;

        if (!player.hasPermission(EPermission.LIST_OTHERS.permission)) {
            t = player;
        }

        final Player target = t;

        if (target == null) {
            player.sendMessage(Core.gCore.message.get(EMessage.INVALID_PLAYER));
            return;
        }

        warpMap.forEach((name, warp) -> {
            if (warp.getOwner().equalsIgnoreCase(target.getName())) {
                warpOwnerList.add(name);
            }
        });

        if (warpOwnerList.isEmpty()) {
            player.sendMessage(Core.gCore.message.get(EMessage.TARGET_IS_WITHOUT_WARPS));
            return;
        }

        player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.LISTED_WARPS), "{target-name}", target.getName(), "{warp-names}" , WarpUtils.getStringLister(warpOwnerList)));
    }
}
