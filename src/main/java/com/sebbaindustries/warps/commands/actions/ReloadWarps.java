package com.sebbaindustries.warps.commands.actions;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.utils.Color;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadWarps extends ICommand {

    /**
     * Constructor that creates ICommand instance with all necessary arguments
     */
    public ReloadWarps() {
        super("reloadwarps", "/reloadwarps", 0);
        permissions().add(EPermission.ROOT);
        setPlayerOnly();
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        if (sender.hasPermission("warps.command.reload")) {
            Long time = System.currentTimeMillis();
            Core.gCore.settings.reloadSettings();
            Core.gCore.message.reloadMessages();
            Core.gCore.guiInterface.reloadInterface();
            Long time2 = System.currentTimeMillis() - time;
            sender.sendMessage(Color.chat("&aDone in " + time2 + "ms."));
        }
    }

}
