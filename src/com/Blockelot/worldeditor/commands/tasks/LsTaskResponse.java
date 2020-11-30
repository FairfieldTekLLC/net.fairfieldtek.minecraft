package com.Blockelot.worldeditor.commands.tasks;

import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.Util.MiscUtil;
import com.Blockelot.worldeditor.http.DirectoryElement;
import com.Blockelot.worldeditor.http.LsResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LsTaskResponse
        extends BukkitRunnable {

    public LsResponse LsResponse;

    public LsTaskResponse(LsResponse lsResponse) {
        this.LsResponse = lsResponse;
    }

    @Override
    public void run() {
        Player player = PluginManager.Plugin.getServer().getPlayer(UUID.fromString(this.LsResponse.getUuid()));
        try {
            if (player == null) {
                return;
            }
            PluginManager.PlayerInfoList.get(player).setLastAuth(this.LsResponse.getAuth());
            player.sendMessage(ChatColor.WHITE + "Displaying Directory Contents");

            for (DirectoryElement element : this.LsResponse.getContents()) {
                switch (element.getElementType()) {
                    case 0: {
                        player.sendMessage(ChatColor.WHITE + "-> ( " + ChatColor.BLUE + "D" + ChatColor.WHITE + " ) " + element.getName());
                        continue;
                    }
                    case 1: {
                        player.sendMessage(ChatColor.WHITE + "-> ( " + ChatColor.BLUE + "S" + ChatColor.WHITE + " ) " + MiscUtil.padRight(element.getName(), 52, " ") + "(" + element.getBlockCount() + ")");
                    }
                }
            }
            player.sendMessage("Current Directory: " + this.LsResponse.getDirectoryPath());
        } catch (Exception e) {
            
        }
        PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "LS");
        this.cancel();
    }
}
