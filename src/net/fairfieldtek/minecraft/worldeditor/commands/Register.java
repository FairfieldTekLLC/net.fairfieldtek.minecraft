package net.fairfieldtek.minecraft.worldeditor.commands;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.RegisterTaskRequest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Register
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }
            if (args.length != 1) {
                player.sendMessage("Usage: /fft.reg <EmailAddress>");
            }
            Initialization.PlayerInfoList.get(player).setIsProcessing(true,"Register");
            new RegisterTaskRequest(player, args[0]).runTaskAsynchronously((org.bukkit.plugin.Plugin) Initialization.Plugin);
        }
        return true;
    }
}
