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
            Player player = (Player)sender;
            if (args.length != 1) {
                player.sendMessage("Usage: /fft.reg <EmailAddress>");
            }
            new RegisterTaskRequest(player, args[0]).runTaskAsynchronously((org.bukkit.plugin.Plugin)Initialization.Plugin);
        }
        return true;
    }
}

