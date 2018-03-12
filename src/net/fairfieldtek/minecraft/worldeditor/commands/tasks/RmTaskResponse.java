package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.RmResponse;
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
        Player player = Initialization.Plugin.getServer().getPlayer(UUID.fromString(this.RmResponse.getUuid()));
        if (player == null) {
            return;
        }
        
        Initialization.PlayerInfoList.get(player).setLastAuth(this.RmResponse.getLastAuth());
        Initialization.PlayerInfoList.get(player).setCurrentPath(this.RmResponse.getDirectoryPath());
        Initialization.PlayerInfoList.get(player).setIsProcessing(false,"Rm");
        if (!this.RmResponse.getWasSuccessful()) {
            player.sendMessage(ChatColor.RED + this.RmResponse.getMessage());
        } else {
            player.sendMessage(ChatColor.YELLOW + "Folder Removed.");
        }
        player.sendMessage("Current Directory: " + this.RmResponse.getDirectoryPath());
        this.cancel();
    }
}
