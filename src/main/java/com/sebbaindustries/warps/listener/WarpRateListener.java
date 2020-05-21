package com.sebbaindustries.warps.listener;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.events.WarpRateEvent;
import com.sebbaindustries.warps.message.EMessage;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.Warp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WarpRateListener implements Listener {

    @EventHandler
    public void onWarpRate(WarpRateEvent event) {
        final Player rater = event.getRater();
        final Warp rated = event.getRated();
        final int rate = event.getRate();

        rated.setRating(rater.getUniqueId(), rate);
        rater.sendMessage(Replace.replaceString(Core.gCore.message.get(EMessage.RATED_WARP)
                , "{warp-owner}", rated.getOwner().getName()
                , "{warp-name}", rated.getName()
                , "{rating}", String.valueOf(rate)));
    }
}
