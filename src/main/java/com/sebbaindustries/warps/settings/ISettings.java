package com.sebbaindustries.warps.settings;

/**
 * <b>This class contains all enums for messages</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public enum ISettings {

    BLACKLISTED_WARP_NAMES(1, "blacklisted-warp-names", null),
    ALLOW_IN_AIR_WARPS(2, "blacklisted-items", "allow-in-air-warps"),
    BLACKLISTED_ITEMS(3, "blacklisted-items", null),
    RING_CHECK_RADIUS(4, "ring-check", "radius"),
    RING_CHECK_ITEMS(5, "ring-check", null),

    TELEPORT_CONFIRM_TIME(6, "teleport", "confirm-availability-time"),
    TELEPORT_WAIT_TIME(7, "teleport", "default-teleport-wait-time"),

    TELEPORT_METHOD(8, "teleport-display", "method"),

    USE_PROGRESS_BAR(9, "teleport-display", "use-progress-bar"),
    PROGRESS_BAR_COMPLETED_COLOR(10, "teleport-display", "completed-color"),
    PROGRESS_BAR_UNCOMPLETED_COLOR(11, "teleport-display", "uncompleted-color"),
    PROGRESS_BAR_SYMBOL(12, "teleport-display", "progress-bar-symbol"),
    PROGRESS_BAR_LENGTH(13, "teleport-display", "progress-bar-length"),

    TITLE_TOP(14, "title", "top"),
    TITLE_SUB(15, "title", "sub"),
    TITLE_FADEIN(16, "title", "fadein"),
    TITLE_FADEOUT(17, "title", "fadeout"),

    ACTIONBAR_TEXT(18, "actionbar", "text"),

    CHAT_TEXT(19, "chat", "text"),
    CHAT_TEXT_FREQUENCY(20, "chat", "text-send-frequency"),
    ;

    public Integer ID;
    public String elementName;
    public String attributeName;

    ISettings(Integer ID, String elementName, String attributeName) {
        this.ID = ID;
        this.elementName = elementName;
        this.attributeName = attributeName;
    }
}
