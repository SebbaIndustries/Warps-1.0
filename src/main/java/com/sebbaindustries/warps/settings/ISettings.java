package com.sebbaindustries.warps.settings;

/**
 * <b>This class contains all enums for messages</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public enum ISettings {

    MAX_WARPS(0, "value1", "value2");

    public Integer ID;
    public String elementName;
    public String attributeName;

    ISettings(Integer ID, String elementName, String attributeName) {
        this.ID = ID;
        this.elementName = elementName;
        this.attributeName = attributeName;
    }
}
