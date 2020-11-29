package com.Blockelot.worldeditor.commands.filesystem;

import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.commands.tasks.SaveClipboardTaskRequest;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveClipboard
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                if (args.length != 1) {
                    player.sendMessage("Usage: /fft.Save <Schematic Name>");
                    return true;
                }
                if (PluginManager.PlayerInfoList.get(player).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }
                PluginManager.PlayerInfoList.get(player).setIsProcessing(true, "SaveClipboard");
                
                PlayerInfo pi = PluginManager.PlayerInfoList.get(player);
                
                if (pi.ClipSchematic.IsEmpty())
                {
                    player.sendMessage("No blocks in Clipboard.");
                    PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "SaveClipboard");
                }
                
                player.sendMessage(ChatColor.RED + "Requesting schematic save...");
                new SaveClipboardTaskRequest(pi,args[0]).runTaskAsynchronously((org.bukkit.plugin.Plugin) PluginManager.Plugin);
            } catch (Exception e) {
                PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "SaveClipboard");
                System.out.println(e.getLocalizedMessage());
                System.out.println(e.getMessage());
            }
        }
        return true;
    }
}
