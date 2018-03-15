package net.fairfieldtek.minecraft.worldeditor.commands.filesystem;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.MkTaskRequest;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MK
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {

                if (args.length != 1) {
                    player.sendMessage("Usage: /fft.mk <Directory>");
                    return true;
                }
                if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }
                Initialization.PlayerInfoList.get(player).setIsProcessing(true, "Mk");
                PlayerInfo pi = Initialization.PlayerInfoList.get(player);
                player.sendMessage(ChatColor.RED + "Requesting directory creation.");
                new MkTaskRequest(player.getUniqueId().toString(), pi.getLastAuth(), pi.getCurrentPath(), args[0]).runTaskAsynchronously((org.bukkit.plugin.Plugin) Initialization.Plugin);
            } catch (Exception e) {
                Initialization.PlayerInfoList.get(player).setIsProcessing(false, "Mk");
                System.out.println(e.getLocalizedMessage());
                System.out.println(e.getMessage());
            }
        }
        return true;
    }
}
