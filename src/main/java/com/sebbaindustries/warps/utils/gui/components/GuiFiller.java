package com.sebbaindustries.warps.utils.gui.components;

import com.sebbaindustries.warps.utils.gui.guis.BaseGui;
import com.sebbaindustries.warps.utils.gui.guis.GuiItem;
import com.sebbaindustries.warps.utils.gui.guis.PaginatedGui;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public final class GuiFiller {

    private final BaseGui gui;

    public GuiFiller(final BaseGui gui) {
        this.gui = gui;
    }

    /**
     * Fills top portion of the GUI
     *
     * @param guiItem GuiItem
     */
    public void fillTop(@NotNull final GuiItem guiItem) {
        fillTop(Collections.singletonList(guiItem));
    }

    /**
     * Fills top portion of the GUI with alternation
     *
     * @param guiItems List of GuiItems
     */
    public void fillTop(@NotNull final List<GuiItem> guiItems) {
        final List<GuiItem> items = repeatList(guiItems, gui.getRows() * 9);
        for (int i = 0; i < 9; i++) {
            if (!gui.getGuiItems().containsKey(i)) gui.setItem(i, items.get(i));
        }
    }

    /**
     * Fills bottom portion of the GUI
     *
     * @param guiItem GuiItem
     */
    public void fillBottom(@NotNull final GuiItem guiItem) {
        fillBottom(Collections.singletonList(guiItem));
    }

    /**
     * Fills bottom portion of the GUI with alternation
     *
     * @param guiItems GuiItem
     */
    public void fillBottom(@NotNull final List<GuiItem> guiItems) {
        final int rows = gui.getRows();
        final List<GuiItem> items = repeatList(guiItems, rows * 9);
        for (int i = 9; i > 0; i--) {
            if (gui.getGuiItems().get(i) == null) gui.setItem((rows * 9) - i, items.get(i));
        }
    }

    /**
     * Fills the outside section of the GUI with a GuiItem
     *
     * @param guiItem GuiItem
     */
    public void fillBorder(@NotNull final GuiItem guiItem) {
        fillBorder(Collections.singletonList(guiItem));
    }

    /**
     * Fill empty slots with Multiple GuiItems, goes through list and starts again
     *
     * @param guiItems GuiItem
     */
    public void fillBorder(@NotNull final List<GuiItem> guiItems) {
        final int rows = gui.getRows();
        if (rows <= 2) return;

        final List<GuiItem> items = repeatList(guiItems, rows * 9);

        for (int i = 0; i < rows * 9; i++) {
            if ((i <= 8) || (i >= (rows * 9) - 9)
                    || i == 9 || i == 18
                    || i == 27 || i == 36
                    || i == 17 || i == 26
                    || i == 35 || i == 44)
                gui.setItem(i, items.get(i));

        }
    }

    /**
     * Fills rectangle from points within the GUI
     *
     * @param rowFrom Row point 1
     * @param colFrom Col point 1
     * @param rowTo   Row point 2
     * @param colTo   Col point 2
     * @param guiItem Item to fill with
     * @author Harolds
     */
    public void fillBetweenPoints(final int rowFrom, final int colFrom, final int rowTo, final int colTo, @NotNull final GuiItem guiItem) {
        fillBetweenPoints(rowFrom, colFrom, rowTo, colTo, Collections.singletonList(guiItem));
    }

    /**
     * Fills rectangle from points within the GUI
     *
     * @param rowFrom  Row point 1
     * @param colFrom  Col point 1
     * @param rowTo    Row point 2
     * @param colTo    Col point 2
     * @param guiItems Item to fill with
     * @author Harolds
     */
    public void fillBetweenPoints(final int rowFrom, final int colFrom, final int rowTo, final int colTo, @NotNull final List<GuiItem> guiItems) {
        int minRow = Math.min(rowFrom, rowTo);
        int maxRow = Math.max(rowFrom, rowTo);
        int minCol = Math.min(colFrom, colTo);
        int maxCol = Math.max(colFrom, colTo);

        final int rows = gui.getRows();
        final List<GuiItem> items = repeatList(guiItems, rows * 9);

        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= 9; col++) {
                int slot = getSlotFromRowCol(row, col);
                if (!((row >= minRow && row <= maxRow) && (col >= minCol && col <= maxCol)))
                    continue;

                gui.setItem(slot, items.get(slot));
            }
        }
    }

    /**
     * Sets an GuiItem to fill up the entire inventory where there is no other item
     *
     * @param guiItem The item to use as fill
     */
    public void fill(@NotNull final GuiItem guiItem) {
        fill(Collections.singletonList(guiItem));
    }

    /**
     * Fill empty slots with Multiple GuiItems, goes through list and starts again
     *
     * @param guiItems GuiItem
     */
    public void fill(@NotNull final List<GuiItem> guiItems) {
        if (gui instanceof PaginatedGui) throw new GuiException("Full filling a GUI is not supported in a Paginated GUI!");

        final int rows = gui.getRows();
        final List<GuiItem> items = repeatList(guiItems, rows * 9);
        for (int i = 0; i < (rows) * 9; i++) {
            if (gui.getGuiItems().get(i) == null) gui.setItem(i, items.get(i));
        }
    }

    /**
     * Repeats any type of Array. Allows for alternating items
     * Stores references to existing Objects -> Does not create new objects
     *
     * @param guiItems  Array Type
     * @param newLength Length of array
     * @return New array
     */
    private List<GuiItem> repeatList(@NotNull final List<GuiItem> guiItems, final int newLength) {
        final List<GuiItem> repeated = new ArrayList<>();
        Collections.nCopies(gui.getRows() * 9, guiItems).forEach(repeated::addAll);
        return repeated;
    }


    /**
     * Gets the slot from the row and col passed
     *
     * @param row The row
     * @param col The col
     * @return The new slot
     */
    private int getSlotFromRowCol(final int row, final int col) {
        return (col + (row - 1) * 9) - 1;
    }

}
