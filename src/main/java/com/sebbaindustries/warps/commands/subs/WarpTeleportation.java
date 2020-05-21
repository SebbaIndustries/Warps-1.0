package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.*;
import com.sebbaindustries.warps.warp.components.SafetyCheck;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpTeleportation extends ICommand {

    public WarpTeleportation() {
        super("teleport", "teleport [warp]", 1);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.TELEPORT);
        setPlayerOnly();
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final String name = args.length >= 1 ? args[0] : player.getName();

        final Warp warp = Core.gCore.warpStorage.getWarp(name);
        /*
        Checks if the warp exists
        */
        if (warp == null) {
            player.sendMessage(Core.gCore.message.get(EMessage.INVALID_WARP));
            return;
        }

        if (!warp.getAccessibility() && !warp.getOwner().equals(player)) {
            player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.PRIVATE_WARP), "{warp-owner}", warp.getOwner().getName()));
            return;
        }

        /*
        @placeholder {warp-owner}
         */
        if (!SafetyCheck.isLocationSafe(warp.getLocation())) {
            player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.UNSAFE_TELEPORT_LOCATION)
                    , "{warp-owner}", warp.getOwner().getName()));
            return;
        }

        /*
        TODO: If warp teleportation delay (set in config) is 0 don't send message
         */
        player.sendMessage(Core.gCore.message.get(EMessage.TELEPORTATION_STARTED));
        /*
        TODO: TIMER @Nzd
        */
        player.teleport(WarpUtils.convertWarpLocation(warp.getLocation()));
    }
}
