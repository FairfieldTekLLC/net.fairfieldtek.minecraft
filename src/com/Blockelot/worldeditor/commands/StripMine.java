package com.Blockelot.worldeditor.commands;

import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.commands.tasks.StripMineTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.command.CommandExecutor;

/**
 *
 * @author geev
 */
public class StripMine implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission(PluginManager.Config.Permission_Editor) || (player = (Player) sender).hasPermission(PluginManager.Config.Permission_StripMine) || player.isOp())) {

            if (PluginManager.PlayerInfoList.get(player).getIsProcessing()) {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }

            PluginManager.PlayerInfoList.get(player).setIsProcessing(true, "StripMine");
            player.sendMessage(ChatColor.RED + "Starting to stripmine chunk");
            StripMineTask ut = new StripMineTask(PluginManager.PlayerInfoList.get(player));

            ut.runTaskTimer((org.bukkit.plugin.Plugin) PluginManager.Plugin, 1, 15);
        }
        return true;

    }
}
