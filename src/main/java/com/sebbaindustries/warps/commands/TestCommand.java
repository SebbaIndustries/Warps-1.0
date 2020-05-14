package com.sebbaindustries.warps.commands;

import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.IPermission;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TestCommand extends ICommand {


    public TestCommand() {
        super("argument", "usage", null);
        permissions().add(IPermission.ROOT, IPermission.COMMANDS, IPermission.CREATE);
    }

    /**
     * Abstract for executing commands
     *
     * @param sender Player or console instance
     * @param args   command arguments
     */
    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {

    }
}
