package com.Blockelot.worldeditor.commands;

import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.commands.tasks.LsTaskRequest;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LS
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {
                if (PluginManager.PlayerInfoList.get(player).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }
                PluginManager.PlayerInfoList.get(player).setIsProcessing(true, "LS");

                PlayerInfo pi = PluginManager.PlayerInfoList.get(player);
                player.sendMessage("Requesting directory Listing for '" + pi.getCurrentPath() + "'.");

                new LsTaskRequest(pi).runTaskAsynchronously((org.bukkit.plugin.Plugin) PluginManager.Plugin);
            } catch (Exception e) {
                PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "LS");
                System.out.println(e.getLocalizedMessage());
                System.out.println(e.getMessage());
            }
        }

        return true;
    }
}
