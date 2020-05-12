package com.sebbaindustries.warps.utils;

import com.sebbaindustries.warps.Core;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * <b>This class contains all setting for the plugin and access to the file that saves them</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public class Settings {

    /**
     * Prepares XML file and tries to find elementName (<b>>tag<</b>) and attributeName(tag <b>attr="value"</b>)
     *
     * @param elementName   name of a element that contains data we need
     * @param attributeName name of a attribute that contains data we need
     * @return Value of a attribute, if not found <b>$ERROR_NOT_FOUND</b> and if file encounters an exception <b>$ERROR_STACK</b>
     */
    private String prepareXML(@NotNull final String elementName, @NotNull final String attributeName) {
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

                        //Read attributes within message tag
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

    /**
     * Reloads all settings, called when class is created or command is run (/reload)
     */
    public void reloadSettings() {

    }


}
