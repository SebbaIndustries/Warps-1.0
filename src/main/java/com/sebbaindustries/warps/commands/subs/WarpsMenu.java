package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WarpsMenu extends ICommand {

    public WarpsMenu() {
        super("menu", "menu", null);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.MENU);
        setDef();
        setPlayerOnly();
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {

    }
}
