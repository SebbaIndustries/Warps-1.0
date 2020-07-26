package com.sebbaindustries.warps.warp.components;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.settings.ESettings;
import org.bukkit.entity.Player;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WarpVisits {

    public WarpVisits() {
        resetVisits();
    }

    private List<Integer> visits = new ArrayList<>();
    private HashMap<String, Integer> playerVisits = new HashMap<>();

    /**
     * Gets visits from list and sums them up
     * @return Integer of visits, def: 0
     */
    public int getWarpVisits() {
        return visits.stream().reduce(0, Integer::sum);
    }

    public boolean addVisit(Player player) {

        if (!playerVisits.containsKey(player.getName())) playerVisits.put(player.getName(), 0);
        if (playerVisits.get(player.getName()) > Core.gCore.settings.getInt(ESettings.COUNTED_WARP_VISITS)) return false;

        visits.set(0, visits.get(0) + 1);
        playerVisits.put(player.getName(), playerVisits.get(player.getName()) + 1);
        return true;
    }

    public void loadVisits(String jsonString) {
        resetVisits();
        if (jsonString == null || jsonString.equalsIgnoreCase("NULL")) {
            return;
        }

        JSONObject jsonObject = new JSONObject(jsonString);

        JSONArray visitsArray = jsonObject.getJSONArray("visits");
        JSONObject playersObjectMap = jsonObject.getJSONObject("players");

        visits.forEach(visit -> visits.set(visit, visitsArray.getInt(visit)));
        playersObjectMap.toMap().forEach((player, visit) -> playerVisits.put(player, Integer.parseInt(visit.toString())));
    }

    /**
     * Clears visits list, sets 0 for every day
     */
    private void resetVisits() {
        visits.clear();
        playerVisits.clear();
        for (int i = 0; i < Core.gCore.settings.getInt(ESettings.WARP_VISIT_LENGTH); i++) {
            visits.add(0);
        }
    }

    public JSONObject getJSON() {
        JSONObject json = new JSONObject();
        json.put("visits", visits);
        json.put("players", playerVisits);
        return json;
    }

    public void shiftDays() {
        playerVisits.clear();
        //make a loop to run through the array list
        for(int i = visits.size() - 1; i > 0; i--) {
            visits.set(i, visits.get(i - 1));
        }
        visits.set(0, 0);
    }

}
