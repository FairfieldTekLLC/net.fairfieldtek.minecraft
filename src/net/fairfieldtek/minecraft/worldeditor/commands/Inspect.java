package net.fairfieldtek.minecraft.worldeditor.commands;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.Util.PlayerUtils;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Stairs;
import org.bukkit.ChatColor;

public class Inspect
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            try {
                Player player = (Player) sender;
                if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }

                player.sendMessage(ChatColor.YELLOW + "Inspecting Block:");

                Block target = PlayerUtils.getTargetBlock(player, 10);
                if (target == null) {
                    player.sendMessage(ChatColor.YELLOW + "Can't find target block!");
                    return true;
                }

                player.sendMessage(ChatColor.YELLOW + "Location: " + target.getX() + " " + target.getY() + " " + target.getZ());
                player.sendMessage(ChatColor.YELLOW + "Biome: " + target.getBiome().name());
                player.sendMessage(ChatColor.YELLOW + "Material is: " + target.getType().name());
                player.sendMessage(ChatColor.YELLOW + "Magic Number: " + target.getData());
                player.sendMessage(ChatColor.YELLOW + "Humidity: " + target.getHumidity());
                player.sendMessage(ChatColor.YELLOW + "Temperature: " + target.getTemperature());
                player.sendMessage(ChatColor.YELLOW + "Block Powered: " + target.getBlockPower());

                MaterialData sMat = target.getState().getData();

                if (sMat instanceof Stairs) {
                    Stairs stairs = (Stairs) sMat;
                    player.sendMessage(ChatColor.YELLOW + "Facing: " + stairs.getDescendingDirection().name() + " : " + stairs.isInverted() + " " + stairs.getFacing().getModX() + " " + stairs.getFacing().getModY() + " " + stairs.getFacing().getModZ());
                } else if (sMat instanceof Directional) {
                    Directional d = (Directional) sMat;
                    player.sendMessage(ChatColor.YELLOW + "Facing: " + d.getFacing().getModX() + " " + d.getFacing().getModY() + " " + d.getFacing().getModZ());
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e.getLocalizedMessage());
                System.out.println("Exception: " + e.getMessage());
            }

        }
        return true;
    }
}
