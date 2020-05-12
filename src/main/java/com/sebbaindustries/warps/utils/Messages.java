package com.sebbaindustries.warps.utils;

import com.sebbaindustries.warps.Core;
import org.apache.commons.lang.BooleanUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * <b>This class contains all messages plugin uses and access to the file that contains them</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public class Messages {

    /**
     * Hashmap containing all messages
     */
    private final HashMap<Integer, String> messages = new HashMap<>();

    /**
     * Enums for messages, they just point to id
     */
    public enum M {

        NO_PERMISSION(0),
        ;

        private final int value;

        M(int value) {
            this.value = value;
        }
    }

    /**
     * gets message from hashmap
     *
     * @param message Message enum
     * @return Translated message from memory
     * @see M message enum
     */
    public final String getMessage(final M message) {
        return messages.get(message.value);
    }

    /**
     * Reloads messages and loads them in HashMao
     */
    public final void reloadMessages() {
        messages.clear();
        for (final M ID : M.values()) {
            messages.put(ID.value, getMessage(ID.value));
        }
    }

    /**
     * Gets prefix for messages
     *
     * @return formatted prefix
     */
    private String getPrefix() {
        try {
            final XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.messages));
            while (sReader.hasNext()) {
                sReader.next();
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (sReader.getLocalName().equalsIgnoreCase("prefix")) {
                        return readPrefix(sReader);
                    }
                }
            }
            // Tag or attribute not found
            return "$ERROR_NOT_FOUND";
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return "$ERROR_STACK";
        }
    }

    /**
     * Reads all messages inside <msg></msg> element and looks for right language
     *
     * @param sReader Instance
     * @return String with right message or null if it's not found
     * @throws XMLStreamException Handled in public method getPrefix()
     */
    private String readPrefix(XMLStreamReader sReader) throws XMLStreamException {
        while (sReader.hasNext()) {
            //Move to next event
            sReader.next();

            //Check if its 'START_ELEMENT'
            if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                //msg tag - opened
                if (sReader.getLocalName().equalsIgnoreCase("msg")) {
                    // return sReader.getElementText();
                    if (sReader.getAttributeCount() > 0) {
                        // checks if element has lang attribute
                        String id = sReader.getAttributeValue(null, "lang");

                        // if attribute is same as lang tag it returns a message
                        if (id.equalsIgnoreCase(Core.gCore.lang.LANG)) return Color.chat(sReader.getElementText());
                    }
                }
            }
        }
        return "$ERROR_NOT_FOUND";
    }


    /**
     * Gets right message by ID, in a <message id="N"></message> tag
     *
     * @param messageID Integer
     * @return formatted message
     */
    private String getMessage(final int messageID) {
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.messages));
            while (sReader.hasNext()) {
                //Move to next event
                sReader.next();

                //Check if its 'START_ELEMENT'
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    //message tag - opened
                    if (sReader.getLocalName().equalsIgnoreCase("message")) {

                        //Read attributes within message tag
                        if (sReader.getAttributeCount() > 0) {
                            String id = sReader.getAttributeValue(null, "id");

                            // check if id is same to message name
                            if (messageID == Integer.parseInt(id)) {
                                return readMessage(sReader);
                            }
                        }
                    }
                }
            }
            return "$ERROR_NOT_FOUND";
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return "$ERROR_STACK";
        }
    }

    /**
     * Reads all messages inside <msg></msg> element and looks for right language
     *
     * @param sReader Instance
     * @return String with right message or null if it's not found
     * @throws XMLStreamException Handled in getMessage()
     */
    private String readMessage(XMLStreamReader sReader) throws XMLStreamException {
        while (sReader.hasNext()) {
            //Move to next event
            sReader.next();

            //Check if its 'START_ELEMENT'
            if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                //msg tag - opened
                if (sReader.getLocalName().equalsIgnoreCase("msg")) {
                    // return sReader.getElementText();
                    if (sReader.getAttributeCount() > 0) {
                        // checks if element has lang attribute
                        String lang = sReader.getAttributeValue(null, "lang");
                        // FOR ANYONE READING THIS: USE BooleanUtils by Apache, life savers tbh -Sebba
                        boolean prefix = BooleanUtils.toBoolean(sReader.getAttributeValue(null, "prefix"));

                        // if attribute is same as lang tag it returns a message
                        if (lang.equalsIgnoreCase(Core.gCore.lang.LANG)) {
                            if (prefix) {
                                return Color.chat(getPrefix() + sReader.getElementText());
                            }
                            return Color.chat(sReader.getElementText());
                        }
                    }
                }
            }
        }
        return "$ERROR_NOT_FOUND";
    }

    /**
     * @param tag like ID, but in string form -> #N
     * @return list of help messages
     * @deprecated may get used in the feature
     */
    @Deprecated
    private List<String> getHelpList(String tag) {
        List<String> helpList = new ArrayList<>();
        tag = "help line " + tag;
        try {
            XMLInputFactory iFactory = XMLInputFactory.newInstance();
            XMLStreamReader sReader = iFactory.createXMLStreamReader(new FileReader(Core.gCore.fileManager.messages));
            while (sReader.hasNext()) {
                //Move to next event
                sReader.next();

                //Check if its 'START_ELEMENT'
                if (sReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    //message tag - opened
                    if (sReader.getLocalName().equalsIgnoreCase("message")) {

                        //Read attributes within message tag
                        if (sReader.getAttributeCount() > 0) {
                            String id = sReader.getAttributeValue(null, "id");

                            // check if id is same to message name
                            if (tag.equalsIgnoreCase(id)) {
                                helpList.add(readMessage(sReader));
                            }
                        }
                    }
                }
            }
            return helpList;
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
            return Collections.singletonList("$ERROR_STACK");
        }
    }

}
