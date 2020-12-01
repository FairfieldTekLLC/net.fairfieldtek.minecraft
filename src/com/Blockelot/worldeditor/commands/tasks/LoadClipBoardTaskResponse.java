package com.Blockelot.worldeditor.commands.tasks;

import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.http.SchematicDataDownloadResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.Blockelot.worldeditor.container.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author geev
 */
public class LoadClipBoardTaskResponse
        extends BukkitRunnable {

    public SchematicDataDownloadResponse Response;

    public LoadClipBoardTaskResponse(SchematicDataDownloadResponse response) {
        this.Response = response;
    }

    @Override
    public void run() {
        // try {
        Player player = PluginManager.Plugin.getServer().getPlayer(UUID.fromString(this.Response.getUuid()));
        if (player == null) {
            this.cancel();
            return;
        }
        PluginManager.PlayerInfoList.get(player).setIsProcessing(true, "Load Clipboard");
        PlayerInfo pi = PluginManager.PlayerInfoList.get(player);
        pi.setLastAuth(this.Response.getAuth());
        pi.SelectEnd = null;
        pi.SelectStart = null;

        try {
            // System.out.println("inv pallet? " + Response.getBlockInvePalette().length);

            pi.ClipSchematic.LoadResponse(Response);
        } catch (Exception ex) {
            player.sendMessage(ChatColor.YELLOW + ex.getMessage());
            Logger.getLogger(LoadClipBoardTaskResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!this.Response.getWasSuccessful()) {
            player.sendMessage(ChatColor.YELLOW + "File not loaded.");
            player.sendMessage(ChatColor.RED + this.Response.getMessage());
        } else {
            player.sendMessage(ChatColor.GREEN + this.Response.getMessage());
        }
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//            System.out.println(e.getMessage());
//        }
        PluginManager.PlayerInfoList.get(PluginManager.Plugin.getServer().getPlayer(UUID.fromString(this.Response.getUuid()))).setIsProcessing(false, "Load Clipboard");
        this.cancel();
    }
}
