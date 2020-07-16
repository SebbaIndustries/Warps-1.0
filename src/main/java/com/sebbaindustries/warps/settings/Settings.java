package com.sebbaindustries.warps.settings;

import com.sebbaindustries.warps.warp.components.WarpSettings;
import org.apache.commons.lang.BooleanUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <b>This class contains all settings for the plugin and access to them</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public class Settings extends SettingsFactory{

    private final HashMap<Integer, String> settings = new HashMap<>();
    private List<WarpSettings> warpSettings = new ArrayList<>();

    /**
     * Gets setting that is specified in the enum
     * @see ESettings
     * @param eSettings ESettings enum
     * @return Setting in string datatype
     */
    @Override
    public String get(ESettings eSettings) {
        return settings.get(eSettings.ID);
    }

    /**
     * Gets setting and returns in int datatype
     * @see ESettings
     * @param eSettings ESettings enum
     * @return Setting in int datatype
     */
    public int getInt(ESettings eSettings) {
        return Integer.parseInt(get(eSettings));
    }

    /**
     * Gets setting and returns in boolean datatype
     * @see ESettings
     * @param eSettings ESettings enum
     * @return Setting in boolean datatype
     */
    public boolean getBool(ESettings eSettings) {
        return BooleanUtils.toBoolean(get(eSettings));
    }

    /**
     * Gets setting and returns in double datatype
     * @see ESettings
     * @param eSettings ESettings enum
     * @return Setting in double datatype
     */
    public double getDouble(ESettings eSettings) {
        return Double.parseDouble(get(eSettings));
    }

    /**
     * gets setting and returns it as array
     * @see ESettings
     * @param eSettings ISetting enum
     * @return Setting in String array datatype
     */
    public String[] getStrArray(ESettings eSettings) {
        return get(eSettings).split(" ");
    }

    public List<String> getList(ESettings eSettings) {
        return Arrays.asList(getStrArray(eSettings));
    }

    /**
     * Reloads and saves setting to server memory
     */
    @Override
    public void reloadSettings() {
        settings.clear();
        warpSettings.clear();
        for (ESettings iSetting : ESettings.values()) {
            settings.put(iSetting.ID, prepareXML(iSetting.elementName, iSetting.attributeName));
        }
        getWarpSettings();
    }

    @Override
    protected void getWarpSettings() {
        warpSettings = prepareWarpSettings();
    }

    public final WarpSettings getWarpSettings(final @NotNull Player p) {
        WarpSettings warpSetting = null;
        for (WarpSettings settings : warpSettings) if (p.hasPermission(settings.getPermission())) warpSetting = settings;
        return warpSetting;
    }
}
