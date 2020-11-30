package com.Blockelot.worldeditor.commands;

import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.commands.tasks.UndoTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Undo
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission(PluginManager.Config.Permission_Undo) || (player = (Player) sender).hasPermission(PluginManager.Config.Permission_Editor) || player.isOp())) {

            if (PluginManager.PlayerInfoList.get(player).getIsProcessing()) {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }
            PluginManager.PlayerInfoList.get(player).setIsProcessing(true, "Undo");
            player.sendMessage(ChatColor.RED + "Starting Undo, guess you really messed up, eh?");
            UndoTask ut = new UndoTask(PluginManager.PlayerInfoList.get(player));

            ut.runTaskTimer((org.bukkit.plugin.Plugin) PluginManager.Plugin, 1, 16);
        }
        return true;
    }
}
