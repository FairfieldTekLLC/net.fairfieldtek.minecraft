package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.SchematicDataResponse;
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
        Player player = Initialization.Plugin.getServer().getPlayer(UUID.fromString(this.Response.getUuid()));
        if (player == null) {
            return;
        }
        Initialization.PlayerInfoList.get(player).setIsProcessing(false,"SaveClipboard");
        Initialization.PlayerInfoList.get(player).setLastAuth(this.Response.getLastAuth());
        if (!this.Response.getWasSuccessful()) {
            player.sendMessage(ChatColor.YELLOW + "File not saved.");
            player.sendMessage(ChatColor.RED + this.Response.getMessage());
        } else {
            player.sendMessage(ChatColor.GREEN + this.Response.getMessage());
        }
        this.cancel();
    }
}
