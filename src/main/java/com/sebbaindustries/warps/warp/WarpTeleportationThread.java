package com.sebbaindustries.warps.warp;

import com.sebbaindustries.warps.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;


public class WarpTeleportationThread{

    private final Player p;
    private final Location loc;

    public WarpTeleportationThread(final @NotNull Player p, final @NotNull Location loc) {
        this.p = p;
        this.loc = loc;
    }

    public final void run() {
        CompletableFuture.runAsync(() -> {
            threadSleep(3000);
            teleportPlayer(p, loc);
            return;
        });
    }


    private void teleportPlayer(final @NotNull Player p, final @NotNull Location loc) {
        Bukkit.getScheduler().runTask(Core.getPlugin(Core.class), () -> p.teleport(loc));
    }

    public void threadSleep(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
