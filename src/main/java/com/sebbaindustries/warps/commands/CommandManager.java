package com.sebbaindustries.warps.commands;

import com.sebbaindustries.warps.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * <b>This class contains command initializations</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public class CommandManager implements CommandExecutor, TabCompleter {

    /**
     * registers commands and creates new parser instance
     *
     * @param core Main class
     */
    public CommandManager(final @NotNull Core core) {
        Objects.requireNonNull(core.getCommand("warp")).setExecutor(this::onCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("warp")).setTabCompleter(this::onTabComplete);
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(final @NotNull CommandSender sender, final @NotNull Command cmd, final @NotNull String label, final String[] args) {
        return null;
    }

}
