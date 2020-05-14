package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.utils.Color;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.Warp;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpDelete extends ICommand {

    public WarpDelete() {
        super("delete", "delete [warp]", 1);
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final String name = args.length == 1 ? args[0] : player.getName();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (warp == null) {
            /*
            TODO: Invalid warp message
            @placeholder warpName = {warp-name}
             */
            return;
        }

        if (!warp.getOwner().equals(player)) {
            /*
            TODO: Not owner message
            @placeholder warpName = {warp-name}
            @placeholder warpOwner = {warp-owner}
             */
            return;
        }

        if (Core.gCore.warpStorage.deleteWarp(name)) {
            /*
            TODO: Beautify warp location (toString)
            @placeholder warpName = {warp-name}
            @placeholder warpLocation = {warp-location}
            */
            player.sendMessage(Color.chat(Replace.replaceString("Some remove message", "{warp-name}", name, "{warp-location}", warp.getLocation().toString())));
            return;
        }

        /*
        TODO: Add some message of why the deletion failed
         */
        player.sendMessage(Color.chat("Failed to remove warp"));
    }
}
