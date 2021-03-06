package com.sebbaindustries.warps.message;

/**
 * <b>This enum has ID's for all messages</b>
 * @author sebbaindustries
 * @version 1.0
 */
public enum EMessage {

    NO_PERMISSION(0),
    FAILED_WARP_CREATION(1),
    BLACKLISTED_WARP_NAME(2),
    SUCCESSFULLY_CREATED_WARP(3),
    INVALID_LOCATION(4),
    INVALID_WARP(5),
    NOT_WARP_OWNER(6),
    FAILED_TO_REMOVE_WARP(7),
    SUCCESSFULLY_REMOVED_WARP(8),
    SUCCESSFULLY_CHANGED_LOCATION(9),
    INVALID_COMMAND_ARGUMENT(10),
    INVALID_PLAYER(11),
    SUCCESSFULLY_CHANGED_WARP_OWNER(12),
    NEW_OWNER_CANNOT_BE_OLD_OWNER(13),
    CHANGED_WARP_STATUS(14),
    PRIVATE_WARP(15),
    UNSAFE_TELEPORT_LOCATION(16),
    TELEPORTATION_STARTED(17),
    PRIVATE_WARP_RATING(18),
    RATED_WARP(19),
    CANT_RATE_OWN_WARP(20),
    TOO_LONG_WARP_DESCRIPTION(21),
    SUCCESSFULLY_SET_DESCRIPTION(22),
    SUCCESSFULLY_REMOVED_DESCRIPTION(23),
    NO_CREATED_WARPS(24),
    LISTED_WARPS(25),
    ALL_LISTED_WARPS(30),
    TARGET_IS_WITHOUT_WARPS(26),
    SET_WARP_CATEGORY(27),
    NO_CATEGORY_WARPS(28),
    INVALID_CATEGORY(29),
    ;

    public Integer ID;

    EMessage(Integer ID) {
        this.ID = ID;
    }
}
