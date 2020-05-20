package com.sebbaindustries.warps.commands.events;

import com.sebbaindustries.warps.warp.Warp;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class WarpDeleteEvent extends Event {

    @Override
    public @NotNull HandlerList getHandlers() {
        return new HandlerList();
    }

    private Player owner;
    private Warp warp;

    public WarpDeleteEvent(final Player owner, final Warp warp) {
        this.owner = owner;
        this.warp = warp;
    }

    public Player getOwner() { return owner; }
    public Warp getWarp() { return warp; }
}
