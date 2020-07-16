package com.sebbaindustries.warps.interfaces;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.utils.gui.components.ItemNBT;
import com.sebbaindustries.warps.utils.gui.guis.GuiItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public abstract class InterfaceFactory {

    public abstract void setWarpItemStack();

    /**
     * READ before use!
     * getSingleXMLEntry("slots", "interface", "pages", "warps");
     * 10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34
     * <p>
     * getSingleXMLEntry(null, "interface", "pages", "warps", "material");
     * DIAMOND
     *
     * @param attribute given attribute
     * @param path given paths
     * @return returns a string of retrieved value
     */
    String getSingleXMLEntry(@Nullable String attribute, @NotNull String... path) {
        int position = 0;
        int finalStop = path.clone().length - 1;
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.warpInterface));
            while (sReader.hasNext()) {
                //Move to next event
                sReader.next();

                //Check if its 'START_ELEMENT'
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {

                    if (sReader.getLocalName().equalsIgnoreCase(path[position])) {
                        if (finalStop == position) {
                            if (attribute == null) {
                                return sReader.getElementText();
                            }
                            return sReader.getAttributeValue(null, attribute);
                        }
                        position++;
                    }
                }
            }
            sReader.close();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return "$ERROR_STACK";
        }
        return "$ERROR_NOT_FOUND";
    }

    /**
     * READ before use!
     * getMultipleXMLEntry("action", "interface", "button");
     * [next-page, previous-page, none]
     * <p>
     * getMultipleXMLEntry(null, "interface", "pages", "button", "material");
     * [SPECTRAL_ARROW, SPECTRAL_ARROW, KNOWLEDGE_BOOK]
     *
     * @param attribute given attribute
     * @param path given paths
     * @return returns a list of retrieved values
     */
    private List<String> getMultipleXMLEntry(@Nullable String attribute, @NotNull String... path) {
        int position = 0;
        int finalStop = path.clone().length - 1;
        String limiter = path.clone()[finalStop - 2];
        List<String> entries = new ArrayList<>();
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.warpInterface));
            while (sReader.hasNext()) {
                //Move to next event
                sReader.next();

                //Check if its 'START_ELEMENT'
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {

                    if (sReader.getLocalName().equalsIgnoreCase(path[position])) {
                        if (finalStop == position) {
                            if (attribute == null) {
                                entries.add(sReader.getElementText());
                            } else {
                                entries.add(sReader.getAttributeValue(null, attribute));
                            }
                            position = finalStop;
                            continue;
                        }
                        position++;
                    }
                }
                if (sReader.getEventType() == XMLStreamReader.END_ELEMENT) {
                    if (sReader.getLocalName().equalsIgnoreCase(limiter)) return entries;
                }
            }
            sReader.close();
            return entries;
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return Collections.singletonList("$ERROR_STACK");
        }
    }

    String getInterfaceAttributes(String menu, String attribute) {
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.warpInterface));
            while (sReader.hasNext()) {
                //Move to next event
                sReader.next();

                //Check if its 'START_ELEMENT'
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    //message tag - opened
                    if (sReader.getLocalName().equalsIgnoreCase(menu)) {
                        // Read attributes within message tag
                        if (sReader.getAttributeCount() > 0) {
                            return sReader.getAttributeValue(null, attribute);
                        }
                    }
                }
            }
            sReader.close();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    Map<Integer, GuiItem> getButtons(final String sub) {
        Map<Integer, GuiItem> items = new HashMap<>();
        List<String> materials = getMultipleXMLEntry(null, "interface", sub, "button", "material");
        List<String> slots = getMultipleXMLEntry("slot", "interface", sub, "button");
        List<String> types = getMultipleXMLEntry("type", "interface", sub, "button");
        List<String> displays = getMultipleXMLEntry(null, "interface", sub, "button", "display");
        List<String> lores = getMultipleXMLEntry(null, "interface", sub, "button", "lore");

        for (int i = 0; i < materials.size(); i++) {
            final Material material = Material.matchMaterial(materials.get(i));
            Validate.notNull(material);

            ItemStack item = new ItemStack(material);
            final ItemMeta meta = item.getItemMeta();

            if (!displays.get(i).contains("$empty")) {
                meta.setDisplayName(displays.get(i));
            }
            if (!lores.get(i).contains("$empty")) {
                meta.setLore(Arrays.asList(lores.get(i).split("\n")));
            }

            item.setItemMeta(meta);

            item = ItemNBT.setNBTTag(item, "type", types.get(i));

            items.put(Integer.parseInt(slots.get(i)), new GuiItem(item));
        }

        return items;
    }

    protected int getWarpsPerPage() {
        return Integer.parseInt(getInterfaceAttributes("pages", "warps"));
    }

    protected int getMenuRows() {
        return Integer.parseInt(getInterfaceAttributes("pages", "rows"));
    }

    protected String getMenuDisplay() {
        return getInterfaceAttributes("pages", "display");
    }

    List<Integer> getWarpSlots() {
        List<Integer> slots = new ArrayList<>();
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.warpInterface));

            while (sReader.hasNext()) {
                sReader.next();

                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (sReader.getLocalName().equalsIgnoreCase("warps")) {
                        if (sReader.getAttributeCount() > 0) {
                            Arrays.stream(getSlots(sReader.getAttributeValue(null, "slots"))).forEach(slots::add);
                            sReader.close();
                            return slots;
                        }
                    }
                }
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    protected ItemStack getWarp() {
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.warpInterface));

            while (sReader.hasNext()) {
                sReader.next();

                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (sReader.getLocalName().equalsIgnoreCase("warps")) {
                        final Material material = readMaterial(sReader);
                        final String display = readDisplay(sReader);
                        final List<String> lore = readLore(sReader);

                        ItemStack item = new ItemStack(material);
                        final ItemMeta meta = item.getItemMeta();

                        meta.setDisplayName(display);
                        meta.setLore(lore);

                        item.setItemMeta(meta);

                        sReader.close();
                        return item;
                    }
                }
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return new ItemStack(Material.STONE);
        }
        return new ItemStack(Material.STONE);
    }

    ItemStack getBackground() {
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.warpInterface));
            while (sReader.hasNext()) {
                //Move to next event
                sReader.next();

                //Check if its 'START_ELEMENT'
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    //message tag - opened
                    if (sReader.getLocalName().equalsIgnoreCase("background")) {
                        // Read attributes within message tag
                        if (sReader.getAttributeCount() > 0) {
                            return new ItemStack(Objects.requireNonNull(Material.matchMaterial(
                                    sReader.getAttributeValue(null, "material"))));
                        }
                    }
                }
            }
            sReader.close();
            // Tag or attribute not found
            return null;
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int[] getSlots(String slotStr) {
        return Arrays.stream(slotStr.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    private List<String> readLore(XMLStreamReader sReader) {
        try {
            while (sReader.hasNext()) {
                sReader.next();
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (sReader.getLocalName().equalsIgnoreCase("lore")) {
                        return Arrays.asList(sReader.getElementText().split("\n"));
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return Collections.singletonList("$ERROR_STACK");
        }
        return Collections.singletonList("$ERROR_NOT_FOUND");
    }

    private String readDisplay(XMLStreamReader sReader) {
        try {
            while (sReader.hasNext()) {
                sReader.next();
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (sReader.getLocalName().equalsIgnoreCase("display")) {
                        return sReader.getElementText();
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return "$ERROR_STACK";
        }
        return "$ERROR_NOT_FOUND";
    }

    private Material readMaterial(XMLStreamReader sReader) {
        try {
            while (sReader.hasNext()) {
                sReader.next();
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (sReader.getLocalName().equalsIgnoreCase("material")) {
                        return Objects.requireNonNull(Material.matchMaterial(sReader.getElementText().toUpperCase()));
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return Material.STONE;
        }
        return Material.STONE;
    }

}
