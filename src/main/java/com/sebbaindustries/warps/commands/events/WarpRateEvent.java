package com.sebbaindustries.warps.commands.events;

import com.sebbaindustries.warps.warp.Warp;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class WarpRateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player rater;
    private Warp rated;
    private int rate;

    public WarpRateEvent(final Player rater, final Warp rated, final int rate) {
        this.rater = rater;
        this.rated = rated;
        this.rate = rate;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public Player getRater() {
        return rater;
    }

    public Warp getRated() {
        return rated;
    }

    public int getRate() {
        return rate;
    }
}
