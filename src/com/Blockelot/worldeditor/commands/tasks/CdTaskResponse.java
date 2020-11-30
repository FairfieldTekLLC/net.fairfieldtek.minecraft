package com.Blockelot.worldeditor.commands.tasks;

import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.Util.ServerUtil;
import com.Blockelot.worldeditor.http.CdResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CdTaskResponse
        extends BukkitRunnable {

    public CdResponse CdResponse;

    public CdTaskResponse(CdResponse cdResponse) {
        this.CdResponse = cdResponse;
    }

    @Override
    public void run() {
        Player player = PluginManager.Plugin.getServer().getPlayer(UUID.fromString(this.CdResponse.getUuid()));
        try {            
            if (player == null) {
                return;
            }            
            PluginManager.PlayerInfoList.get(player).setLastAuth(this.CdResponse.getAuth());
            PluginManager.PlayerInfoList.get(player).setCurrentPath(this.CdResponse.getDirectoryPath());
            if (!this.CdResponse.getWasSuccessful()) {
                player.sendMessage(ChatColor.RED + this.CdResponse.getMessage());
            }
            player.sendMessage("Current Directory: " + this.CdResponse.getDirectoryPath());
        } catch (Exception e) {
            ServerUtil.consoleLog(e.getLocalizedMessage());
            ServerUtil.consoleLog(e.getMessage());
        }
        PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Cd");
        this.cancel();
    }
}
