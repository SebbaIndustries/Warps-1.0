package com.sebbaindustries.warps.commands.creator;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.subs.*;
import com.sebbaindustries.warps.utils.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <b>This class contains command handling and executing to it's sub-classes</b><br>
 *
 * @author sebbaindustries
 * @version 1.0
 */
public class CommandFactory implements CommandExecutor {

    private final Set<ICommand> iCommands;
    private final ICommand defaultICommand;

    /**
     * Main constructor, registers commands and it's sub classes <br>
     * <b>IF YOU DARE TO CREATE NEW ICommandHandler INSTANCE IN OTHER PLACE THAN GlobalCore I WILL PERSONALLY REMOVE YOU FROM EARTH.</b>
     *
     * @param core Core instance
     */
    public CommandFactory(final @NotNull Core core) {
        // Register commands
        Objects.requireNonNull(core.getCommand("warp")).setExecutor(this);

        // Register sub-commands
        iCommands = Stream.of(
                new WarpCreate(),
                new WarpChange(),
                new WarpDelete(),
                new WarpTeleportation(),
                new WarpsMenu(),
                new WarpRate(),
                new SettingsDebug()
                ).collect(Collectors.toSet());
        // Find "default" command
        defaultICommand = iCommands.stream().filter(ICommand::isDef).findAny().orElseThrow(NoDefaultCommandException::new);
        // Check if every command has at least 1 permission attached to them
        iCommands.forEach(iCommand -> {
            if (iCommand.permissions().getPermissions().isEmpty()) throw new NoPermissionSetCommandException();
        });
    }

    /**
     * @param sender  Player or console instance
     * @param command Command class
     * @param label   todo open gui if label == "warps"
     * @param args    command arguments
     * @return True ALWAYS FUCKING true, if you change this I quit.
     * @see CommandExecutor
     */
    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {

        // Check length if there is no arguments execute base command,
        if (args.length == 0) {
            defaultICommand.execute(sender, args);
            return true;
        }

        // Check if first argument equals to any subs
        final Optional<ICommand> optionalCommand = iCommands.stream().filter(cmd -> cmd.getArgument().equalsIgnoreCase(args[0])).findAny();

        // TODO remove this add check for warps here
        if (!optionalCommand.isPresent()) {
            sender.sendMessage(Color.chat("&cUnknown Command.")); //plugin.getConfig().getString("settings.unknown-command")));
            return true;
        }

        final ICommand cmd = optionalCommand.get();

        // Check if console can execute this sub-command
        // TODO add same thing for players
        if (cmd.isPlayerOnly() && !(sender instanceof Player)) {
            sender.sendMessage(Color.chat("&cThis command can not be executed in console!")); //plugin.getConfig().getString("settings.player-only")));
            return true;
        }

        // Check if player has permission to execute this sub-command
        if (!checkPermissions(cmd, sender)) {
            sender.sendMessage(Color.chat("&cYou do not have permission to execute this command Jimbo.")); // plugin.getConfig().getString("settings.no-permission")));
            return true;
        }

        // Check if usage is correct (prevents some nasty NPEs)
        if (cmd.getMinArgs() > Arrays.copyOfRange(args, 1, args.length).length || cmd.getMaxArgs() < Arrays.copyOfRange(args, 1, args.length).length) {
            sender.sendMessage(Color.chat("Invalid Usage! Usage /warps " + cmd.getUsage()));
            return true;
        }

        // Finally execute this sub-command
        cmd.execute(sender, Arrays.copyOfRange(args, 1, args.length));
        return true;

    }

    /**
     * Checks permissions for the player and returns boolean <br>
     *
     * @param iCommand command template instance
     * @param sender   Player or console instance
     * @return True if player has at least 1 permission for the sub-command, false if player has none #SadTimes
     * @see ICommand
     * Perfected by Frosty
     */
    private boolean checkPermissions(ICommand iCommand, CommandSender sender) {
        return iCommand.permissions().getPermissions().stream().anyMatch(perm -> sender.hasPermission(perm.permission));
    }

}
