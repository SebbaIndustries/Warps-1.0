package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.warp.Warp;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;

public class SettingsDebug extends ICommand {

    public SettingsDebug() {
        super("debug", "debug", 0);
        permissions().add(EPermission.ROOT);
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        /*

        WarpSettings warpSettings = Core.gCore.settings.getFakeWarpSettings(2);

        if (warpSettings == null) {
            System.out.println(":(");
            return;
        }
        System.out.println(warpSettings.getPermission());

         */
        //new WarpTeleportationThread((Player) sender).start();
    }
}
