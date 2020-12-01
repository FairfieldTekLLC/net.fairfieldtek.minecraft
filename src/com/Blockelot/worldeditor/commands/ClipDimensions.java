package com.Blockelot.worldeditor.commands;

import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author geev
 */
public class ClipDimensions implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission(PluginManager.Config.Permission_Size) || (player = (Player) sender).hasPermission(PluginManager.Config.Permission_User) || player.isOp())) {
            if (PluginManager.PlayerInfoList.get(player).getIsProcessing()) {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }

            PlayerInfo pi = PluginManager.PlayerInfoList.get(player);
            if (pi.SelectStart == null || pi.SelectEnd == null) {
                player.sendMessage(ChatColor.YELLOW + "Please select something first!");
                return true;
            }

            int x = Math.abs(pi.SelectStart.X - pi.SelectEnd.X);
            int y = Math.abs(pi.SelectStart.Y - pi.SelectEnd.X);
            int z = Math.abs(pi.SelectStart.Z - pi.SelectEnd.Z);

            player.sendMessage(ChatColor.YELLOW + "Your selection dimension is " + x + " " + y + " " + z + ".");

            return true;
        }
        return true;
    }
}
