package com.sebbaindustries.warps.warp;

import org.jetbrains.annotations.NotNull;

public class WarpSettings {

    private final String permission;
    private final int maxWarps;
    private final boolean envNormal;
    private final boolean envNether;
    private final boolean envTheEnd;
    private final int waitTime;

    public WarpSettings(
            final @NotNull String permission, final int maxWarps, final boolean envNormal,
            final boolean envNether, final boolean envTheEnd, final int waitTime) {
        this.permission = permission;
        this.maxWarps = maxWarps;
        this.envNormal = envNormal;
        this.envNether = envNether;
        this.envTheEnd = envTheEnd;
        this.waitTime = waitTime;
    }

    public final String getPermission() {
        return permission;
    }

    public final int getMaxWarps() {
        return maxWarps;
    }

    public final boolean isEnvNormal() {
        return envNormal;
    }

    public final boolean isEnvNether() {
        return envNether;
    }

    public final boolean isEnvTheEnd() {
        return envTheEnd;
    }

    public final int getWaitTime() {
        return waitTime;
    }
}
