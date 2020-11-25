package net.fairfieldtek.minecraft.worldeditor.commands;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.Util.BlockUtil;
import net.fairfieldtek.minecraft.Util.PlayerUtils;
import net.fairfieldtek.minecraft.worldeditor.container.BlockInfo;
import net.fairfieldtek.minecraft.worldeditor.container.IPoint;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.fairfieldtek.minecraft.worldeditor.container.BlockCollection;

public class RegenChunk
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
                Initialization.PlayerInfoList.get(player).setIsProcessing(true, "REGEN");
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
                IPoint point = new IPoint(x, y, z);
                World world = player.getWorld();
                Block block = world.getBlockAt(point.X, point.Y, point.Z);
                Chunk chunk = world.getChunkAt(block);
                PlayerInfo pi = Initialization.PlayerInfoList.get(player);

                //pi.UndoSchematic.Clear();
                BlockCollection undo = pi.NewUndo();

                //pi.UndoBuffer.clear();
                player.sendMessage(ChatColor.RED + "Making everything right in the world again...");
                player.sendMessage(ChatColor.RED + "Putting existing chunk blocks into Undo Buffer...");
                int ix = 0;
                while (x <= 15) {
                    for (int iz = 0; iz <= 15; ++iz) {
                        for (int iy = 255; iy >= 0; --iy) {
                            block = chunk.getBlock(ix, iy, iz);
                            //BlockDef blockDef = BlockUtil.GetBlockDef(block, 0, 0, 0, player);
                            undo.AddBlock(block, 0, 0, 0, player);
                            //pi.UndoBuffer.add(blockDef);
                        }
                    }
                    ++x;
                }
                int cx = chunk.getX();
                int cy = chunk.getZ();
                player.sendMessage("Regenerating Chunk for location " + point.X + " " + point.Y + " " + point.Z);
                world.regenerateChunk(cx, cy);
                player.sendMessage("Finished Regenerating Chunk for location " + point.X + " " + point.Y + " " + point.Z);
                Initialization.PlayerInfoList.get(player).setIsProcessing(false, "REGEN");
            } catch (Exception e) {
                Initialization.PlayerInfoList.get(player).setIsProcessing(false, "REGEN");
                System.out.println(e.getLocalizedMessage());
                System.out.println(e.getMessage());
            }
        }
        return true;
    }
}
