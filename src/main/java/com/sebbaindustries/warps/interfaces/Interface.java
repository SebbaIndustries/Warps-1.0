package com.sebbaindustries.warps.interfaces;

import com.sebbaindustries.warps.utils.gui.guis.GuiItem;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interface extends InterfaceFactory {

    private static Map<Integer, GuiItem> items = new HashMap<>();

    /*
    Warps Menu
     */
    private int guiRows;
    private String display;
    private int warpsPerPage;
    private ItemStack warpItemStack;
    private ItemStack backgroundItemStack;

    /*
    Selector Menu
     */
    private int selectorRows;
    private String selectorDisplay;
    private Map<Integer, GuiItem> selectorItems = new HashMap<>();

    @Override
    public void setWarpItemStack() {
        this.warpItemStack = getWarp();
    }

    public void reloadInterface() {
        items.clear();

        /*
        Warps Menu
         */
        List<Integer> warpSlots;
        setWarpItemStack();
        guiRows = Integer.parseInt(getInterfaceAttributes("pages", "rows"));
        display = getInterfaceAttributes("pages", "display");
        warpSlots = getWarpSlots();
        warpsPerPage = warpSlots.size();

        backgroundItemStack = getBackground();

        for (int i = 0; i < guiRows * 9; i++) {
            if (!warpSlots.contains(i)) {
                if (items.get(i) == null) items.put(i, new GuiItem(backgroundItemStack.clone()));
            }
        }

        Map<Integer, GuiItem> buttons = getButtons();
        for (Integer slot : buttons.keySet()) {
            items.put(slot, buttons.get(slot));
        }

        /*
        Selector Menu
         */

        selectorRows = Integer.parseInt(getInterfaceAttributes("selector", "rows"));
        selectorDisplay = getInterfaceAttributes("selector", "display");

        ItemStack selectorBackgroundItemStack = getBackground();

        for (int i = 0; i < selectorRows * 9; i++) {
            if (selectorItems.get(i) == null) selectorItems.put(i, new GuiItem(selectorBackgroundItemStack.clone()));
        }

        Map<Integer, GuiItem> selectorButtons = getSelectorButtons();
        for (Integer slot : selectorButtons.keySet()) {
            selectorItems.put(slot, buttons.get(slot));
        }
    }

    public ItemStack getWarpItemStack() {
        return warpItemStack;
    }

    public ItemStack getBackgroundItemStack() {
        return backgroundItemStack;
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

    public String getSelectorDisplay() {
        return selectorDisplay;
    }

    public int getSelectorRows() {
        return selectorRows;
    }

    public Map<Integer, GuiItem> getSelectorItems() {
        return selectorItems;
    }

}
