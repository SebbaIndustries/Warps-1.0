package com.sebbaindustries.warps.interfaces;

import com.sebbaindustries.warps.Core;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class InterfaceFactory {

    protected String p(@NotNull final String elementName, final String attributeName) {
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.warpInterface));
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

}
