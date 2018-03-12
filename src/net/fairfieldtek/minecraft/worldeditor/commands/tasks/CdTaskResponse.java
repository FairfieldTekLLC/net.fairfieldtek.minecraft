package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.CdResponse;
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
        Player player = Initialization.Plugin.getServer().getPlayer(UUID.fromString(this.CdResponse.getUuid()));
        if (player == null) {
            return;
        }
        Initialization.PlayerInfoList.get(player).setIsProcessing(false,"Cd");
        Initialization.PlayerInfoList.get(player).setLastAuth(this.CdResponse.getLastAuth());
        Initialization.PlayerInfoList.get(player).setCurrentPath(this.CdResponse.getDirectoryPath());
        if (!this.CdResponse.getWasSuccessful()) {
            player.sendMessage(ChatColor.RED + this.CdResponse.getMessage());
        }
        player.sendMessage("Current Directory: " + this.CdResponse.getDirectoryPath());
        this.cancel();
    }
}
