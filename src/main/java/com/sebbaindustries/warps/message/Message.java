package com.sebbaindustries.warps.message;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * <b>This class initializes messages, and allows access to them</b>
 * @author sebbaindustries
 * @version 1.0
 */
public class Message extends MessageFactory {

    private final HashMap<Integer, String> messages = new HashMap<>();
    private String prefix;

    /**
     * Gets specified message from the hashmap, returns error if message does not exist <br>
     * @see EMessage
     * @param eMessage Enum for messages
     * @return Translated message
     */
    @Override
    public final String get(final @NotNull EMessage eMessage) {
        return messages.get(eMessage.ID);
    }

    /**
     * @return Plugin prefix from xml file
     */
    @Override
    public String getPrefix() {
        return prefix;
    }

    /**
     * Reloads messages and stores them in server memory
     */
    @Override
    public void reloadMessages() {
        prefix = getMessagePrefix();
        messages.clear();
        for (final EMessage eMessage : EMessage.values()) {
            messages.put(eMessage.ID, getMessage(eMessage.ID));
        }
    }
}
