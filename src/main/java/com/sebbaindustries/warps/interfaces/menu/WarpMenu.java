package com.sebbaindustries.warps.interfaces.menu;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.interfaces.Interface;
import com.sebbaindustries.warps.utils.Color;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.utils.gui.components.ItemNBT;
import com.sebbaindustries.warps.utils.gui.guis.GuiItem;
import com.sebbaindustries.warps.utils.gui.guis.PaginatedGui;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpUtils;
import com.sebbaindustries.warps.warp.components.WarpTeleportationThread;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class WarpMenu {

    static PaginatedGui getWarpMenu(final Core core, final String type) {
        final Interface iMenu = Core.gCore.guiInterface;
        final Map<String, Warp> warpMap = WarpUtils.getFilteredWarps(Core.gCore.warpStorage.getWarpHashMap(), type);
        final PaginatedGui gui = new PaginatedGui(core, iMenu.getMenuRows(), iMenu.getWarpsPerPage(), Replace.replaceString(iMenu.getMenuDisplay()
                , "{type}", StringUtils.capitalize(type.toLowerCase()), "{warp-amount}", String.valueOf(Core.gCore.warpStorage.getWarpHashMap().size())));

        if (warpMap.size() < 1) {
            return null;
        }

        gui.setDefaultClickAction(e -> e.setCancelled(true));

        for (String name : warpMap.keySet()) {
            final Warp warp = Core.gCore.warpStorage.getWarp(name);
            ItemStack warpItem = iMenu.getWarpItemStack().clone();

            final ItemMeta meta = warpItem.getItemMeta();

            if (meta != null) {
                meta.setDisplayName(Replace.replaceString(meta.getDisplayName()
                        , "{warp-name}", warp.getName()));

                meta.setLore(Replace.replaceList(meta.getLore()
                        , "{warp-owner}", warp.getOwner().getName()
                        , "{warp-status}", Color.chat(WarpUtils.getBooleanString(warp.getAccessibility()))
                        , "{warp-rating}", String.valueOf(WarpUtils.getWarpAverageRating(warp))
                        , "{warp-location}", WarpUtils.getLocationString(warp.getLocation())
                        , "{warp-world}", WarpUtils.getWorldString(warp.getLocation().getWorld())
                        , "{warp-description}", WarpUtils.getSplitDescription(warp.getDescription())
                ));
            }

            warpItem.setItemMeta(meta);

            gui.addItem(new GuiItem(warpItem, event -> {
                final Player player = (Player) event.getWhoClicked();

                // If the warp is public, teleport player
                if (warp.getAccessibility()) {
                    new WarpTeleportationThread(player, WarpUtils.convertWarpLocation(warp.getLocation())).start();
                }
            }));
        }

        for (Integer slot : iMenu.getItems().keySet()) {
            final ItemStack item = iMenu.getItems().get(slot).getItemStack().clone();
            final String action = ItemNBT.getNBTTag(item, "action");

            switch (action) {
                case "next-page":
                    if (gui.getNextPageNum() != gui.getCurrentPageNum()) {
                        gui.setItem(slot, new GuiItem(item, event -> gui.nextPage()));
                    } else {
                        gui.setItem(slot, new GuiItem(iMenu.getBackgroundItemStack()));
                    }
                    break;
                case "previous-page":
                    if (gui.getPrevPageNum() != gui.getCurrentPageNum()) {
                        gui.setItem(slot, new GuiItem(item, event -> gui.prevPage()));
                    } else {
                        gui.setItem(slot, new GuiItem(iMenu.getBackgroundItemStack()));
                    }
                    break;
                default:
                    gui.setItem(slot, new GuiItem(item));
            }
        }

        return gui;
    }
}
