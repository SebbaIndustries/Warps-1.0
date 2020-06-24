package com.sebbaindustries.warps.commands.actions;

import com.google.common.primitives.Ints;
import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpUtils;
import com.sebbaindustries.warps.warp.components.SafetyCheck;
import com.sebbaindustries.warps.warp.components.WarpLocation;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MoveWarp extends ICommand {

    /**
     * Constructor that creates ICommand instance with all necessary arguments
     */
    public MoveWarp() {
        super("movewarp", "usage", 1);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.MOVE);
        setPlayerOnly();
    }

    /**
     * Abstract for executing commands
     *
     * @param sender Player or console instance
     * @param args   command arguments
     */
    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-o")) {
                moveOfficialWarp(player, args);
                return;
            }
            if (arg.equalsIgnoreCase("-a")) {
                movePlayerBypassWarp(player, args);
                return;
            }
        }

        movePlayerWarp(player, args);
    }

    private void moveOfficialWarp(@NotNull final Player p, final String[] args) {
        final String name = args.length >= 1 ? args[0] : p.getName();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (!p.hasPermission("warps.command.movewarp.official")) {
            return;
        }

        if (warp == null || warp.getType() != Warp.Type.SERVER) {
            p.sendMessage(Core.gCore.message.get(EMessage.INVALID_WARP));
            return;
        }

        performWarpMove(args, p, warp);
    }

    private void movePlayerBypassWarp(@NotNull final Player p, final String[] args) {
        final String name = args.length >= 1 ? args[0] : p.getName();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);
        if (!p.hasPermission("warps.command.movewarp.admin")) {
            return;
        }

        if (warp == null) {
            p.sendMessage(Core.gCore.message.get(EMessage.INVALID_WARP));
            return;
        }

        performWarpMove(args, p, warp);
    }

    private void performWarpMove(final String[] args, final Player p, final Warp warp) {
        final String type = args.length >= 3 ? args[2] : null;
        if (type == null) {
            p.sendMessage(Core.gCore.message.get(EMessage.INVALID_COMMAND_ARGUMENT));
            return;
        }

        final Location location = p.getLocation();
        WarpLocation warpLocation;
        if (type.equalsIgnoreCase("location")) {
            warpLocation = new WarpLocation(p.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        } else {
            warpLocation = new WarpLocation(p.getWorld(), Ints.tryParse(args[2]), Ints.tryParse(args[3]), Ints.tryParse(args[4]), location.getYaw(), location.getPitch());
        }

        setWarpLocation(warp, warpLocation, p);
    }

    private void setWarpLocation(final Warp warp, final WarpLocation warpLocation, final Player p) {
        warp.setLocation(warpLocation);
        Core.gCore.warpStorage.updateWarp(warp);
        p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_CHANGED_LOCATION)
                , "{warp-location}", WarpUtils.getLocationString(warpLocation)));
    }

    private void movePlayerWarp(@NotNull final Player p, final String[] args) {
        final String name = args.length >= 1 ? args[0] : p.getName();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (warp == null) {
            Core.gCore.message.get(EMessage.INVALID_WARP);
            return;
        }

        if (!warp.getOwner().equalsIgnoreCase(p.getName())) {
            p.sendMessage(Core.gCore.message.get(EMessage.NOT_WARP_OWNER));
            return;
        }

        final Location location = p.getLocation();
        final WarpLocation warpLocation = new WarpLocation(p.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        if (!SafetyCheck.isLocationSafe(warpLocation)) {
            p.sendMessage(Core.gCore.message.get(EMessage.INVALID_LOCATION));
            return;
        }

        setWarpLocation(warp, warpLocation, p);
    }
}
