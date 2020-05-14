package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.commands.creator.ICommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WarpCreate extends ICommand {

    public WarpCreate() {
        super("create", "create [name]", 1);
    }

    @Override
    public void execute(final @NotNull CommandSender sender, final String[] args) {

    }

}
