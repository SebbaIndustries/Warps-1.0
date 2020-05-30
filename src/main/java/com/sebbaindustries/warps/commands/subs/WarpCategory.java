package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.EPermission;
import com.sebbaindustries.warps.interfaces.menu.CategoryMenu;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.utils.EnumCheck;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.utils.gui.guis.PaginatedGui;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpCategory extends ICommand {

    private final Core core;

    public WarpCategory(final Core core) {
        super("category", "category [category/set] (warp) <category>", 0);
        permissions().add(EPermission.ROOT, EPermission.COMMANDS, EPermission.CATEGORY);
        setPlayerOnly();

        this.core = core;
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final String argument = args.length >= 1 ? args[0].toLowerCase() : null;

        if (argument == null) {
            player.sendMessage(Core.gCore.message.get(EMessage.INVALID_CATEGORY));
            return;
        }

        if (argument.equalsIgnoreCase("set")) {
            final String name = args.length >= 2 ? args[1] : player.getName();
            final Warp.Category category = args.length == 3 ? Warp.Category.valueOf(args[2].toUpperCase()) : Warp.Category.UNDEFINED;

            final Warp warp = Core.gCore.warpStorage.getWarp(name);

            if (warp == null) {
                player.sendMessage(Core.gCore.message.get(EMessage.INVALID_WARP));
                return;
            }

            if (!warp.getOwner().equalsIgnoreCase(player.getName())) {
                player.sendMessage(Core.gCore.message.get(EMessage.NOT_WARP_OWNER));
                return;
            }

            warp.setCategory(category);
            player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.SET_WARP_CATEGORY), "{warp-category}", WarpUtils.getFormattedCategory(category), "{warp-name}", name));
            return;
        }

        if (!EnumCheck.isValid(Warp.Category.class, args[0].toUpperCase())) {
            player.sendMessage(Core.gCore.message.get(EMessage.INVALID_CATEGORY));
            return;
        }

        final Warp.Category category = Warp.Category.valueOf(args[0].toUpperCase());
        final PaginatedGui categoryMenu = CategoryMenu.getCategoryWarpMenu(core, category);

        if (categoryMenu == null) {
            player.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.NO_CATEGORY_WARPS), "{warp-category}", argument));
            return;
        }

        categoryMenu.open(player);
    }
}
