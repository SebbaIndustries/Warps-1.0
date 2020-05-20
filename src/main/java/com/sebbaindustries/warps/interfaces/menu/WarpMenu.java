package com.sebbaindustries.warps.interfaces.menu;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.interfaces.Interface;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.utils.gui.components.ItemNBT;
import com.sebbaindustries.warps.utils.gui.guis.GuiItem;
import com.sebbaindustries.warps.utils.gui.guis.PaginatedGui;
import com.sebbaindustries.warps.warp.Warp;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WarpMenu {

    public static PaginatedGui getWarpMenu(final Core core) {
        final Interface iMenu = Core.gCore.guiInterface;
        final PaginatedGui gui = new PaginatedGui(core, iMenu.getMenuRows(), iMenu.getWarpsPerPage(), Replace.replaceString(iMenu.getMenuDisplay()
                , "{type}", "Player", "{warp-amount}", String.valueOf(Core.gCore.warpStorage.getWarpHashMap().size())));

        if (Core.gCore.warpStorage.getWarpHashMap().size() < 1) {
            return null;
        }

        gui.setDefaultClickAction(e -> e.setCancelled(true));

        for (Integer slot : iMenu.getItems().keySet()) {
            final GuiItem item = iMenu.getItems().get(slot);
            final String action = ItemNBT.getNBTTag(item.getItemStack(), "action");

            if (action != null) {
                switch (action) {
                    case "next-page":
                        //gui.setItem(slot, new GuiItem(item.getItemStack(), event -> gui.nextPage()));
                        break;
                    case "previous-page":
                        //gui.setItem(slot, new GuiItem(item.getItemStack(), event -> gui.prevPage()));
                        break;
                    default:
                        //gui.setItem(slot, item);
                }
            } else {
                gui.setItem(slot, item);
            }
        }

        for (String name : Core.gCore.warpStorage.getWarpHashMap().keySet()) {
            final Warp warp = Core.gCore.warpStorage.getWarp(name);
            ItemStack warpItem = iMenu.getWarpItemStack();

            final ItemMeta meta = warpItem.getItemMeta();

            if (meta != null) {
                meta.setDisplayName(Replace.replaceString(meta.getDisplayName()
                        , "{warp-name}", name));

                /*
                meta.setLore(Replace.replaceList(meta.getLore()
                        , "{warp-owner}", warp.getOwner().getName()
                        , "{warp-status}", WarpUtils.getBooleanString(warp.getAccessibility())
                        , "{warp-rating}", String.valueOf(WarpUtils.getWarpAverageRating(warp))
                        , "{warp-location}", WarpUtils.getLocationString(warp.getLocation())
                        , "{warp-world}", WarpUtils.getWorldString(warp.getLocation().getWorld())
                        , "{warp-description}", warp.getDescription()
                ));
                */
            }

            warpItem.setItemMeta(meta);

            gui.addItem(new GuiItem(warpItem, event -> {
                final Player player = (Player) event.getWhoClicked();

                player.sendMessage("Teleporting to " + name + " Warp");
            }));
        }

        return gui;
    }
}
