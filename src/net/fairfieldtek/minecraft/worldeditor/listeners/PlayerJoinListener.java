package net.fairfieldtek.minecraft.worldeditor.listeners;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
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
        if (!Initialization.PlayerInfoList.containsKey(player)) {
            System.out.print("Adding Player " + player.getUniqueId().toString() + " - joined.  ");
            Initialization.PlayerInfoList.put(player, new PlayerInfo());
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Initialization.PlayerInfoList.containsKey(player)) {
            System.out.print("Removing Player " + player.getUniqueId().toString() + " - left.  ");
            Initialization.PlayerInfoList.remove(player);
        }
    }
    
    
}
