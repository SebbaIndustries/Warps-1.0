package com.sebbaindustries.warps.commands.actions;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ModifyWarp extends ICommand {

    public ModifyWarp() {
        super("modifywarp", "usage", 1);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS);
        setPlayerOnly();
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-o")) {
                modifyOfficialWarp(player, args);
                return;
            }
            if (arg.equalsIgnoreCase("-a")) {
                modifyPlayerBypassWarp(player, args);
                return;
            }
        }

        modifyPlayerWarp(player, args);
    }

    private void modifyPlayerWarp(@NotNull final Player p, final String[] args) {
        final String name = args.length >= 1 ? args[0] : p.getName();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (warp == null) {
            p.sendMessage(Core.gCore.message.get(EMessage.INVALID_WARP));
            return;
        }

        if (!warp.getOwner().equalsIgnoreCase(p.getName())) {
            p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.NOT_WARP_OWNER)
                    , "{warp-owner}", warp.getOwner()));
            return;
        }

        final String type = args.length >= 2 ? args[1] : "status";
        switch (type) {
            case "description":
                final String description = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

                if (Arrays.copyOfRange(args, 2, args.length).length > Core.gCore.settings.getWarpSettings(p).getMaxDescLength()) {
                    p.sendMessage(Core.gCore.message.get(EMessage.TOO_LONG_WARP_DESCRIPTION));
                    return;
                }

                warp.setDescription(description);
                Core.gCore.warpStorage.updateWarp(warp);
                if (description.length() == 0) {
                    p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_REMOVED_DESCRIPTION)
                            , "{warp-name}", name));
                } else {
                    p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_SET_DESCRIPTION)
                            , "{warp-description}", description, "{warp-name}", name));
                }
                break;
            case "category":
                final Warp.Category category = args.length == 3 ? Warp.Category.valueOf(args[2].toUpperCase()) : Warp.Category.UNDEFINED;

                warp.setCategory(category);
                Core.gCore.warpStorage.updateWarp(warp);
                p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SET_WARP_CATEGORY)
                        , "{warp-category}", WarpUtils.getFormattedCategory(category), "{warp-name}", name));
                break;
            case "status":
                final boolean status = args.length == 3 ? WarpUtils.getBooleanValue(args[2].toLowerCase()) : !warp.getAccessibility();

                warp.setAccessibility(status);
                Core.gCore.warpStorage.updateWarp(warp);
                p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.CHANGED_WARP_STATUS)
                        , "{warp-name}", warp.getName()
                        , "{warp-status}", WarpUtils.getBooleanString(warp.getAccessibility())));
                break;
            case "owner":
                final String targetName = args.length == 3 ? args[2] : null;

                if (targetName == null) {
                    p.sendMessage(Core.gCore.message.get(EMessage.INVALID_COMMAND_ARGUMENT));
                    return;
                }
                final Player target = Bukkit.getPlayerExact(targetName);

                if (target == null) {
                    p.sendMessage(Core.gCore.message.get(EMessage.INVALID_PLAYER));
                    return;
                }

                if (target.equals(p)) {
                    p.sendMessage(Core.gCore.message.get(EMessage.NEW_OWNER_CANNOT_BE_OLD_OWNER));
                    return;
                }

                warp.setOwner(target);
                Core.gCore.warpStorage.updateWarp(warp);
                p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_CHANGED_WARP_OWNER)
                        , "{warp-previous-owner}", p.getName()
                        , "{warp-new-owner}", target.getName()));
                break;
        }
    }

    private void modifyPlayerBypassWarp(@NotNull final Player p, final String[] args) {
        final String name = args.length >= 1 ? args[0] : p.getName();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);
        if (!p.hasPermission("warps.command.modifywarp.admin")) {
            return;
        }

        if (warp == null) {
            p.sendMessage(Core.gCore.message.get(EMessage.INVALID_WARP));
            return;
        }

        final String type = args.length >= 2 ? args[1] : "status";
        switch (type) {
            case "description":
                final String description = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

                if (Arrays.copyOfRange(args, 2, args.length).length > Core.gCore.settings.getWarpSettings(p).getMaxDescLength()) {
                    p.sendMessage(Core.gCore.message.get(EMessage.TOO_LONG_WARP_DESCRIPTION));
                    return;
                }

                warp.setDescription(description);
                Core.gCore.warpStorage.updateWarp(warp);
                if (description.length() == 0) {
                    p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_REMOVED_DESCRIPTION)
                            , "{warp-name}", name));
                } else {
                    p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_SET_DESCRIPTION)
                            , "{warp-description}", description, "{warp-name}", name));
                }
                break;
            case "category":
                final Warp.Category category = args.length == 3 ? Warp.Category.valueOf(args[2].toUpperCase()) : Warp.Category.UNDEFINED;

                warp.setCategory(category);
                Core.gCore.warpStorage.updateWarp(warp);
                p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SET_WARP_CATEGORY)
                        , "{warp-category}", WarpUtils.getFormattedCategory(category), "{warp-name}", name));
                break;
            case "status":
                final boolean status = args.length == 3 ? WarpUtils.getBooleanValue(args[2].toLowerCase()) : !warp.getAccessibility();

                warp.setAccessibility(status);
                Core.gCore.warpStorage.updateWarp(warp);
                p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.CHANGED_WARP_STATUS)
                        , "{warp-name}", warp.getName()
                        , "{warp-status}", WarpUtils.getBooleanString(warp.getAccessibility())));
                break;
            case "owner":
                final String targetName = args.length == 3 ? args[2] : null;

                if (targetName == null) {
                    p.sendMessage(Core.gCore.message.get(EMessage.INVALID_COMMAND_ARGUMENT));
                    return;
                }
                final Player target = Bukkit.getPlayerExact(targetName);

                if (target == null) {
                    p.sendMessage(Core.gCore.message.get(EMessage.INVALID_PLAYER));
                    return;
                }

                if (target.equals(p)) {
                    p.sendMessage(Core.gCore.message.get(EMessage.NEW_OWNER_CANNOT_BE_OLD_OWNER));
                    return;
                }

                warp.setOwner(target);
                Core.gCore.warpStorage.updateWarp(warp);
                p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_CHANGED_WARP_OWNER)
                        , "{warp-previous-owner}", p.getName()
                        , "{warp-new-owner}", target.getName()));
                break;
        }
    }

    private void modifyOfficialWarp(@NotNull final Player p, final String[] args) {
        final String name = args.length >= 1 ? args[0] : p.getName();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);
        if (!p.hasPermission("warps.command.modifywarp.official")) {
            return;
        }

        if (warp == null || warp.getType() != Warp.Type.SERVER) {
            p.sendMessage(Core.gCore.message.get(EMessage.INVALID_WARP));
            return;
        }

        final String type = args.length >= 2 ? args[2] : "status";
        switch (type) {
            case "description":
                final String description = String.join(" ", Arrays.copyOfRange(args, 3, args.length));

                if (Arrays.copyOfRange(args, 3, args.length).length > Core.gCore.settings.getWarpSettings(p).getMaxDescLength()) {
                    p.sendMessage(Core.gCore.message.get(EMessage.TOO_LONG_WARP_DESCRIPTION));
                    return;
                }

                warp.setDescription(description);
                Core.gCore.warpStorage.updateWarp(warp);
                if (description.length() == 0) {
                    p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_REMOVED_DESCRIPTION)
                            , "{warp-name}", name));
                } else {
                    p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_SET_DESCRIPTION)
                            , "{warp-description}", description, "{warp-name}", name));
                }
                break;
            case "category":
                final Warp.Category category = args.length == 4 ? Warp.Category.valueOf(args[3].toUpperCase()) : Warp.Category.UNDEFINED;

                warp.setCategory(category);
                Core.gCore.warpStorage.updateWarp(warp);
                p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SET_WARP_CATEGORY)
                        , "{warp-category}", WarpUtils.getFormattedCategory(category), "{warp-name}", name));
                break;
        }
    }
}
