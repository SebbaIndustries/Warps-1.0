package com.sebbaindustries.warps.commands.creator;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.actions.*;
import com.sebbaindustries.warps.commands.creator.completion.*;
import com.sebbaindustries.warps.utils.Color;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpUtils;
import com.sebbaindustries.warps.warp.components.SafetyCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <b>This class contains command handling and executing to it's sub-classes</b><br>
 *
 * @author Sebba, Frcsty
 * @version 1.0
 */
public class CommandFactory implements CommandExecutor {

    private final Set<ICommand> iCommands;

    /**
     * Main constructor, registers commands and it's sub classes <br>
     * <b>IF YOU DARE TO CREATE NEW ICommandHandler INSTANCE IN OTHER PLACE THAN GlobalCore I WILL PERSONALLY REMOVE YOU FROM EARTH.</b>
     *
     * @param core Core instance
     */
    public CommandFactory(final @NotNull Core core) {
        // Register commands
        Objects.requireNonNull(core.getCommand("warps")).setExecutor(this);
        Objects.requireNonNull(core.getCommand("reloadwarps")).setExecutor(this);

        final PluginCommand warp = core.getCommand("warp");

        Objects.requireNonNull(warp).setExecutor(this);
        Objects.requireNonNull(warp).setTabCompleter(new WarpCompletion());

        final PluginCommand set = core.getCommand("setwarp");

        Objects.requireNonNull(set).setExecutor(this);
        Objects.requireNonNull(set).setTabCompleter(new SetCompletion());

        final PluginCommand delete = core.getCommand("delwarp");

        Objects.requireNonNull(delete).setExecutor(this);
        Objects.requireNonNull(delete).setTabCompleter(new DeleteCompletion());

        final PluginCommand move = core.getCommand("movewarp");

        Objects.requireNonNull(move).setExecutor(this);
        Objects.requireNonNull(move).setTabCompleter(new MoveCompletion());

        final PluginCommand modify = core.getCommand("modifywarp");

        Objects.requireNonNull(modify).setExecutor(this);
        Objects.requireNonNull(modify).setTabCompleter(new ModifyCompletion());

        final PluginCommand rate = core.getCommand("ratewarp");

        final PluginCommand list = core.getCommand("listwarps");

        Objects.requireNonNull(list).setExecutor(this);
        Objects.requireNonNull(list).setTabCompleter(new ListCompletion());

        // Register sub-commands
        iCommands = Stream.of(
                new SetWarp(),
                new MoveWarp(),
                new ModifyWarp(),
                new ListWarps(),
                new DelWarp(),
                new WarpTeleportation(),
                new ReloadWarps(),
                new WarpsMenu(core)
        ).collect(Collectors.toSet());
        // Check if every command has at least 1 permission attached to them
        iCommands.forEach(iCommand -> {
            if (iCommand.permissions().getPermissions().isEmpty()) throw new NoPermissionSetCommandException();
        });
    }

    /**
     * @param sender  Player or console instance
     * @param command Command class
     * @param label   command label
     * @param args    command arguments
     * @return True ALWAYS FUCKING true, if you change this I quit.
     * @see CommandExecutor
     */
    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        // Check if first argument equals to any subs
        final Optional<ICommand> baseCommand = iCommands.stream().filter(cmd -> cmd.getArgument().equalsIgnoreCase(command.getName())).findAny();

        if (!baseCommand.isPresent()) {
            sender.sendMessage("Invalid command!");
            return true;
        }

        final ICommand cmd = baseCommand.get();

        if (cmd.isDef()) {
            cmd.execute(sender, args);
            return true;
        }

        if (cmd.getArgument().equalsIgnoreCase("warp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You cannot use this through console!");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage("Missing arguments /warp <warp>");
                return true;
            }
            final Warp warp = Core.gCore.warpStorage.getWarp(args[0]);

            if (warp == null) {
                sender.sendMessage("Invalid warp!");
                return true;
            }

            if (!warp.getAccessibility()) {
                sender.sendMessage("Private warp");
                return true;
            }

            if (!SafetyCheck.isLocationSafe(warp.getLocation())) {
                sender.sendMessage("Invalid warp location!");
                return true;
            }
            ((Player) sender).teleport(WarpUtils.convertWarpLocation(warp.getLocation()));
            // teleport player to warp (add safety check)

            return true;
        }

        // Check if console can execute this sub-command
        // TODO add same thing for players
        if (cmd.isPlayerOnly() && !(sender instanceof Player)) {
            sender.sendMessage(Color.chat("&cThis command can not be executed in console!"));
            return true;
        }

        // Check if player has permission to execute this sub-command
        if (!checkPermissions(cmd, sender)) {
            sender.sendMessage(Color.chat("&cYou do not have permission to execute this command Jimbo."));
            return true;
        }

        // Check if usage is correct (prevents some nasty NPEs)
        if (cmd.getMinArgs() > args.length) {
            sender.sendMessage(Color.chat("Invalid Usage! Usage " + cmd.getUsage()));
            return true;
        }

        // Finally execute this command
        cmd.execute(sender, args);
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
