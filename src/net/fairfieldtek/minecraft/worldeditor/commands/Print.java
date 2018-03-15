package net.fairfieldtek.minecraft.worldeditor.commands;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Print
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission("fft.we.editor") || player.isOp()) && Initialization.PlayerInfoList.containsKey(player)) {
            PlayerInfo info = Initialization.PlayerInfoList.get(player);
            if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
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
