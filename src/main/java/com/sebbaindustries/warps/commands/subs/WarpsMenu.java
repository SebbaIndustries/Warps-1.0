package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.interfaces.menu.WarpMenu;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.utils.gui.guis.PaginatedGui;
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
        final PaginatedGui gui = WarpMenu.getWarpMenu(core);

        if (gui == null) {
            sender.sendMessage(Core.gCore.message.get(EMessage.NO_CREATED_WARPS));
            return;
        }

        gui.open((Player) sender);
    }
}
