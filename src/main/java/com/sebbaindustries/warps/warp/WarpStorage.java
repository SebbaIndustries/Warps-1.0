package com.sebbaindustries.warps.warp;

import java.util.HashMap;
import java.util.Map;

public class WarpStorage {

    private Map<String, Warp> warpHashMap = new HashMap<>();
    public int nextID = 0;

    public int genNextID() {
        nextID++;
        return nextID;
    }

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

    public void updateWarp(Warp warp) {
        warpHashMap.put(warp.getName(), warp);
    }

    public Warp getWarp(String warpName) {
        return warpHashMap.getOrDefault(warpName, null);
    }

    public Map<String, Warp> getWarpHashMap() { return warpHashMap; }

}
