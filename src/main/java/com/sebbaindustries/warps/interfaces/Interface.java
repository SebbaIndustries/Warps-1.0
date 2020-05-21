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
    private ItemStack backgroundItemStack;

    @Override
    public void setWarpItemStack() {
        this.warpItemStack = getWarp();
    }

    public void reloadInterface() {
        items.clear();

        setWarpItemStack();
        guiRows = Integer.parseInt(getInterfaceAttributes("rows"));
        display = getInterfaceAttributes("display");
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
    }

    public ItemStack getWarpItemStack() {
        return warpItemStack;
    }

    public ItemStack getBackgroundItemStack() { return backgroundItemStack; }

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
