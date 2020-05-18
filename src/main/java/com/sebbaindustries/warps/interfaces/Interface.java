package com.sebbaindustries.warps.interfaces;

import com.sebbaindustries.warps.interfaces.components.IAction;
import com.sebbaindustries.warps.interfaces.graphics.GuiItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Interface extends InterfaceFactory {

    private static HashMap<Integer, GuiItem> guiInterfaceMap = new HashMap<>();
    private int guiRows;
    private int guiSize;
    private String display;
    private int warpsPerPage;

    @Override
    public List<Integer> getWarpSlots() {
        List<Integer> warpSlots = new ArrayList<>();
        for (GuiItem item : guiInterfaceMap.values()) {
            if (item.getIAction() == IAction.WARP) warpSlots.add(item.getSlot());
        }
        return warpSlots;
    }

    @Override
    public GuiItem getGuiItem(int slot) {
        return guiInterfaceMap.get(slot);
    }

    @Override
    public List<Integer> getBackgroundSlots() {
        List<Integer> backgroundSlots = new ArrayList<>();
        for (GuiItem item : guiInterfaceMap.values()) {
            if (item.getIAction() == IAction.BACKGROUND) backgroundSlots.add(item.getSlot());
        }
        return backgroundSlots;
    }

    @Override
    public List<Integer> getButtonSlots() {
        List<Integer> warpSlots = new ArrayList<>();
        for (GuiItem item : guiInterfaceMap.values()) {
            if (item.getIAction() == IAction.PAGE_NEXT || item.getIAction() == IAction.PAGE_PREV || item.getIAction() == IAction.NONE) {
                warpSlots.add(item.getSlot());
            }
        }
        return warpSlots;
    }

    public void reloadInterface() {
        guiInterfaceMap.clear();
        guiRows = Integer.parseInt(getInterfaceAttributes("rows"));
        guiSize = guiRows * 9;
        display = getInterfaceAttributes("display");
        ItemStack background = getBackground();
        for (int i = 0; i < guiSize; i++) {
            guiInterfaceMap.put(i, new GuiItem(IAction.BACKGROUND, background, i, "", ""));
        }
        for (GuiItem item : getButtons()) {
            guiInterfaceMap.put(item.getSlot(), item);
        }
        warpsPerPage = 0;
        for (GuiItem item : getWarps()) {
            guiInterfaceMap.put(item.getSlot(), item);
            warpsPerPage++;
        }
    }

    public String getDisplay() {
        return display;
    }

    public int getWarpsPerPage() {
        return warpsPerPage;
    }

    public int getGuiRows() {
        return guiRows;
    }

    public int getGuiSize() {
        return guiSize;
    }
}
