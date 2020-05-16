package com.sebbaindustries.warps.settings;

import org.apache.commons.lang.BooleanUtils;

import java.util.HashMap;

/**
 * <b>This class contains all settings for the plugin and access to them</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public class Settings extends SettingsFactory{

    private final HashMap<Integer, String> settings = new HashMap<>();

    /**
     * Gets setting that is specified in the enum
     * @see ISettings
     * @param iSettings ISettings enum
     * @return Setting in string datatype
     */
    @Override
    public String get(ISettings iSettings) {
        return settings.get(iSettings.ID);
    }

    /**
     * Gets setting and returns in int datatype
     * @see ISettings
     * @param iSettings ISettings enum
     * @return Setting in int datatype
     */
    public int getInt(ISettings iSettings) {
        return Integer.parseInt(get(iSettings));
    }

    /**
     * Gets setting and returns in boolean datatype
     * @see ISettings
     * @param iSettings ISettings enum
     * @return Setting in boolean datatype
     */
    public boolean getBool(ISettings iSettings) {
        return BooleanUtils.toBoolean(get(iSettings));
    }

    /**
     * Gets setting and returns in double datatype
     * @see ISettings
     * @param iSettings ISettings enum
     * @return Setting in double datatype
     */
    public double getDouble(ISettings iSettings) {
        return Double.parseDouble(get(iSettings));
    }

    /**
     * gets setting and returns it as array
     * @see ISettings
     * @param iSettings ISetting enum
     * @return Setting in String array datatype
     */
    public String[] getStrArray(ISettings iSettings) {
        return get(iSettings).split(" ");
    }

    /**
     * Reloads and saves setting to server memory
     */
    @Override
    public void reloadSettings() {
        settings.clear();
        for (ISettings iSetting : ISettings.values()) {
            settings.put(iSetting.ID, prepareXML(iSetting.elementName, iSetting.attributeName));
        }
    }
}
