package com.sebbaindustries.warps.warp;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.settings.ISettings;
import com.sebbaindustries.warps.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class WarpTeleportationThread extends Thread {

    private final Player p;
    private int seconds;

    public WarpTeleportationThread(Player p, int seconds) {
        this.p = p;
        this.seconds = seconds;
    }

    public String getProgressBar(int current, int max, int totalBars, String symbol,
                                        String completedColor, String notCompletedColor) {
        float percent = (float) current / max;

        int progressBars = (int) (totalBars * percent);

        int leftOver = (totalBars - progressBars);

        StringBuilder sb = new StringBuilder();
        sb.append((completedColor));
        for (int i = 0; i < progressBars; i++) {
            sb.append(symbol);
        }
        sb.append((notCompletedColor));
        for (int i = 0; i < leftOver; i++) {
            sb.append(symbol);
        }
        return sb.toString();
    }

    /**
     * Thread Code
     */
    public void run() {

        // pull data from Config
        boolean useProgressBar = Core.gCore.settings.getBool(ISettings.USE_PROGRESS_BAR);
        String colorComplete = Color.chat(Core.gCore.settings.get(ISettings.PROGRESS_BAR_COMPLETED_COLOR));
        String colorNotComplete = Color.chat(Core.gCore.settings.get(ISettings.PROGRESS_BAR_UNCOMPLETED_COLOR));
        String symbol = Color.chat(Core.gCore.settings.get(ISettings.PROGRESS_BAR_SYMBOL));
        int length = Core.gCore.settings.getInt(ISettings.PROGRESS_BAR_LENGTH);
        String titleTop = Color.chat(Core.gCore.settings.get(ISettings.TITLE_TOP)); // Main title
        String titleSub = Color.chat(Core.gCore.settings.get(ISettings.TITLE_SUB)); // Sub title

        try {
            if (useProgressBar) { // Using progress bar
                seconds = seconds * 10; // For Faster clock (progress bar)
                // nice fade in first title
                p.sendTitle(titleTop, getProgressBar(0, seconds, length, symbol, colorComplete, colorNotComplete), 10, 10, 0);
                Thread.sleep(100); // Faster clock for progress bar 0.1s
                for (int i = 1; i <= seconds; i++) {
                    // no fade in
                    p.sendTitle(titleTop, getProgressBar(i, seconds, length, symbol, colorComplete, colorNotComplete), 0, 10, 10);
                    Thread.sleep(100);
                }
            } else { // Using timer
                // nice fade in first title
                p.sendTitle(titleTop, titleSub.replace("${time}", String.valueOf(seconds)), 10, 25, 0);
                Thread.sleep(1000); // Slower clock for normal countdown 1.0s
                // no fade in
                for (int i = 1; i <= seconds; i++) {
                    p.sendTitle(titleTop, titleSub.replace("${time}", String.valueOf(seconds - i)), 0, 25, 10);
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void teleportPlayer(final @NotNull Player p, final @NotNull Location loc) {
        Bukkit.getScheduler().runTask(Core.getPlugin(Core.class), () -> p.teleport(loc));
    }
}
