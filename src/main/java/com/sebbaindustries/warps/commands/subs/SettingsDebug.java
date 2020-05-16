package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.IPermission;
import com.sebbaindustries.warps.settings.ISettings;
import com.sebbaindustries.warps.settings.Settings;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SettingsDebug extends ICommand {

    public SettingsDebug() {
        super("debug", "debug", 0);
        permissions().add(IPermission.ROOT);
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Settings settings = Core.gCore.settings;

        System.out.println("ACTIONBAR_TEXT: " + settings.get(ISettings.ACTIONBAR_TEXT));
        System.out.println("ALLOW_IN_AIR_WARPS: " + settings.get(ISettings.ALLOW_IN_AIR_WARPS));
        System.out.println("BLACKLISTED_ITEMS: " + settings.get(ISettings.BLACKLISTED_ITEMS));
        System.out.println("BLACKLISTED_WARP_NAMES: " + settings.get(ISettings.BLACKLISTED_WARP_NAMES));
        System.out.println("CHAT_TEXT: " + settings.get(ISettings.CHAT_TEXT));
        System.out.println("CHAT_TEXT_FREQUENCY: " + settings.get(ISettings.CHAT_TEXT_FREQUENCY));
        System.out.println("PROGRESS_BAR_COMPLETED_COLOR: " + settings.get(ISettings.PROGRESS_BAR_COMPLETED_COLOR));
        System.out.println("PROGRESS_BAR_LENGTH: " + settings.get(ISettings.PROGRESS_BAR_LENGTH));
        System.out.println("PROGRESS_BAR_SYMBOL: " + settings.get(ISettings.PROGRESS_BAR_SYMBOL));
        System.out.println("PROGRESS_BAR_UNCOMPLETED_COLOR: " + settings.get(ISettings.PROGRESS_BAR_UNCOMPLETED_COLOR));
        System.out.println("RING_CHECK_ITEMS: " + settings.get(ISettings.RING_CHECK_ITEMS));
        System.out.println("RING_CHECK_RADIUS: " + settings.get(ISettings.RING_CHECK_RADIUS));
        System.out.println("TELEPORT_CONFIRM_TIME: " + settings.get(ISettings.TELEPORT_CONFIRM_TIME));
        System.out.println("TELEPORT_METHOD: " + settings.get(ISettings.TELEPORT_METHOD));
        System.out.println("TELEPORT_WAIT_TIME: " + settings.get(ISettings.TELEPORT_WAIT_TIME));
        System.out.println("TITLE_FADEIN: " + settings.get(ISettings.TITLE_FADEIN));
        System.out.println("TITLE_FADEOUT: " + settings.get(ISettings.TITLE_FADEOUT));
        System.out.println("TITLE_SUB: " + settings.get(ISettings.TITLE_SUB));
        System.out.println("TITLE_TOP: " + settings.get(ISettings.TITLE_TOP));
        System.out.println("USE_PROGRESS_BAR: " + settings.get(ISettings.USE_PROGRESS_BAR));
    }
}
