package com.sebbaindustries.warps.commands.actions;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.database.DBWarpUtils;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.settings.ESettings;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpUtils;
import com.sebbaindustries.warps.warp.components.SafetyCheck;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class SetWarp extends ICommand {

    /**
     * Constructor that creates ICommand instance with all necessary arguments
     */
    public SetWarp() {
        super("setwarp", "usage", 0);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.CREATE);
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
                createOfficialWarp(player, args);
                return;
            }
            if (arg.equalsIgnoreCase("-a")) {
                player.sendMessage(Core.gCore.message.get(EMessage.INVALID_COMMAND_ARGUMENT));
                return;
            }
        }
        createPlayerWarp(player, args);
    }

    private void createOfficialWarp(@NotNull Player p, String[] args) {
        if (!p.hasPermission("warps.command.setwarp.official")) {
            return;
        }
        String name = null;
        for (String arg : args) {
            if (!arg.contains("-")) {
                name = arg;
                break;
            }
        }
        if (name == null) {
            System.out.println("NULL NAME " + Arrays.toString(args));
            return;
        }

        final Warp warp = new Warp(Warp.Type.SERVER, p, name);

        addWarp(p, warp);
    }

    private void createPlayerWarp(@NotNull Player p, String[] args) {
        final String name = args.length >= 1 ? args[0] : p.getName();
        /*
         * Checks whether the warp name contains a blacklisted word
         *
         * @placeholder {warp-name}
         * @return blacklisted name message
         */
        if (Core.gCore.settings.getList(ESettings.BLACKLISTED_WARP_NAMES).contains(name.toLowerCase()) && !name.equalsIgnoreCase(p.getName())) {
            p.sendMessage(Replace.replaceString(
                    Core.gCore.message.get(EMessage.BLACKLISTED_WARP_NAME)
                    , "{warp-name}", name));
            return;
        }
        /*
         * Creates a new player warp instance using the given arguments
         */
        final Warp warp = new Warp(Warp.Type.PLAYER, p, name);

        /*
         * Checks if the warps location is a safe area
         *
         * @see SafetyCheck
         * @return invalid location message
         */
        if (!SafetyCheck.isLocationSafe(warp.getLocation())) {
            p.sendMessage(Core.gCore.message.get(EMessage.INVALID_LOCATION));
            return;
        }
        addWarp(p, warp);
    }

    private String getReason() {
        return "warp already exists!";
    }

    private void addWarp(Player p, Warp warp) {
        if (Core.gCore.warpStorage.addWarp(warp)) {
            CompletableFuture.runAsync(() -> DBWarpUtils.createNewWarp(warp));
            p.sendMessage(Replace.replaceString(
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
        p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.FAILED_WARP_CREATION)
                , "{warp-name}", warp.getName()
                , "{reason}", getReason()));
    }

}
