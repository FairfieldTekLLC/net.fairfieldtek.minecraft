package com.Blockelot.worldeditor.commands;

import com.Blockelot.PluginManager;
import com.Blockelot.Util.ServerUtil;
import com.Blockelot.worldeditor.commands.tasks.DeleteTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Delete
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission(PluginManager.Config.Permission_Delete) || (player = (Player) sender).hasPermission(PluginManager.Config.Permission_Editor) || player.isOp())) {
            try {
                if (PluginManager.PlayerInfoList.get(player).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }
                PluginManager.PlayerInfoList.get(player).setIsProcessing(true, "Delete");

                if (!PluginManager.PlayerInfoList.containsKey(player)) {
                    player.sendMessage("Select something first!");
                }
                if (PluginManager.PlayerInfoList.get(player).SelectStart == null || PluginManager.PlayerInfoList.get(player).SelectEnd == null) {
                    player.sendMessage("Select something with /fft.we.select");
                }
                player.sendMessage(ChatColor.RED + "Starting Delete Procedure...");
                DeleteTask ut = new DeleteTask(player);

                ut.runTaskTimer((org.bukkit.plugin.Plugin) PluginManager.Plugin, 1, 15);

            } catch (Exception e) {
                PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Delete");
                ServerUtil.consoleLog(e.getLocalizedMessage());
                ServerUtil.consoleLog(e.getMessage());
            }

        }
        return true;
    }
}
