package com.Blockelot.worldeditor.commands;

import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Print
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission("fft.we.editor") || player.isOp()) && PluginManager.PlayerInfoList.containsKey(player)) {
            PlayerInfo info = PluginManager.PlayerInfoList.get(player);
            if (PluginManager.PlayerInfoList.get(player).getIsProcessing()) {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }

            if (info.SelectStart != null) {
                player.sendMessage("Start Position : " + info.SelectStart.toString());
            } else {
                player.sendMessage("Starting Position not defined.");
            }
            if (info.SelectEnd != null) {
                player.sendMessage("End Position : " + info.SelectEnd.toString());
            } else {
                player.sendMessage("Ending Position not defined.");
            }
        }
        return true;
    }
}
