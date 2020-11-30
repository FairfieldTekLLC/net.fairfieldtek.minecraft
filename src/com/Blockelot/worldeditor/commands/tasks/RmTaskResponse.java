package com.Blockelot.worldeditor.commands.tasks;

import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.http.RmResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RmTaskResponse
        extends BukkitRunnable {

    public RmResponse RmResponse;

    public RmTaskResponse(RmResponse rmResponse) {
        this.RmResponse = rmResponse;
    }

    @Override
    public void run() {
        Player player = PluginManager.Plugin.getServer().getPlayer(UUID.fromString(this.RmResponse.getUuid()));
        try {
            try {
                if (player == null) {
                    return;
                }

                PluginManager.PlayerInfoList.get(player).setLastAuth(this.RmResponse.getAuth());
                PluginManager.PlayerInfoList.get(player).setCurrentPath(this.RmResponse.getDirectoryPath());

                if (!this.RmResponse.getWasSuccessful()) {
                    player.sendMessage(ChatColor.RED + this.RmResponse.getMessage());
                } else {
                    player.sendMessage(ChatColor.YELLOW + this.RmResponse.getMessage());
                }
                player.sendMessage("Current Directory: " + this.RmResponse.getDirectoryPath());
            } catch (Exception e) {
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
        }

        PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Rm");
        this.cancel();
    }
}
