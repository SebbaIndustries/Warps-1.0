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

public class WarpDelete extends ICommand {

    public WarpDelete() {
        super("delete", "delete [warp]", 1);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.DELETE);
        setPlayerOnly();
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final String name = args.length == 1 ? args[0] : player.getName();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (warp == null) {
            /*
             * @placeholder warpName = {warp-name}
             */
            player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.INVALID_WARP), "{warp-name}", name));
            return;
        }

        if (!warp.getOwner().equals(player)) {
            /*
            @placeholder warpName = {warp-name}
            @placeholder warpOwner = {warp-owner}
             */
            player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.NOT_WARP_OWNER)
                    , "{warp-name}", name
                    , "{warp-owner}", warp.getOwner().getName()));
            return;
        }

        if (Core.gCore.warpStorage.deleteWarp(name)) {
            /*
            @placeholder warpName = {warp-name}
            @placeholder warpLocation = {warp-location}
            @placeholder warpWorld = {warp-world}
            */
            player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_REMOVED_WARP)
                    , "{warp-name}", name
                    , "{warp-location}", WarpUtils.getLocationString(warp.getLocation())
                    , "{warp-world}", warp.getLocation().getWorld().getName()));
            return;
        }

        player.sendMessage(Core.gCore.message.get(EMessage.FAILED_TO_REMOVE_WARP));
    }
}
