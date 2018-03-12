package net.fairfieldtek.minecraft.worldeditor.commands.filesystem;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.AuthenticateTaskRequest;
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
            Player player = (Player)sender;
            new AuthenticateTaskRequest(player).runTaskAsynchronously((org.bukkit.plugin.Plugin)Initialization.Plugin);
            player.sendMessage(ChatColor.RED + "Authenticating against Library...");
        }
        return true;
    }
}

