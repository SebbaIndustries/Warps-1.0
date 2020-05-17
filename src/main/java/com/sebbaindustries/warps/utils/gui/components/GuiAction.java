package com.sebbaindustries.warps.utils.gui.components;

import org.bukkit.event.Event;

@FunctionalInterface
public interface GuiAction<T extends Event> {

    /**
     * Executes the task passed to it
     *
     * @param event Inventory action
     */
    void execute(final T event);
}
