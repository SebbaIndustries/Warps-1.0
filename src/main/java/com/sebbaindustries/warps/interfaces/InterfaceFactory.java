package com.sebbaindustries.warps.interfaces;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.utils.gui.components.ItemNBT;
import com.sebbaindustries.warps.utils.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public abstract class InterfaceFactory {

    public abstract void setWarpItemStack();

    protected String getInterfaceAttributes(String attribute) {
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.warpInterface));
            while (sReader.hasNext()) {
                //Move to next event
                sReader.next();

                //Check if its 'START_ELEMENT'
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    //message tag - opened
                    if (sReader.getLocalName().equalsIgnoreCase("interface")) {
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

    protected Map<Integer, GuiItem> getButtons() {
        Map<Integer, GuiItem> buttons = new HashMap<>();
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.warpInterface));
            while (sReader.hasNext()) {
                //Move to next event
                sReader.next();

                //Check if its 'START_ELEMENT'
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    //message tag - opened
                    if (sReader.getLocalName().equalsIgnoreCase("button")) {
                        // Read attributes within message tag
                        if (sReader.getAttributeCount() > 0) {
                            final int slot = Integer.parseInt(sReader.getAttributeValue(null, "slot"));
                            final String action = sReader.getAttributeValue(null, "action");
                            final Material material = readMaterial(sReader);
                            final String display = readDisplay(sReader);
                            final List<String> lore = readLore(sReader);

                            ItemStack button = new ItemStack(material);
                            final ItemMeta meta = button.getItemMeta();

                            meta.setDisplayName(display);
                            meta.setLore(lore);

                            button = ItemNBT.setNBTTag(button, "action", action);

                            buttons.put(slot, new GuiItem(button));
                        }
                    }
                }
            }
            sReader.close();
            // Tag or attribute not found
            return buttons;
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected int getWarpsPerPage() {
        return Integer.parseInt(getInterfaceAttributes("warps"));
    }

    protected int getMenuRows() {
        return Integer.parseInt(getInterfaceAttributes("rows"));
    }

    protected String getMenuDisplay() {
        return getInterfaceAttributes("display");
    }

    protected ItemStack getWarp() {
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.warpInterface));

            while (sReader.hasNext()) {
                sReader.next();

                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (sReader.getLocalName().equalsIgnoreCase("warps")) {
                        if (sReader.getAttributeCount() > 0) {
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
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return new ItemStack(Material.STONE);
        }
        return new ItemStack(Material.STONE);
    }

    protected ItemStack getBackground() {
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
                        return Arrays.asList(sReader.getElementText().split("\\n"));
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
