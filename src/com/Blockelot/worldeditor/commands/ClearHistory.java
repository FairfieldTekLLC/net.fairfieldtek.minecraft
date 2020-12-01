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
public class ClearHistory
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission(PluginManager.Config.Permission_ClearHistory) || (player = (Player) sender).hasPermission(PluginManager.Config.Permission_User) || player.isOp())) {
            if (PluginManager.PlayerInfoList.get(player).getIsProcessing()) {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }

            PlayerInfo pi = PluginManager.PlayerInfoList.get(player);
            pi.ClearHistory();
            player.sendMessage(ChatColor.RED + "Cleared History.");
            return true;
        }
        return true;

    }
}
