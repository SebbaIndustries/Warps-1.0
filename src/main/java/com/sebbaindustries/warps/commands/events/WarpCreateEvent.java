package com.sebbaindustries.warps.commands.events;

import com.sebbaindustries.warps.warp.components.WarpLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class WarpCreateEvent extends Event {

    @Override
    public @NotNull HandlerList getHandlers() {
        return new HandlerList();
    }

    private Player owner;
    private String name;
    private WarpLocation location;

    public WarpCreateEvent(final Player owner, final String name, final WarpLocation location) {
        this.owner = owner;
        this.name = name;
        this.location = location;
    }

    public Player getOwner() {
        return owner;
    }

    public String getWarpName() {
        return name;
    }

    public WarpLocation getWarpLocation() {
        return location;
    }
}
