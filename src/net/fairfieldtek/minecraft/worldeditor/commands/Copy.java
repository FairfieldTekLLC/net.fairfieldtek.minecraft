package net.fairfieldtek.minecraft.worldeditor.commands;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.CopyTask;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Copy
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission("fft.we.editor") || player.isOp())) {

            try {

                if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }

                Initialization.PlayerInfoList.get(player).setIsProcessing(true, "Copy");

                if (Initialization.PlayerInfoList.containsKey(player)) {
                    int sbx;
                    int sez;
                    int sby;
                    int sex;
                    int sbz;
                    int sey;
                    PlayerInfo pi = Initialization.PlayerInfoList.get(player);
                    if (pi.SelectStart == null || pi.SelectEnd == null) {
                        player.sendMessage("Starting and Ending Coordinates not set.  Use /fft.we.select x y z ");
                        return true;
                    }
                    if (pi.SelectStart.X > pi.SelectEnd.X) {
                        sbx = pi.SelectEnd.X;
                        sex = pi.SelectStart.X;
                    } else {
                        sex = pi.SelectEnd.X;
                        sbx = pi.SelectStart.X;
                    }
                    if (pi.SelectStart.Y > pi.SelectEnd.Y) {
                        sby = pi.SelectEnd.Y;
                        sey = pi.SelectStart.Y;
                    } else {
                        sey = pi.SelectEnd.Y;
                        sby = pi.SelectStart.Y;
                    }
                    if (pi.SelectStart.Z > pi.SelectEnd.Z) {
                        sbz = pi.SelectEnd.Z;
                        sez = pi.SelectStart.Z;
                    } else {
                        sez = pi.SelectEnd.Z;
                        sbz = pi.SelectStart.Z;
                    }
                    player.sendMessage(ChatColor.RED + "Starting Copy Procedure...");

                    CopyTask ct = new CopyTask(sbx, sex, sby, sey, sbz, sez, player.getUniqueId());

                    ct.runTaskTimer((org.bukkit.plugin.Plugin) Initialization.Plugin, 2, 15);

                }

            } catch (Exception e) {
                Initialization.PlayerInfoList.get(player).setIsProcessing(false, "Copy");
                System.out.println(e.getLocalizedMessage());
                System.out.println(e.getMessage());
            }
        }

        return true;
    }
}
