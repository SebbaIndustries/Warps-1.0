package com.sebbaindustries.warps.interfaces.graphics;

import com.sebbaindustries.warps.interfaces.components.IAction;
import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class GuiItem {

    // The ItemStack of the GuiItem
    private ItemStack itemStack;
    private int slot;
    private IAction iAction;
    private String display;
    private String lore;

    /**
     * constructor with no action
     * @param itemStack The ItemStack to be used
     */
    public GuiItem(@NotNull final IAction iAction, @NotNull final ItemStack itemStack, final int slot, String display, String lore) {
        this.itemStack = itemStack;
        this.slot = slot;
        this.display = display;
        this.lore = lore;
        this.iAction = iAction;
    }

    /**
     * Gets the GuiItem's ItemStack
     * @return The ItemStack
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Replaces the ItemStack of the GUI Item
     * @param itemStack The new ItemStack
     */
    public void setItemStack(@NotNull final ItemStack itemStack) {
        Validate.notNull(itemStack, "The itemstack for the GUI Item cannot be null!");
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public IAction getIAction() {
        return iAction;
    }

    public void setIAction(IAction iAction) {
        this.iAction = iAction;
    }

    public String getDisplay() {
        return display;
    }

    public String getLore() {
        return lore;
    }
}
