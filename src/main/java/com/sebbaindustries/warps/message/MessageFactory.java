package com.sebbaindustries.warps.message;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.utils.Color;
import org.apache.commons.lang.BooleanUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * <b>This class constructs messages form xml file</b>
 * @author sebbaindustries
 * @version 1.0
 */
public abstract class MessageFactory {

    public abstract String get(IMessage iMessage);
    public abstract String getPrefix();
    public abstract void reloadMessages();


    /**
     * Gets prefix for messages
     * @return formatted prefix
     */
    protected String getMessagePrefix() {
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
    protected String getMessage(final int messageID) {
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
                                return Color.chat(getMessagePrefix() + sReader.getElementText());
                            }
                            return Color.chat(sReader.getElementText());
                        }
                    }
                }
            }
        }
        return "$ERROR_NOT_FOUND";
    }

}
