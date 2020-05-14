package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpChange extends ICommand {

    public WarpChange() {
        super("change", "", 1);
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final String name = args.length >= 1 ? args[0] : player.getName();
        final String argument = args[1].toLowerCase();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (warp == null) {
            /*
             TODO: Invalid warp message
             */

            return;
        }

        if (!warp.getOwner().equals(player)) {
            /*
            TODO: Not warp owner message
             */

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
                /*
                TODO: rename "thing" variable to something more appropriate
                 */
                final String thing = args.length >= 3 ? args[2].toLowerCase() : null;

                if (thing == null) {
                    /*
                    Set the new warp location to the users location
                     */
                    final Location location = player.getLocation();
                    final WarpLocation warpLocation = new WarpLocation(player.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

                    /*
                    TODO: add safety check when assigning the new location, add location change message
                     */
                    warp.setLocation(warpLocation);
                    return;
                }

                switch (thing) {
                    case "x":
                        // change x
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
                    /*
                    TODO: send invalid arg message
                     */
                    return;
                }
                final Player target = Bukkit.getPlayerExact(targetName);

                if (target == null) {
                    /*
                    TODO: send invalid player message
                     */
                    return;
                }

                warp.setOwner(target);
                /*
                TODO: send owner change message, request some confirmation to ensure owner changes are intentional!
                 */
                break;
        }

    }
}
