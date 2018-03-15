package net.fairfieldtek.minecraft.worldeditor.commands.filesystem;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.LsTaskRequest;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LS
implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            
             if (Initialization.PlayerInfoList.get(player).getIsProcessing())
            {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }
            Initialization.PlayerInfoList.get(player).setIsProcessing(true, "LS");
            PlayerInfo pi = Initialization.PlayerInfoList.get(player);
            player.sendMessage(ChatColor.RED + "Requesting directory Listing for '" + pi.getCurrentPath() + "'.");
            new LsTaskRequest(player.getUniqueId().toString(), pi.getLastAuth(), pi.getCurrentPath()).runTaskAsynchronously((org.bukkit.plugin.Plugin)Initialization.Plugin);
        }
        return true;
    }
}

