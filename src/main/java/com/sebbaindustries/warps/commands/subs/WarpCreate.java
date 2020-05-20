package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.settings.ESettings;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.components.SafetyCheck;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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

}
