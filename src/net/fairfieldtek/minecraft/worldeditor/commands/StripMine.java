package net.fairfieldtek.minecraft.worldeditor.commands;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.StripMineTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.command.CommandExecutor;

/**
 *
 * @author geev
 */
public class StripMine implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission("fft.we.editor") || player.isOp())) {

            if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }

            Initialization.PlayerInfoList.get(player).setIsProcessing(true, "StripMine");
            player.sendMessage(ChatColor.RED + "Starting to stripmine chunk");
            StripMineTask ut = new StripMineTask(player.getUniqueId());

            ut.runTaskTimer((org.bukkit.plugin.Plugin) Initialization.Plugin, 1, 15);
        }
        return true;

    }
}
