package com.sebbaindustries.warps.interfaces;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.interfaces.components.EAction;
import com.sebbaindustries.warps.interfaces.graphics.GuiItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class InterfaceFactory {

    public abstract List<Integer> getWarpSlots();
    public abstract GuiItem getGuiItem(int slot);
    public abstract List<Integer> getBackgroundSlots();
    public abstract List<Integer> getButtonSlots();


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

    protected List<GuiItem> getButtons() {
        List<GuiItem> buttonsList = new ArrayList<>();
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
                            int slot = Integer.parseInt(sReader.getAttributeValue(null, "slot"));
                            EAction iAction = machIAction(sReader.getAttributeValue(null, "action"));
                            ItemStack mat = readMaterial(sReader);
                            String display = readDisplay(sReader);
                            String lore = readLore(sReader);
                            buttonsList.add(new GuiItem(iAction, mat, slot, display, lore));
                        }
                    }
                }
            }
            sReader.close();
            // Tag or attribute not found
            return buttonsList;
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected List<GuiItem> getWarps() {
        List<GuiItem> warpsList = new ArrayList<>();
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.warpInterface));
            while (sReader.hasNext()) {
                //Move to next event
                sReader.next();

                //Check if its 'START_ELEMENT'
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    //message tag - opened
                    if (sReader.getLocalName().equalsIgnoreCase("warps")) {
                        // Read attributes within message tag
                        if (sReader.getAttributeCount() > 0) {
                            int[] slots = getSlots(sReader.getAttributeValue(null, "slots"));
                            ItemStack mat = readMaterial(sReader);
                            String display = readDisplay(sReader);
                            String lore = readLore(sReader);
                            for (int slot : slots) {
                                warpsList.add(new GuiItem(EAction.WARP, mat, slot, display, lore));
                            }
                        }
                    }
                }
            }
            sReader.close();
            // Tag or attribute not found
            return warpsList;
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
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

    private EAction machIAction(String action) {
        for (EAction iAction : EAction.values()) {
            if (iAction.action.equalsIgnoreCase(action)) return iAction;
        }
        return EAction.NONE;
    }

    private int[] getSlots(String slotStr) {
        return Arrays.stream(slotStr.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    private String readLore(XMLStreamReader sReader) {
        try {
            while (sReader.hasNext()) {
                sReader.next();
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (sReader.getLocalName().equalsIgnoreCase("lore")) {
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

    private ItemStack readMaterial(XMLStreamReader sReader) {
        try {
            while (sReader.hasNext()) {
                sReader.next();
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (sReader.getLocalName().equalsIgnoreCase("material")) {
                        return new ItemStack(Objects.requireNonNull(Material.matchMaterial(sReader.getElementText().toUpperCase())));
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return new ItemStack(Material.STONE);
        }
        return new ItemStack(Material.STONE);
    }

}
