package net.fairfieldtek.minecraft.worldeditor.commands.filesystem;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.AuthenticateTaskRequest;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.LoginTaskRequest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Authenticate
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }
                Initialization.PlayerInfoList.get(player).setIsProcessing(true, "Authenticate");

                if (args.length == 1) {
                    player.sendMessage("Processing Login....");

                    new LoginTaskRequest(player, args[0]).runTaskAsynchronously((org.bukkit.plugin.Plugin) Initialization.Plugin);
                    return true;
                }

                new AuthenticateTaskRequest(player).runTaskAsynchronously((org.bukkit.plugin.Plugin) Initialization.Plugin);

                player.sendMessage("Requesting Authenticating against Library...");
                player.sendMessage("After email use: " + ChatColor.YELLOW + "'/fft.auth <Auth Token>'" + ChatColor.WHITE + " to login.");
            } catch (Exception e) {

            }
        }
        return true;
    }
}
