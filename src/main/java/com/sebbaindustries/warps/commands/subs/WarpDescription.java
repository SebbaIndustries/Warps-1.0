package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.IPermission;
import com.sebbaindustries.warps.message.IMessage;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.Warp;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class WarpDescription extends ICommand {

    public WarpDescription() {
        super("description", "description set/remove [warp] <description>", 2);
        permissions().add(IPermission.ROOT, IPermission.COMMANDS, IPermission.DESCRIPTION);
        setPlayerOnly();
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final String argument = args[0].toLowerCase();
        final String name = args[1];
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (warp == null) {
            player.sendMessage(Core.gCore.message.get(IMessage.INVALID_WARP));
            return;
        }

        if (!warp.getOwner().equals(player)) {
            player.sendMessage(Core.gCore.message.get(IMessage.NOT_WARP_OWNER));
            return;
        }

        final String description = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

        if (Arrays.copyOfRange(args, 2, args.length).length > Core.gCore.settings.getWarpSettings(player).getMaxDescLength()) {
            player.sendMessage(Core.gCore.message.get(IMessage.TOO_LONG_WARP_DESCRIPTION));
            return;
        }

        switch (argument) {
            case "set":
                warp.setDescription(description);
                player.sendMessage(Replace.replaceString(Core.gCore.message.get(IMessage.SUCCESSFULLY_SET_DESCRIPTION)
                        , "{warp-description}", description, "{warp-name}", name));
                break;
            case "remove":
                /*
                TODO: add option for this
                 */
                warp.setDescription("/");
                player.sendMessage(Replace.replaceString(Core.gCore.message.get(IMessage.SUCCESSFULLY_REMOVED_DESCRIPTION)
                        , "{warp-name}", name));
                break;
            default:
                player.sendMessage(Core.gCore.message.get(IMessage.INVALID_COMMAND_ARGUMENT));
        }

    }
}
