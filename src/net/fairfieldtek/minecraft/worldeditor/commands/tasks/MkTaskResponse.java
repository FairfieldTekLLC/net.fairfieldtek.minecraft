package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.MkResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MkTaskResponse
        extends BukkitRunnable {

    public MkResponse MkResponse;

    public MkTaskResponse(MkResponse mkResponse) {
        this.MkResponse = mkResponse;
    }

    @Override
    public void run() {
        Player player = Initialization.Plugin.getServer().getPlayer(UUID.fromString(this.MkResponse.getUuid()));
        if (player == null) {
            return;
        }
        Initialization.PlayerInfoList.get(player).setLastAuth(this.MkResponse.getLastAuth());
        Initialization.PlayerInfoList.get(player).setCurrentPath(this.MkResponse.getDirectoryPath());
        if (!this.MkResponse.getWasSuccessful()) {
            player.sendMessage(ChatColor.RED + this.MkResponse.getMessage());
        } else {
            player.sendMessage("Directory created.");
        }
        player.sendMessage("Current Directory: " + this.MkResponse.getDirectoryPath());
        this.cancel();
    }
}
