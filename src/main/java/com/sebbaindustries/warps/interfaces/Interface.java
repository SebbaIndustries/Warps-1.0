package com.sebbaindustries.warps.interfaces;

import com.sebbaindustries.warps.utils.gui.guis.GuiItem;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interface extends InterfaceFactory {

    private static Map<Integer, GuiItem> items = new HashMap<>();

    private List<Integer> warpSlots;
    private int guiRows;
    private String display;
    private int warpsPerPage;
    private ItemStack warpItemStack;

    @Override
    public void setWarpItemStack() {
        this.warpItemStack = getWarp();
    }

    public void reloadInterface() {
        items.clear();

        setWarpItemStack();
        warpsPerPage = Integer.parseInt(getInterfaceAttributes("warps"));
        guiRows = Integer.parseInt(getInterfaceAttributes("rows"));
        display = getInterfaceAttributes("display");
        warpSlots = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);

        ItemStack background = getBackground();

        for (int i = 0; i < guiRows * 9; i++) {
            if (!warpSlots.contains(i)) {
                if (items.get(i) == null) items.put(i, new GuiItem(background));
            }
        }

        Map<Integer, GuiItem> buttons = getButtons();
        for (Integer slot : buttons.keySet()) {
            items.put(slot, buttons.get(slot));
        }
    }

    public ItemStack getWarpItemStack() {
        return warpItemStack;
    }

    public String getMenuDisplay() {
        return display;
    }

    public int getWarpsPerPage() {
        return warpsPerPage;
    }

    public int getMenuRows() {
        return guiRows;
    }

    public Map<Integer, GuiItem> getItems() {
        return items;
    }


    public void printLog() {
        System.out.println("PRINT LOG");
        System.out.println("PRINT LOG");
        System.out.println("PRINT LOG");
        System.out.println(" ");
        System.out.println("Gui rows: " + guiRows);
        System.out.println("Warps " + warpsPerPage);
        System.out.println("Display: " + display);
        System.out.println(" ");
        System.out.println("Warp item stack " + warpItemStack.getItemMeta().getLore());
        System.out.println(" ");
        for (int i = 0; i < guiRows * 9; i++) {
            System.out.println(items.get(i).getItemStack().getType().toString());
        }
        System.out.println("Warp Slots: " + warpSlots);
    }

}
