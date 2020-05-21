package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.components.SafetyCheck;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.components.WarpLocation;
import com.sebbaindustries.warps.warp.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpChange extends ICommand {

    public WarpChange() {
        super("change", "change [warp] (status/location/owner) <input/x/y/z/pitch/yaw> {x/y/z/pitch/yaw}", 1);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS);
        setPlayerOnly();
    }

    /*
    TODO: Split each individual argument into it's own class once the command handler is adjusted
    TODO: Permission required for each executed action
     */

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final String name = args.length >= 1 ? args[0] : player.getName();
        final String argument = args.length >= 2 ? args[1].toLowerCase() : "status";
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (warp == null) {
            player.sendMessage(Core.gCore.message.get(EMessage.INVALID_WARP));
            return;
        }

        if (!warp.getOwner().equals(player)) {
            player.sendMessage(Core.gCore.message.get(EMessage.NOT_WARP_OWNER));
            return;
        }

        switch (argument) {
            case "status":
                final boolean status = args.length == 3 ? Boolean.getBoolean(args[2].toLowerCase()) : !warp.getAccessibility();
                /*
                @param boolean public/private
                @desc Toggles the warps status to public/private
                @placeholder {warp-name}
                @placeholder {warp-status}
                 */
                warp.setAccessibility(status);
                player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.CHANGED_WARP_STATUS)
                        ,"{warp-name}", warp.getName()
                        ,"{warp-status}", WarpUtils.getBooleanString(warp.getAccessibility())));
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
                        player.sendMessage(Core.gCore.message.get(EMessage.INVALID_LOCATION));
                        return;
                    }

                    /*
                    @placeholder {warp-location}
                     */
                    player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_CHANGED_LOCATION)
                            , "{warp-location}", WarpUtils.getLocationString(warpLocation)));
                    warp.setLocation(warpLocation);
                    return;
                }

                double subParameter;
                WarpLocation warpLocation;
                switch (parameter) {
                    case "x":
                        subParameter = args.length == 4 ? Double.valueOf(args[3]) : warp.getLocation().getX();
                        warpLocation = new WarpLocation(warp.getLocation().getWorld(), subParameter, warp.getLocation().getY(), warp.getLocation().getZ(), warp.getLocation().getYaw(), warp.getLocation().getPitch());
                        warp.setLocation(warpLocation);
                        player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_CHANGED_LOCATION)
                                ,"{warp-location}", WarpUtils.getLocationString(warpLocation)));
                        break;
                    case "y":
                        subParameter = args.length == 4 ? Double.valueOf(args[3]) : warp.getLocation().getY();
                        warpLocation = new WarpLocation(warp.getLocation().getWorld(), warp.getLocation().getX(), subParameter, warp.getLocation().getZ(), warp.getLocation().getYaw(), warp.getLocation().getPitch());
                        warp.setLocation(warpLocation);
                        player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_CHANGED_LOCATION)
                                ,"{warp-location}", WarpUtils.getLocationString(warpLocation)));
                        break;
                    case "z":
                        subParameter = args.length == 4 ? Double.valueOf(args[3]) : warp.getLocation().getZ();
                        warpLocation = new WarpLocation(warp.getLocation().getWorld(), warp.getLocation().getX(), warp.getLocation().getY(), subParameter, warp.getLocation().getYaw(), warp.getLocation().getPitch());
                        warp.setLocation(warpLocation);
                        player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_CHANGED_LOCATION)
                                ,"{warp-location}", WarpUtils.getLocationString(warpLocation)));
                        break;
                    case "yaw":
                        subParameter = args.length == 4 ? Double.valueOf(args[3]) : warp.getLocation().getYaw();
                        warpLocation = new WarpLocation(warp.getLocation().getWorld(), warp.getLocation().getX(), warp.getLocation().getY(), warp.getLocation().getZ(), (float) subParameter, warp.getLocation().getPitch());
                        warp.setLocation(warpLocation);
                        player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_CHANGED_LOCATION)
                                ,"{warp-location}", WarpUtils.getLocationString(warpLocation)));
                        break;
                    case "pitch":
                        subParameter = args.length == 4 ? Double.valueOf(args[3]) : warp.getLocation().getPitch();
                        warpLocation = new WarpLocation(warp.getLocation().getWorld(), warp.getLocation().getX(), warp.getLocation().getY(), warp.getLocation().getZ(), warp.getLocation().getYaw(), (float) subParameter);
                        warp.setLocation(warpLocation);
                        player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_CHANGED_LOCATION)
                                ,"{warp-location}", WarpUtils.getLocationString(warpLocation)));
                        break;
                }
                break;
            case "owner":
                final String targetName = args.length == 3 ? args[2] : null;

                if (targetName == null) {
                    player.sendMessage(Core.gCore.message.get(EMessage.INVALID_COMMAND_ARGUMENT));
                    return;
                }
                final Player target = Bukkit.getPlayerExact(targetName);

                if (target == null) {
                    player.sendMessage(Core.gCore.message.get(EMessage.INVALID_PLAYER));
                    return;
                }

                if (target.equals(player)) {
                    player.sendMessage(Core.gCore.message.get(EMessage.NEW_OWNER_CANNOT_BE_OLD_OWNER));
                    return;
                }

                warp.setOwner(target);
                /*
                @placeholder {warp-previous-owner}
                @placeholder {warp-new-owner}
                 */
                player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_CHANGED_WARP_OWNER)
                        , "{warp-previous-owner}", player.getName()
                        , "{warp-new-owner}", target.getName()));
                /*
                TODO: request some confirmation to ensure owner changes are intentional! TIMER @Nzd
                 */
                break;
        }

    }
}
