package com.Blockelot.worldeditor.commands.tasks;

import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.http.SchematicDataResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveClipboardTaskResponse
        extends BukkitRunnable {

    public SchematicDataResponse Response;

    public SaveClipboardTaskResponse(SchematicDataResponse response) {
        this.Response = response;
    }

    @Override
    public void run() {
        Player player = PluginManager.Plugin.getServer().getPlayer(UUID.fromString(this.Response.getUuid()));
        try {
            if (player == null) {
                return;
            }

            if (Response.getFinal()) {
                PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "SaveClipboard");
            }

            PluginManager.PlayerInfoList.get(player).setLastAuth(this.Response.getLastAuth());
            if (!this.Response.getWasSuccessful()) {
                player.sendMessage(ChatColor.YELLOW + "File not saved.");
                if (!"".equals(Response.getMessage())) {
                    player.sendMessage(ChatColor.RED + this.Response.getMessage());
                }
            } else {
                if (!"".equals(Response.getMessage())) {
                    player.sendMessage(ChatColor.GREEN + this.Response.getMessage());
                }
                PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "SaveClipboard");
            }
        } catch (Exception e) {
            PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "SaveClipboard");
        }

        this.cancel();
    }
}
