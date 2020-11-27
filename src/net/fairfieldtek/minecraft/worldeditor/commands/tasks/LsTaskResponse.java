package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.Util.MiscUtil;
import net.fairfieldtek.minecraft.worldeditor.http.DirectoryElement;
import net.fairfieldtek.minecraft.worldeditor.http.LsResponse;
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
        Player player = Initialization.Plugin.getServer().getPlayer(UUID.fromString(this.LsResponse.getUuid()));
        if (player == null) {
            return;
        }
        Initialization.PlayerInfoList.get(player).setIsProcessing(false, "LS");
        Initialization.PlayerInfoList.get(player).setLastAuth(this.LsResponse.getLastAuth());
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
        this.cancel();
    }
}
