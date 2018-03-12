package net.fairfieldtek.minecraft.worldeditor.commands;

import net.fairfieldtek.minecraft.Util.BlockUtil;
import net.fairfieldtek.minecraft.Util.PlayerUtils;
import net.fairfieldtek.minecraft.worldeditor.container.BlockDef;
import net.fairfieldtek.minecraft.worldeditor.enumeration.Axis;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Rotate
implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player)sender).hasPermission("fft.we.editor") || player.isOp())) {
            int degrees;
            int z;
            Axis axis;
            int y;
            int x;
            Block block;
            block28 : {
                axis = Axis.N;
                x = 0;
                y = 0;
                z = 0;
                degrees = 0;
                try {
                    block29 : {
                        if (args[0].equals("X") || args[0].equals("x") || args[0].equals("Y") || args[0].equals("y") || args[0].equals("Z") || args[0].equals("z")) {
                            block = PlayerUtils.getTargetBlock(player, 10);
                            x = block.getX();
                            y = block.getY();
                            z = block.getZ();
                            if (args[0].equals("x") || args[0].equals("X")) {
                                axis = Axis.X;
                            }
                            if (args[0].equals("y") || args[0].equals("Y")) {
                                axis = Axis.Y;
                            }
                            if (args[0].equals("z") || args[0].equals("Z")) {
                                axis = Axis.Z;
                            }
                            try {
                                degrees = Integer.parseInt(args[1]);
                                break block28;
                            }
                            catch (Exception e) {
                                player.sendMessage("valid formats: ");
                                player.sendMessage("               /fft.we.rotate {Axis} {Degrees}");
                                player.sendMessage("               /fft.we.rotate x y z {Axis} {degrees}");
                                return true;
                            }
                        }
                        try {
                            x = Integer.parseInt(args[0]);
                        }
                        catch (Exception e) {
                            player.sendMessage("valid formats: ");
                            player.sendMessage("               /fft.we.rotate {Axis} {Degrees}");
                            player.sendMessage("               /fft.we.rotate x y z {Axis} {degrees}");
                            return true;
                        }
                        try {
                            y = Integer.parseInt(args[1]);
                        }
                        catch (Exception e) {
                            player.sendMessage("valid formats: ");
                            player.sendMessage("               /fft.we.rotate {Axis} {Degrees}");
                            player.sendMessage("               /fft.we.rotate x y z {Axis} {degrees}");
                            return true;
                        }
                        try {
                            z = Integer.parseInt(args[2]);
                        }
                        catch (Exception e) {
                            player.sendMessage("valid formats: ");
                            player.sendMessage("               /fft.we.rotate {Axis} {Degrees}");
                            player.sendMessage("               /fft.we.rotate x y z {Axis} {degrees}");
                            return true;
                        }
                        try {
                            if (args[3].equals("x") || args[3].equals("X")) {
                                axis = Axis.X;
                                break block29;
                            }
                            if (args[3].equals("y") || args[3].equals("Y")) {
                                axis = Axis.Y;
                                break block29;
                            }
                            if (args[3].equals("z") || args[3].equals("Z")) {
                                axis = Axis.Z;
                                break block29;
                            }
                            player.sendMessage("valid formats: ");
                            player.sendMessage("               /fft.we.rotate {Axis} {Degrees}");
                            player.sendMessage("               /fft.we.rotate x y z {Axis} {degrees}");
                            return true;
                        }
                        catch (Exception e) {
                            player.sendMessage("valid formats: ");
                            player.sendMessage("               /fft.we.rotate {Axis} {Degrees}");
                            player.sendMessage("               /fft.we.rotate x y z {Axis} {degrees}");
                            return true;
                        }
                    }
                    try {
                        degrees = Integer.parseInt(args[4]);
                    }
                    catch (Exception e) {
                        player.sendMessage("valid formats: ");
                        player.sendMessage("               /fft.we.rotate {Axis} {Degrees}");
                        player.sendMessage("               /fft.we.rotate x y z {Axis} {degrees}");
                        return true;
                    }
                }
                catch (Exception e) {
                    player.sendMessage("valid formats: ");
                    player.sendMessage("               /fft.we.rotate {Axis} {Degrees}");
                    player.sendMessage("               /fft.we.rotate x y z {Axis} {degrees}");
                    return true;
                }
            }
            if (y > player.getWorld().getMaxHeight()) {
                y = player.getWorld().getMaxHeight();
            }
            block = player.getWorld().getBlockAt(x, y, z);
            BlockDef blockDef = BlockUtil.GetBlockDef(block, 0, 0, 0, player);
            switch (axis) {
                case X: {
                    blockDef = BlockUtil.GetRotX(blockDef, degrees);
                    break;
                }
                case Y: {
                    blockDef = BlockUtil.GetRotY(blockDef, degrees);
                    break;
                }
                case Z: {
                    blockDef = BlockUtil.GetRotZ(blockDef, degrees);
                }
            }
            BlockUtil.SetBlock(block, blockDef, player, false);
            player.sendMessage(ChatColor.RED + "Rotation Complete.");
        }
        return true;
    }

}

