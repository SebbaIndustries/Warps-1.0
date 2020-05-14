package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.commands.creator.ICommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WarpDelete extends ICommand {

    public WarpDelete() {
        super("delete", "delete [warp]", 1);
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {

    }
}
