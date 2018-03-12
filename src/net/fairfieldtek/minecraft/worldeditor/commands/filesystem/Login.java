package net.fairfieldtek.minecraft.worldeditor.commands.filesystem;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.LoginTaskRequest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Login
implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (args.length != 1) {
                player.sendMessage("Usage: /fft.login <Auth Token>");
            }
             if (Initialization.PlayerInfoList.get(player).getIsProcessing())
            {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }
            Initialization.PlayerInfoList.get(player).setIsProcessing(true, "Login");
            player.sendMessage(ChatColor.RED + "Requesting Authentication....");
            new LoginTaskRequest(player, args[0]).runTaskAsynchronously((org.bukkit.plugin.Plugin)Initialization.Plugin);
        }
        return true;
    }
}

