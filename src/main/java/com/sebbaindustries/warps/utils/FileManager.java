package com.sebbaindustries.warps.utils;

import com.sebbaindustries.warps.Core;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

/**
 * <b>This class contains access to every generated file plugin uses or generates for server administrators</b><br>
 * @author sebbaindustries
 * @version 1.0
 */
public class FileManager {

    private final Core core;

    public FileManager(final @NotNull Core core) {
        this.core = core;
        generateREADME();
        generateMessages();
        generateSettings();
        generateConfiguration();
    }

    /*
    README.md File
     */

    /**
     * Generates README.md File
     */
    public final void generateREADME() {
        File README = new File(core.getDataFolder(), "README.md");

        if (!README.exists()) {
            core.saveResource("README.md", false);
        }
    }

    /*
    messages.xml file
     */

    public File messages;

    /**
     * Generates messages.xml File
     */
    public final void generateMessages() {
        if (messages == null) {
            messages = new File(core.getDataFolder(), "messages.xml");
        }
        if (!messages.exists()) {
            core.saveResource("messages.xml", false);
        }
    }

    /*
    settings.xml
     */

    public File settings;

    /**
     * Generates settings.xml File
     */
    public final void generateSettings() {
        if (settings == null) {
            settings = new File(core.getDataFolder(), "settings.xml");
        }
        if (!settings.exists()) {
            core.saveResource("settings.xml", false);
        }
    }

    /*
    configuration.yml
     */

    private FileConfiguration fileConfiguration = null;
    private File configuration = null;

    /**
     * Generates configuration.yml File
     */
    public final void generateConfiguration() {
        if (configuration == null) {
            configuration = new File(core.getDataFolder(), "configuration.yml");
        }
        if (!configuration.exists()) {
            core.saveResource("configuration.yml", false);
        }
    }

    /**
     * @return configuration file
     * @see FileConfiguration
     */
    public final FileConfiguration getConfiguration() {
        if (fileConfiguration == null) {
            reloadConfiguration();
        }
        return fileConfiguration;
    }

    /**
     * @see YamlConfiguration
     * @see Reader
     * Reloads or creates new configuration file
     */
    public final void reloadConfiguration() {
        if (configuration == null) {
            configuration = new File(core.getDataFolder(), "configuration.yml");

        }
        fileConfiguration = YamlConfiguration.loadConfiguration(configuration);

        Reader defConfigStream = new InputStreamReader(Objects.requireNonNull(core.getResource("configuration.yml")));
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        fileConfiguration.setDefaults(defConfig);
    }

}
