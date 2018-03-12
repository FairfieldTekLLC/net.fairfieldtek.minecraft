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

public class Inspect
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }
            Block target = PlayerUtils.getTargetBlock(player, 10);
            MaterialData sMat = target.getState().getData();
            if (sMat instanceof Stairs) {
                Stairs stairs = (Stairs) sMat;
                player.sendMessage(stairs.getDescendingDirection().name() + " : " + stairs.isInverted() + " " + stairs.getFacing().getModX() + " " + stairs.getFacing().getModY() + " " + stairs.getFacing().getModZ());
            } else if (sMat instanceof Directional) {
                Directional d = (Directional) sMat;
                player.sendMessage("" + d.getFacing().getModX() + " " + d.getFacing().getModY() + " " + d.getFacing().getModZ());
            }
        }
        return true;
    }
}
