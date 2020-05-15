package com.sebbaindustries.warps.message;

/**
 * <b>This enum has ID's for all messages</b>
 * @author sebbaindustries
 * @version 1.0
 */
public enum IMessage {

    NO_PERMISSION(0),
    FAILED_WARP_CREATION(1),
    BLACKLISTED_WARP_NAME(2),
    SUCCESSFULLY_CREATED_WARP(3),
    INVALID_LOCATION(4),
    INVALID_WARP(5),
    NOT_WARP_OWNER(6),
    FAILED_TO_REMOVE_WARP(7),
    SUCCESSFULLY_REMOVED_WARP(8),
    ;

    public Integer ID;

    IMessage(Integer ID) {
        this.ID = ID;
    }
}
