package com.sebbaindustries.warps.commands.actions;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.database.DBWarpUtils;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DelWarp extends ICommand {

    /**
     * Constructor that creates ICommand instance with all necessary arguments
     */
    public DelWarp() {
        super("delwarp", "/delwarp <warp>", 0);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.DELETE);
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
                deleteOfficialWarp(player, args);
                return;
            }
            if (arg.equalsIgnoreCase("-a")) {
                deletePlayerBypassWarp(player, args);
                return;
            }
        }
        deletePlayerWarp(player, args);
    }

    private void deleteOfficialWarp(Player p, String[] args) {
        final String name = args.length == 2 ? args[1] : p.getName();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (!p.hasPermission("warps.command.delwarp.official")) {
            return;
        }

        if (warp == null || warp.getType() != Warp.Type.SERVER) {
            p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.INVALID_WARP), "{warp-name}", name));
            return;
        }

        if (Core.gCore.warpStorage.deleteWarp(name)) {
            delWarp(warp);
            p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_REMOVED_WARP)
                    , "{warp-name}", name
                    , "{warp-location}", WarpUtils.getLocationString(warp.getLocation())
                    , "{warp-world}", warp.getLocation().getWorld().getName()));
            return;
        }

        p.sendMessage(Core.gCore.message.get(EMessage.FAILED_TO_REMOVE_WARP));
    }

    private void deletePlayerWarp(Player p, String[] args) {
        final String name = args.length == 1 ? args[0] : p.getName();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (warp == null) {
            p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.INVALID_WARP), "{warp-name}", name));
            return;
        }

        if (!warp.getOwner().equalsIgnoreCase(p.getName())) {
            p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.NOT_WARP_OWNER)
                    , "{warp-name}", name
                    , "{warp-owner}", warp.getOwner()));
            return;
        }

        if (Core.gCore.warpStorage.deleteWarp(name)) {
            delWarp(warp);
            p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_REMOVED_WARP)
                    , "{warp-name}", name
                    , "{warp-location}", WarpUtils.getLocationString(warp.getLocation())
                    , "{warp-world}", warp.getLocation().getWorld().getName()));
            return;
        }

        p.sendMessage(Core.gCore.message.get(EMessage.FAILED_TO_REMOVE_WARP));
    }

    private void deletePlayerBypassWarp(Player p, String[] args) {
        final String name = args.length == 2 ? args[1] : p.getName();
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (!p.hasPermission("warps.command.delwarp.admin")) {
            return;
        }

        if (warp == null) {
            p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.INVALID_WARP), "{warp-name}", name));
            return;
        }

        if (Core.gCore.warpStorage.deleteWarp(name)) {
            delWarp(warp);
            p.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SUCCESSFULLY_REMOVED_WARP)
                    , "{warp-name}", name
                    , "{warp-location}", WarpUtils.getLocationString(warp.getLocation())
                    , "{warp-world}", warp.getLocation().getWorld().getName()));
            return;
        }

        p.sendMessage(Core.gCore.message.get(EMessage.FAILED_TO_REMOVE_WARP));
    }

    private void delWarp(Warp warp) {
        CompletableFuture.supplyAsync(() -> {
            DBWarpUtils.deleteWarp(warp);
            return null;
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }
}
