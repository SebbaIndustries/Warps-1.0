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

    public Map<Integer, GuiItem> getItems() { return items; }

    /*
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
    }

     */

}
