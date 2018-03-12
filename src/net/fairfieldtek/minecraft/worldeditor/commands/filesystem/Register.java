package net.fairfieldtek.minecraft.worldeditor.commands.filesystem;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.RegisterTaskRequest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Register
implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (args.length != 1) {
                player.sendMessage("Usage: /fft.reg <EmailAddress>");
            }
             if (Initialization.PlayerInfoList.get(player).getIsProcessing())
            {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }
            Initialization.PlayerInfoList.get(player).setIsProcessing(true, "Register");
            player.sendMessage(ChatColor.RED + "Starting Registration...");
            new RegisterTaskRequest(player, args[0]).runTaskAsynchronously((org.bukkit.plugin.Plugin)Initialization.Plugin);
        }
        return true;
    }
}

