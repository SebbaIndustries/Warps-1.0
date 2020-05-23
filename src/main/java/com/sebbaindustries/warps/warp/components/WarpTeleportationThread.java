package com.sebbaindustries.warps.warp.components;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.settings.ESettings;
import com.sebbaindustries.warps.utils.Color;
import com.sebbaindustries.warps.warp.Warp;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;


public class WarpTeleportationThread extends Thread {

    private final Player p;
    Location loc;
    private int seconds;

    public WarpTeleportationThread(Player p, Location loc) {
        this.p = p;
        this.seconds = Core.gCore.settings.getWarpSettings(p).getWaitTime() / 20;
        this.loc = loc;
    }

    public WarpTeleportationThread(Player p, Warp warp) {
        this.p = p;
        this.seconds = Core.gCore.settings.getWarpSettings(p).getWaitTime() / 20;
        this.loc = new Location(
                warp.getLocation().getWorld(),
                warp.getLocation().getX(),
                warp.getLocation().getY(),
                warp.getLocation().getZ(),
                warp.getLocation().getYaw(),
                warp.getLocation().getPitch()
        );
    }

    private @NotNull String getProgressBar(int current, int max, int totalBars, String symbol,
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
        switch (Core.gCore.settings.get(ESettings.TELEPORT_METHOD).toLowerCase()) {
            case "title":
                title();
                break;
            case "actionbar":
                actionbar();
                break;
            case "chat":
                chat();
                break;
            default:
                int waitTime = seconds * 1000;
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

        teleportPlayer(p, loc);
    }

    private void title() {
        String titleTop = Color.chat(Core.gCore.settings.get(ESettings.TITLE_TOP)); // Main title
        String titleSub = Color.chat(Core.gCore.settings.get(ESettings.TITLE_SUB)); // Sub title
        int fadeout = Core.gCore.settings.getInt(ESettings.TITLE_FADEOUT);
        try {
            if (Core.gCore.settings.getBool(ESettings.USE_PROGRESS_BAR)) { // Using progress bar
                String colorComplete = Color.chat(Core.gCore.settings.get(ESettings.PROGRESS_BAR_COMPLETED_COLOR));
                String colorNotComplete = Color.chat(Core.gCore.settings.get(ESettings.PROGRESS_BAR_UNCOMPLETED_COLOR));
                String symbol = Color.chat(Core.gCore.settings.get(ESettings.PROGRESS_BAR_SYMBOL));
                int length = Core.gCore.settings.getInt(ESettings.PROGRESS_BAR_LENGTH);
                seconds = seconds * 10; // For Faster clock (progress bar)
                // nice fade in first title
                p.sendTitle(
                        titleTop.replace("${time}", getProgressBar(0, seconds, length, symbol, colorComplete, colorNotComplete)),
                        titleSub.replace("${time}", getProgressBar(0, seconds, length, symbol, colorComplete, colorNotComplete)),
                        Core.gCore.settings.getInt(ESettings.TITLE_FADEIN),
                        10,
                        0
                );
                Thread.sleep(100); // Faster clock for progress bar 0.1s
                for (int i = 1; i <= seconds; i++) {
                    // no fade in
                    p.sendTitle(
                            titleTop.replace("${time}", getProgressBar(i, seconds, length, symbol, colorComplete, colorNotComplete)),
                            titleSub.replace("${time}", getProgressBar(i, seconds, length, symbol, colorComplete, colorNotComplete)),
                            0,
                            10,
                            fadeout);
                    Thread.sleep(100);
                }
                return;
            }
            double sec = seconds;
            seconds = seconds * 10;
            DecimalFormat df = new DecimalFormat("####0.0");
            // nice fade in first title
            p.sendTitle(
                    titleTop.replace("${time}", String.valueOf(df.format(sec))),
                    titleSub.replace("${time}", String.valueOf(df.format(sec))),
                    Core.gCore.settings.getInt(ESettings.TITLE_FADEIN),
                    10,
                    0
            );
            Thread.sleep(100); // Slower clock for normal countdown 1.0s
            // no fade in
            for (int i = 1; i <= seconds; i++) {
                sec -= 0.1;
                if (sec <= 0.0) sec = 0.0;
                p.sendTitle(
                        titleTop.replace("${time}", String.valueOf(df.format(sec))),
                        titleSub.replace("${time}", String.valueOf(df.format(sec))),
                        0,
                        10,
                        fadeout
                );
                Thread.sleep(100);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void actionbar() {
        try {
            String text = Core.gCore.settings.get(ESettings.ACTIONBAR_TEXT);
            if (Core.gCore.settings.getBool(ESettings.USE_PROGRESS_BAR)) {
                String colorComplete = Color.chat(Core.gCore.settings.get(ESettings.PROGRESS_BAR_COMPLETED_COLOR));
                String colorNotComplete = Color.chat(Core.gCore.settings.get(ESettings.PROGRESS_BAR_UNCOMPLETED_COLOR));
                String symbol = Color.chat(Core.gCore.settings.get(ESettings.PROGRESS_BAR_SYMBOL));
                int length = Core.gCore.settings.getInt(ESettings.PROGRESS_BAR_LENGTH);
                seconds = seconds * 10;
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                        text.replace("${time}", getProgressBar(0, seconds, length, symbol, colorComplete, colorNotComplete))));
                Thread.sleep(100);
                for (int i = 1; i <= seconds; i++) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                            text.replace("${time}", getProgressBar(i, seconds, length, symbol, colorComplete, colorNotComplete))));
                    Thread.sleep(100);
                }
            } else {
                double sec = seconds;
                seconds = seconds * 10;
                DecimalFormat df = new DecimalFormat("####0.0");
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                        text.replace("${time}", String.valueOf(df.format(sec)))));
                Thread.sleep(100);
                for (int i = 1; i <= sec; i++) {
                    sec -= 0.1;
                    if (sec <= 0.0) sec = 0.0;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                            text.replace("${time}", String.valueOf(df.format(sec)))));
                    Thread.sleep(100);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void chat() {

    }

    private void teleportPlayer(final @NotNull Player p, final @NotNull Location loc) {
        Bukkit.getScheduler().runTask(Core.getPlugin(Core.class), () -> p.teleport(loc));
    }
}
