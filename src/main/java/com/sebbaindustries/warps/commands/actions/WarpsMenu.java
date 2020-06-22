package com.sebbaindustries.warps.commands.actions;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.interfaces.menu.SelectorMenu;
import com.sebbaindustries.warps.utils.gui.guis.Gui;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpsMenu extends ICommand {

    private final Core core;

    public WarpsMenu(final Core core) {
        super("menu", "menu", 0);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.MENU);
        setPlayerOnly();
        setDef();

        this.core = core;
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Gui gui = SelectorMenu.getSelectorMenu(core);

        gui.open((Player) sender);
    }
}
