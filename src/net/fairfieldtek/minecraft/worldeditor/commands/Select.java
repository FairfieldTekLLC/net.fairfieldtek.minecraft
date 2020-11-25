package net.fairfieldtek.minecraft.worldeditor.commands;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.Util.PlayerUtils;
import net.fairfieldtek.minecraft.worldeditor.container.IPoint;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Select
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission("fft.we.editor") || player.isOp())) {
            if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }
            int x = 0;
            int y = 0;
            int z = 0;
            if (args.length == 0) {
                Block target = PlayerUtils.getTargetBlock(player, 10);
                x = target.getX();
                y = target.getY();
                z = target.getZ();
            } else {
                if (args.length != 3) {
                    player.sendMessage("Location Required in X Y Z format.");
                    return true;
                }
                try {
                    x = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    player.sendMessage("X value '" + args[0] + "' is not valid.");
                    return true;
                }
                try {
                    y = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    player.sendMessage("Y value '" + args[1] + "' is not valid.");
                    return true;
                }
                try {
                    z = Integer.parseInt(args[2]);
                } catch (Exception e) {
                    player.sendMessage("Z value '" + args[2] + "' is not valid.");
                    return true;
                }
            }
            if (y > player.getWorld().getMaxHeight()) {
                y = player.getWorld().getMaxHeight();
            }
            IPoint point = new IPoint(x, y, z);
            if (Initialization.PlayerInfoList.get(player).SelectStart == null) {
                Initialization.PlayerInfoList.get(player).SelectStart = point;
                player.sendMessage(ChatColor.YELLOW + "Selected Start Point " + point.toString());
                return true;
            }
            if (Initialization.PlayerInfoList.get(player).SelectEnd == null) {
                Initialization.PlayerInfoList.get(player).SelectEnd = point;
                player.sendMessage(ChatColor.YELLOW + "Selected End Point " + point.toString());
                return true;
            }
            player.sendMessage(ChatColor.RED + "Positions already set, clear them first!");
            return true;
        }
        return true;
    }
}
