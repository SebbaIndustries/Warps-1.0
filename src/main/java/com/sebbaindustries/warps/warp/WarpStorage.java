package com.sebbaindustries.warps.warp;

import java.util.HashMap;
import java.util.Map;

public class WarpStorage {

    private Map<String, Warp> warpHashMap = new HashMap<>();

    public boolean addWarp(Warp warp) {
        if (getWarp(warp.getName()) != null) return false;
        warpHashMap.put(warp.getName(), warp);
        return true;
    }

    public boolean deleteWarp(String warpName) {
        if (getWarp(warpName) == null) return false;
        warpHashMap.remove(warpName);
        return true;
    }

    public Warp getWarp(String warpName) {
        return warpHashMap.getOrDefault(warpName, null);
    }

    public Map<String, Warp> getWarpHashMap() { return warpHashMap; }

}
