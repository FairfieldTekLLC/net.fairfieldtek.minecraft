package net.fairfieldtek.minecraft.worldeditor.commands;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.DeleteTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Delete
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("fft.we.editor") || player.isOp()) {
            try {
                if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }
                Initialization.PlayerInfoList.get(player).setIsProcessing(true, "Delete");

                if (!Initialization.PlayerInfoList.containsKey(player)) {
                    player.sendMessage("Select something first!");
                }
                if (Initialization.PlayerInfoList.get(player).SelectStart == null || Initialization.PlayerInfoList.get(player).SelectEnd == null) {
                    player.sendMessage("Select something with /fft.we.select");
                }
                player.sendMessage(ChatColor.RED + "Starting Delete Procedure...");
                DeleteTask ut = new DeleteTask(player);

                ut.runTaskTimer((org.bukkit.plugin.Plugin) Initialization.Plugin, 1, 15);

            } catch (Exception e) {
                Initialization.PlayerInfoList.get(player).setIsProcessing(false, "Delete");
                System.out.println(e.getLocalizedMessage());
                System.out.println(e.getMessage());
            }

        }
        return true;
    }
}
