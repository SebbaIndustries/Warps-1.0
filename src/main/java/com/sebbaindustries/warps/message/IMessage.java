package com.sebbaindustries.warps.message;

/**
 * <b>This enum has ID's for all messages</b>
 * @author sebbaindustries
 * @version 1.0
 */
public enum IMessage {

    NO_PERMISSION(0),
    ;

    public Integer ID;

    IMessage(Integer ID) {
        this.ID = ID;
    }
}
