package net.fairfieldtek.minecraft.worldeditor.commands.filesystem;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.RmTaskRequest;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RM
implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (args.length != 1) {
                player.sendMessage("Usage: /fft.rm <Directory>");
                return true;
            }
            PlayerInfo pi = Initialization.PlayerInfoList.get(player);
            player.sendMessage(ChatColor.RED + "Requesting directory removal.");
            new RmTaskRequest(player.getUniqueId().toString(), pi.getLastAuth(), pi.getCurrentPath(), args[0]).runTaskAsynchronously((org.bukkit.plugin.Plugin)Initialization.Plugin);
        }
        return true;
    }
}

