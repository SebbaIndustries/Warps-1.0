package com.sebbaindustries.warps.global;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.CommandFactory;
import com.sebbaindustries.warps.interfaces.Interface;
import com.sebbaindustries.warps.lang.Lang;
import com.sebbaindustries.warps.listener.WarpRateListener;
import com.sebbaindustries.warps.message.Message;
import com.sebbaindustries.warps.settings.Settings;
import com.sebbaindustries.warps.utils.FileManager;
import com.sebbaindustries.warps.warp.WarpStorage;

/**
 * <b>This class links entire plugin together</b>
 *
 * @author sebbaindustries
 * @version 1.0
 */
public class GlobalCore {

    public final FileManager fileManager;
    public final Settings settings;
    public final Lang lang;
    public final CommandFactory commandFactory;
    public final WarpStorage warpStorage;
    public final Message message;
    public final Interface guiInterface;

    public GlobalCore(Core core) {
        // fileManager needs to be initialized first
        this.fileManager = new FileManager(core);

        message = new Message();
        this.settings = new Settings();
        this.lang = new Lang();
        commandFactory = new CommandFactory(core);
        warpStorage = new WarpStorage();
        guiInterface = new Interface();
        core.getServer().getPluginManager().registerEvents(new WarpRateListener(), core);
    }

}
