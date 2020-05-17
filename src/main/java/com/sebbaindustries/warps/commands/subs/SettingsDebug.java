package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.IPermission;
import com.sebbaindustries.warps.warp.components.WarpTeleportationThread;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SettingsDebug extends ICommand {

    public SettingsDebug() {
        super("debug", "debug", 0);
        permissions().add(IPermission.ROOT);
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
        new WarpTeleportationThread((Player) sender).start();
    }
}
