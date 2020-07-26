package com.sebbaindustries.warps.global;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.CommandFactory;
import com.sebbaindustries.warps.database.Connection;
import com.sebbaindustries.warps.database.DBWarpUtils;
import com.sebbaindustries.warps.interfaces.Interface;
import com.sebbaindustries.warps.lang.Lang;
import com.sebbaindustries.warps.listener.WarpRateListener;
import com.sebbaindustries.warps.message.Message;
import com.sebbaindustries.warps.settings.Settings;
import com.sebbaindustries.warps.utils.FileManager;
import com.sebbaindustries.warps.warp.WarpStorage;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    public final Connection connection;

    public GlobalCore(Core core) {
        // fileManager needs to be initialized first
        this.fileManager = new FileManager(core);

        message = new Message();
        this.settings = new Settings();
        this.lang = new Lang();
        commandFactory = new CommandFactory(core);
        warpStorage = new WarpStorage();
        guiInterface = new Interface();
        connection = new Connection();
        core.getServer().getPluginManager().registerEvents(new WarpRateListener(), core);

        startNewDay();
    }

    public void startNewDay() {
        long delay = ChronoUnit.MILLIS.between(LocalTime.now(), LocalTime.of(23, 30, 0));
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            warpStorage.getWarpHashMap().forEach((warpName, warp) -> warp.getVisitData().shiftDays());
            DBWarpUtils.syncVisits();
        }, delay, TimeUnit.MILLISECONDS);
    }

}
