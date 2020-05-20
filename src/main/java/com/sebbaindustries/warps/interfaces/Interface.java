package com.sebbaindustries.warps.interfaces;

import com.sebbaindustries.warps.utils.gui.guis.GuiItem;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Interface extends InterfaceFactory {

    private static Map<Integer, GuiItem> items = new HashMap<>();

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

        ItemStack background = getBackground();
        for (int i = 0; i < guiRows * 9; i++) {
            if (items.get(i) == null) items.put(i, new GuiItem(background));
        }

        for (Integer slot : getButtons().keySet()) {
            items.put(slot, getButtons().get(slot));
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

    public Map<Integer, GuiItem> getItems() { return items; }
}
