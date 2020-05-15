package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.IPermission;
import com.sebbaindustries.warps.message.IMessage;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.SafetyCheck;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpLocation;
import com.sebbaindustries.warps.warp.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpChange extends ICommand {

    public WarpChange() {
        super("change", "", 1);
        permissions().add(IPermission.ROOT);
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final String name = args.length >= 1 ? args[0] : player.getName();
        final String argument = args[1].toLowerCase();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (warp == null) {
            player.sendMessage(Core.gCore.message.get(IMessage.INVALID_WARP));
            return;
        }

        if (!warp.getOwner().equals(player)) {
            player.sendMessage(Core.gCore.message.get(IMessage.NOT_WARP_OWNER));
            return;
        }

        switch (argument) {
            case "status":
                /*
                TODO: add warp status
                @param boolean public/private
                @desc Toggles the warps status to public/private
                 */

                break;
            case "location":
                final String parameter = args.length >= 3 ? args[2].toLowerCase() : null;

                if (parameter == null) {
                    /*
                    Set the new warp location to the users location
                     */
                    final Location location = player.getLocation();
                    final WarpLocation warpLocation = new WarpLocation(player.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

                    if (!SafetyCheck.isLocationSafe(warpLocation)) {
                        player.sendMessage(Core.gCore.message.get(IMessage.INVALID_LOCATION));
                        return;
                    }

                    /*
                    @placeholder {warp-location}
                     */
                    player.sendMessage(Replace.replaceString(Core.gCore.message.get(IMessage.SUCCESSFULLY_CHANGED_LOCATION)
                            , "{warp-location}", WarpUtils.getLocationString(warpLocation)));
                    warp.setLocation(warpLocation);
                    return;
                }



                switch (parameter) {
                    case "x":

                        break;
                    case "y":
                        // change y
                        break;
                    case "z":
                        // change z
                        break;
                    /*
                    Add yaw and pitch change perhaps?
                     */
                }

                break;
            case "owner":
                final String targetName = args.length == 3 ? args[2] : null;

                if (targetName == null) {
                    player.sendMessage(Core.gCore.message.get(IMessage.INVALID_COMMAND_ARGUMENT));
                    return;
                }
                final Player target = Bukkit.getPlayerExact(targetName);

                if (target == null) {
                    player.sendMessage(Core.gCore.message.get(IMessage.INVALID_PLAYER));
                    return;
                }

                if (target.equals(player)) {
                    player.sendMessage(Core.gCore.message.get(IMessage.NEW_OWNER_CANNOT_BE_OLD_OWNER));
                    return;
                }

                warp.setOwner(target);
                /*
                @placeholder {warp-previous-owner}
                @placeholder {warp-new-owner}
                 */
                player.sendMessage(Replace.replaceString(Core.gCore.message.get(IMessage.SUCCESSFULLY_CHANGED_WARP_OWNER)
                        , "{warp-previous-owner}", player.getName()
                        , "{warp-new-owner}", target.getName()));
                /*
                TODO: send owner change message, request some confirmation to ensure owner changes are intentional!
                 */
                break;
        }

    }
}
