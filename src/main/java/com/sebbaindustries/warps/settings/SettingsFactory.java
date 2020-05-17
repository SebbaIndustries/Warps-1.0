package com.sebbaindustries.warps.settings;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.warp.WarpSettings;
import org.apache.commons.lang.BooleanUtils;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>This class contains all setting for the plugin and access to the file that saves them</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public abstract class SettingsFactory {

    public abstract String get(ISettings iSettings);
    public abstract void reloadSettings();
    protected abstract void getWarpSettings();

    /**
     * Prepares XML file and tries to find elementName (<b>>tag<</b>) and attributeName(tag <b>attr="value"</b>)
     *
     * @param elementName   name of a element that contains data we need
     * @param attributeName name of a attribute that contains data we need
     * @return Value of a attribute, if not found <b>$ERROR_NOT_FOUND</b> and if file encounters an exception <b>$ERROR_STACK</b>
     */
    protected String prepareXML(@NotNull final String elementName, final String attributeName) {
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.settings));
            while (sReader.hasNext()) {
                //Move to next event
                sReader.next();

                //Check if its 'START_ELEMENT'
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    //message tag - opened
                    if (sReader.getLocalName().equalsIgnoreCase(elementName)) {
                        // Read message inside the tags if string is null
                        if (attributeName == null) {
                            final String s = sReader.getElementText();
                            sReader.close();
                            return s;
                        }
                        // Read attributes within message tag
                        if (sReader.getAttributeCount() > 0) {
                            final String s = sReader.getAttributeValue(null, attributeName);
                            sReader.close();
                            return s;
                        }
                    }
                }
            }
            sReader.close();
            // Tag or attribute not found
            return "$ERROR_NOT_FOUND";
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return "$ERROR_STACK";
        }
    }

    protected List<WarpSettings> prepareWarpSettings() {
        List<WarpSettings> warpSettingsList = new ArrayList<>();
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.settings));
            while (sReader.hasNext()) {
                //Move to next event
                sReader.next();

                //Check if its 'START_ELEMENT'
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    //message tag - opened
                    if (sReader.getLocalName().equalsIgnoreCase("warp-settings")) {
                        if (sReader.getAttributeCount() > 0) {
                            warpSettingsList.add(createNewWarpSettings(sReader));
                        }
                    }
                }
            }
            return warpSettingsList;
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private WarpSettings createNewWarpSettings(XMLStreamReader sReader) {
        return new WarpSettings(
                sReader.getAttributeValue(null, "permission"),
                Integer.parseInt(sReader.getAttributeValue(null, "maxwarps")),
                BooleanUtils.toBoolean(sReader.getAttributeValue(null, "Environment.NORMAL")),
                BooleanUtils.toBoolean(sReader.getAttributeValue(null, "Environment.NETHER")),
                BooleanUtils.toBoolean(sReader.getAttributeValue(null, "Environment.THE_END")),
                Integer.parseInt(sReader.getAttributeValue(null, "teleport-wait-time"))
        );
    }

}
