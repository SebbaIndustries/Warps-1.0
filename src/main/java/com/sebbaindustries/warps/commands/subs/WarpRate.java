package com.sebbaindustries.warps.commands.subs;

import com.google.common.primitives.Ints;
import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.Warp;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpRate extends ICommand {

    public WarpRate() {
        super("rate", "rate [warp] <1-10>", 2);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.RATE);
        setPlayerOnly();
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final String name = args.length >= 1 ? args[0] : player.getName();
        final int rate = Ints.constrainToRange(Ints.tryParse(args[1]), 0, 10);
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (warp == null) {
            player.sendMessage(Core.gCore.message.get(EMessage.INVALID_WARP));
            return;
        }

        if (!warp.getOwner().equals(player)) {
            player.sendMessage(Core.gCore.message.get(EMessage.CANT_RATE_OWN_WARP));
            return;
        }

        if (!warp.getAccessibility()) {
            player.sendMessage(Core.gCore.message.get(EMessage.PRIVATE_WARP_RATING));
            return;
        }

        warp.setRating(player.getUniqueId(), rate);
        player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.RATED_WARP)
                , "{warp-owner}", warp.getOwner().getName()
                , "{warp-name}", warp.getName()
                , "{rating}", String.valueOf(rate)));
        // rated warp (message & broadcast)
    }
}
