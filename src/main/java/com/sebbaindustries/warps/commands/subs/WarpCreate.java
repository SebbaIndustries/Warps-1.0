package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.settings.ESettings;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpUtils;
import com.sebbaindustries.warps.warp.components.SafetyCheck;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class WarpCreate extends ICommand {

    public WarpCreate() {
        super("create", "create [name] (SERVER/PLAYER)", 0);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.CREATE);
        setPlayerOnly();
    }

    @Override
    public void execute(final @NotNull CommandSender sender, final String[] args) {
        final Player player = (Player) sender;
        final String name = args.length >= 1 ? args[0] : player.getName();
        final Warp.Type type = args.length == 2 ? Warp.Type.valueOf(args[1].toUpperCase()) : Warp.Type.PLAYER;

        /*
         * Checks whether the warp name contains a blacklisted word
         *
         * @placeholder {warp-name}
         * @return blacklisted name message
         */
        if (Core.gCore.settings.getList(ESettings.BLACKLISTED_WARP_NAMES).contains(name.toLowerCase()) && !name.equalsIgnoreCase(player.getName())) {
            player.sendMessage(Replace.replaceString(
                    Core.gCore.message.get(EMessage.BLACKLISTED_WARP_NAME)
                    , "{warp-name}", name));
            return;
        }

        /*
         * Creates a new warp instance using the given arguments
         */
        final Warp warp = new Warp(type, player, name);

        /*
         * Checks if the warps location is a safe area
         *
         * @see SafetyCheck
         * @return invalid location message
         */
        if (!SafetyCheck.isLocationSafe(warp.getLocation())) {
            player.sendMessage(Core.gCore.message.get(EMessage.INVALID_LOCATION));
            return;
        }
        /*
         @placeholder warpMame = {warp-name}
         @placeholder location = {warp-world}
         @placeholder world = {warp-environment}
          */
        if (Core.gCore.warpStorage.addWarp(warp)) {
            CompletableFuture.runAsync(() -> writeWarpToDB(warp));
            player.sendMessage(Replace.replaceString(
                    Core.gCore.message.get(EMessage.SUCCESSFULLY_CREATED_WARP)
                    , "{warp-name}", warp.getName()
                    , "{warp-location}", WarpUtils.getLocationString(warp.getLocation())
                    , "{warp-world}", WarpUtils.getWorldString(warp.getLocation().getWorld())));
            return;
        }

        /*
         *
         * @placeholder warpName = {warp-name}
         * @placeholder reason = {reason} - Reason for failure to create the warp - beautified
         */
        player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.FAILED_WARP_CREATION)
                , "{warp-name}", name
                , "{reason}", getReason()));
    }

    /*
    TODO: Add actual reason creation (dynamic)
     */
    private String getReason() {
        return "warp already exists!";
    }

    private void writeWarpToDB(Warp warp) {
        Connection con = Core.gCore.connection.getConn();
        try {
            con.prepareStatement(
                    "INSERT INTO warps (id, owner, name) VALUES (" + warp.getID() + ", '" + warp.getOwner() + "', '" + warp.getName() + "');"
            ).executeUpdate();

            con.prepareStatement(
                    "INSERT INTO warp_info (ID, type, category, description, access) VALUES " +
                            "(" + warp.getID() + ", " + warp.getType().id + ", " + warp.getCategory().id + ", " +
                            "'" + warp.getDescription() + "', " + warp.getAccessibility() + ");"
            ).executeUpdate();

            con.prepareStatement(
                    "INSERT INTO warp_locations (ID, world, x, y, z, pitch, yaw) VALUES " +
                            "(" + warp.getID() + ", '" + warp.getLocation().getWorld().getName() + "', " +
                            "" + warp.getLocation().getX() + ", " +
                            "" + warp.getLocation().getY() + ", " +
                            "" + warp.getLocation().getX() + ", " +
                            "" + warp.getLocation().getPitch() + ", " +
                            "" + warp.getLocation().getYaw() + ");"
            ).executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
