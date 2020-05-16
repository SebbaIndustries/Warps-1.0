package com.sebbaindustries.warps.commands.subs;

import com.google.common.primitives.Ints;
import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.IPermission;
import com.sebbaindustries.warps.warp.Warp;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpRate extends ICommand {

    public WarpRate() {
        super("rate", "rate [warp] <1-10>", 2);
        permissions().add(IPermission.ROOT, IPermission.COMMANDS, IPermission.RATE);
        setPlayerOnly();
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final String name = args.length >= 1 ? args[0] : player.getName();
        final int rate = Ints.constrainToRange(Ints.tryParse(args[1]), 0, 10);
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (warp == null) {
            // Invalid warp
            return;
        }

        warp.setRating(player.getUniqueId(), rate);
        // rated warp (message & broadcast)
    }
}
