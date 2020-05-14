package com.sebbaindustries.warps.commands;

import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.IPermission;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TestCommand extends ICommand {

    public TestCommand() {
        super("argument", "usage", 4);
        permissions().addPermission(IPermission.ROOT).addPermission(IPermission.COMMANDS);
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {

    }

}
