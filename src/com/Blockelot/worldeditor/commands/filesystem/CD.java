package com.Blockelot.worldeditor.commands.filesystem;

import com.Blockelot.PluginManager;
import com.Blockelot.Util.ServerUtil;
import com.Blockelot.worldeditor.commands.tasks.CdTaskRequest;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CD
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;

        if (sender instanceof Player && ((player = (Player) sender).hasPermission(PluginManager.Config.Permission_FileSystem) || player.isOp())) {

            try {

                if (args.length != 1) {
                    player.sendMessage("Usage: /fft.cd <Directory>");
                    return true;
                }
                if (PluginManager.PlayerInfoList.get(player).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }
                PluginManager.PlayerInfoList.get(player).setIsProcessing(true, "CD");

                PlayerInfo pi = PluginManager.PlayerInfoList.get(player);

                player.sendMessage(ChatColor.RED + "Requesting directory change.");

                new CdTaskRequest(pi, args[0]).runTaskAsynchronously((org.bukkit.plugin.Plugin) PluginManager.Plugin);

            } catch (Exception e) {

                PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "CD");

                ServerUtil.consoleLog(e.getLocalizedMessage());

                ServerUtil.consoleLog(e.getMessage());

            }
        }
        return true;
    }
}
