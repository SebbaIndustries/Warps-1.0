package com.sebbaindustries.warps.global;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.CommandManager;
import com.sebbaindustries.warps.lang.Lang;
import com.sebbaindustries.warps.utils.FileManager;
import com.sebbaindustries.warps.utils.Messages;
import com.sebbaindustries.warps.utils.Settings;

/**
 * <b>This class links entire plugin together</b>
 * @author sebbaindustries
 * @version 1.0
 */
public class GlobalCore {

    public GlobalCore(Core core) {
        // fileManager needs to be initialized first
        this.fileManager = new FileManager(core);

        this.messages = new Messages();
        this.settings = new Settings();
        this.lang = new Lang();
        this.commandManager = new CommandManager(core);
    }

    public final FileManager fileManager;
    public final Messages messages;
    public final Settings settings;
    public final Lang lang;
    public final CommandManager commandManager;

}
