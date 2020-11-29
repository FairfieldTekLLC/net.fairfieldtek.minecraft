package com.Blockelot.worldeditor.listeners;

import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener
        implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!PluginManager.PlayerInfoList.containsKey(player)) {
            System.out.print("Adding Player " + player.getUniqueId().toString() + " - joined.  ");
            PluginManager.PlayerInfoList.put(player, new PlayerInfo(player));
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (PluginManager.PlayerInfoList.containsKey(player)) {
            System.out.print("Removing Player " + player.getUniqueId().toString() + " - left.  ");
            PluginManager.PlayerInfoList.remove(player);
        }
    }

}
