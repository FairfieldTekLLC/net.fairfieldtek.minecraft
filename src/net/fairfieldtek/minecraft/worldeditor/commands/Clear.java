package net.fairfieldtek.minecraft.worldeditor.commands;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Clear
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission("fft.we.editor") || player.isOp())) {
            if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }
            
            PlayerInfo pi = Initialization.PlayerInfoList.get(player);
            pi.SelectStart = null;
            pi.SelectEnd = null;
            pi.ClipBoard.clear();
            player.sendMessage(ChatColor.RED + "Cleared Selection.");
            return true;
        }
        return true;

    }
}
