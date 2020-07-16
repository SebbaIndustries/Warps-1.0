package com.sebbaindustries.warps.interfaces.menu;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.interfaces.Interface;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.utils.gui.components.ItemNBT;
import com.sebbaindustries.warps.utils.gui.guis.Gui;
import com.sebbaindustries.warps.utils.gui.guis.GuiItem;
import com.sebbaindustries.warps.utils.gui.guis.PaginatedGui;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SelectorMenu {

    public static Gui getSelectorMenu(final Core core) {
        final Interface iMenu = Core.gCore.guiInterface;
        final Gui gui = new Gui(core, iMenu.getSelectorRows(), iMenu.getSelectorDisplay());

        gui.setDefaultClickAction(e -> e.setCancelled(true));

        for (Integer slot : iMenu.getSelectorItems().keySet()) {
            final ItemStack item = iMenu.getSelectorItems().get(slot).getItemStack().clone();
            final String type = ItemNBT.getNBTTag(item, "type");

            switch (type) {
                case "player":
                case "server":
                    gui.setItem(slot, new GuiItem(item, event -> {
                        final Player player = (Player) event.getWhoClicked();
                        final PaginatedGui menu = WarpMenu.getWarpMenu(core, type);

                        if (menu == null) {
                            player.closeInventory();
                            player.sendMessage(Core.gCore.message.get(EMessage.NO_CREATED_WARPS));
                            return;
                        }
                        player.closeInventory();

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                menu.open(player);
                            }
                        }.runTaskLater(core, 5);
                    }));
                    break;
                default:
                    gui.setItem(slot, new GuiItem(item));
            }
        }

        return gui;
    }
}
