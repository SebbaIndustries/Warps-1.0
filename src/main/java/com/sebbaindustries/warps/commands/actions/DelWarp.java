package com.sebbaindustries.warps.commands.actions;

import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class DelWarp extends ICommand {

    /**
     * Constructor that creates ICommand instance with all necessary arguments
     *
     */
    protected DelWarp() {
        super("delwarp", "usage", 0);
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
                adminArguments(player, args);
                return;
            }
        }
        deletePlayerWarp(player, args);
    }

    private void deleteOfficialWarp(Player p, String[] args) {

    }

    private void deletePlayerWarp(Player p, String[] args) {

    }

    private void adminArguments(Player p, String[] args) {

    }
}
