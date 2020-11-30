package com.Blockelot.worldeditor.commands.filesystem;

import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.commands.tasks.AuthenticateTaskRequest;
import com.Blockelot.worldeditor.commands.tasks.LoginTaskRequest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Authenticate
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;

        if (sender instanceof Player && ((player = (Player) sender).hasPermission(PluginManager.Config.Permission_FileSystem) || player.isOp())) {

            try {
                if (PluginManager.PlayerInfoList.get(player).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }

                PluginManager.PlayerInfoList.get(player).setIsProcessing(true, "Authenticate");

                if (args.length == 1) {

                    PluginManager.PlayerInfoList.get(player).setLastAuth(args[0]);

                    player.sendMessage("Processing Login....");

                    new LoginTaskRequest(PluginManager.PlayerInfoList.get(player), args[0]).runTaskAsynchronously((org.bukkit.plugin.Plugin) PluginManager.Plugin);

                    return true;

                }

                new AuthenticateTaskRequest(PluginManager.PlayerInfoList.get(player)).runTaskAsynchronously((org.bukkit.plugin.Plugin) PluginManager.Plugin);

                player.sendMessage("Requesting Authenticating against Library...");

                player.sendMessage("After email use: " + ChatColor.YELLOW + "'/fft.auth <Auth Token>'" + ChatColor.WHITE + " to login.");

            } catch (Exception e) {
                PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Authenticate");

                System.out.println(e.getLocalizedMessage());

                System.out.println(e.getMessage());
            }
        }
        return true;
    }
}
