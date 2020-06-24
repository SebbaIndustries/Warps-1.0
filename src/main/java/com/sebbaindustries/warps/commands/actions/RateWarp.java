package com.sebbaindustries.warps.commands.actions;

import com.google.common.primitives.Ints;
import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.events.WarpRateEvent;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.warp.Warp;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RateWarp extends ICommand {

    /**
     * Constructor that creates ICommand instance with all necessary arguments
     */
    public RateWarp() {
        super("ratewarp", "usage", 2);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.RATE);
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
            if (arg.equalsIgnoreCase("-o") || arg.equalsIgnoreCase("-a")) {
                player.sendMessage(Core.gCore.message.get(EMessage.INVALID_COMMAND_ARGUMENT));
                return;
            }
        }

        ratePlayerWarp(player, args);
    }

    @SuppressWarnings("UnstableApiUsage")
    private void ratePlayerWarp(@NotNull final Player p, final String[] args) {
        final String name = args.length >= 1 ? args[0] : p.getName();
        final int rate = Ints.constrainToRange(Ints.tryParse(args[1]), 0, 10);
        final Warp warp = Core.gCore.warpStorage.getWarp(name);

        if (warp == null) {
            p.sendMessage(Core.gCore.message.get(EMessage.INVALID_WARP));
            return;
        }

        if (!warp.getOwner().equalsIgnoreCase(p.getName())) {
            p.sendMessage(Core.gCore.message.get(EMessage.CANT_RATE_OWN_WARP));
            return;
        }

        if (!warp.getAccessibility()) {
            p.sendMessage(Core.gCore.message.get(EMessage.PRIVATE_WARP_RATING));
            return;
        }

        Bukkit.getServer().getPluginManager().callEvent(new WarpRateEvent(p, warp, rate));
    }
}
