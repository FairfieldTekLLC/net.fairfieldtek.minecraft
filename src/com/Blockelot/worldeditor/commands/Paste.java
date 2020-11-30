package com.Blockelot.worldeditor.commands;

import com.Blockelot.PluginManager;
import com.Blockelot.Util.PlayerUtils;
import com.Blockelot.Util.ServerUtil;
import com.Blockelot.worldeditor.commands.tasks.PasteTask;
import com.Blockelot.worldeditor.container.PlayerInfo;
import com.Blockelot.worldeditor.enumeration.Axis;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Paste
        implements CommandExecutor {

    private Block GetBlockByArgs(String[] args, Player player) {
        int z;
        int y;
        int x;
        if (args.length < 3) {
            return null;
        }
        try {
            x = Integer.parseInt(args[0]);
        } catch (Exception e) {
            player.sendMessage("X value '" + args[0] + "' is not valid.");
            return null;
        }
        try {
            y = Integer.parseInt(args[1]);
        } catch (Exception e) {
            player.sendMessage("Y value '" + args[1] + "' is not valid.");
            return null;
        }
        try {
            z = Integer.parseInt(args[2]);
        } catch (Exception e) {
            player.sendMessage("Z value '" + args[2] + "' is not valid.");
            return null;
        }
        World world = player.getWorld();
        return world.getBlockAt(x, y, z);
    }

    private boolean CheckPlayerInfo(Player player) {
        if (!PluginManager.PlayerInfoList.containsKey(player)) {
            player.sendMessage("Select something first!");
            return false;
        }
        return true;
    }

    private boolean CheckClipBoard(Player player) {
        PlayerInfo pi = PluginManager.PlayerInfoList.get(player);
        if (pi.ClipSchematic.IsEmpty()) {
            player.sendMessage("Clipboard is empty.");
            return false;
        }
        return true;
    }

    private Block NoArgs(Player player) {
        return PlayerUtils.getTargetBlock(player, 10);
    }

    private Axis GetAxis(String[] args, int pos) {
        if (args.length < pos + 1) {
            return Axis.N;
        }
        switch (args[pos]) {
            case "X": {
                return Axis.X;
            }
            case "x": {
                return Axis.X;
            }
            case "Y": {
                return Axis.Y;
            }
            case "y": {
                return Axis.Y;
            }
            case "Z": {
                return Axis.Z;
            }
            case "z": {
                return Axis.Z;
            }
        }
        return Axis.N;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission(PluginManager.Config.Permission_Paste) || (player = (Player) sender).hasPermission(PluginManager.Config.Permission_Editor) || player.isOp())) {
            try {
                if (PluginManager.PlayerInfoList.get(player).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }
                if (PluginManager.PlayerInfoList.get(player).ClipSchematic.IsEmpty()) {
                    player.sendMessage("Nothing in clipboard.");
                    PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Paste");
                    return true;
                }
                PluginManager.PlayerInfoList.get(player).setIsProcessing(true, "Paste");

                Axis axis = Axis.N;
                double degrees = 0.0;
                Block targetBlock = null;
                if (!this.CheckPlayerInfo(player)) {
                    return true;
                }
                if (!this.CheckClipBoard(player)) {
                    return true;
                }
                boolean force = false;
                if (args.length == 0) {
                    targetBlock = this.NoArgs(player);
                    axis = Axis.N;
                    degrees = 0.0;
                } else if (args[0].equalsIgnoreCase("x") || args[0].equalsIgnoreCase("y") || args[0].equalsIgnoreCase("z")) {
                    axis = this.GetAxis(args, 0);
                    degrees = Integer.parseInt(args[1]);
                    targetBlock = this.NoArgs(player);
                    if (args.length >= 3 && args[2].equalsIgnoreCase("f")) {
                        force = true;
                    }
                } else if (args.length == 3) {
                    axis = Axis.N;
                    degrees = 0.0;
                    targetBlock = this.GetBlockByArgs(args, player);
                } else if (args[3].equalsIgnoreCase("x") || args[3].equalsIgnoreCase("y") || args[3].equalsIgnoreCase("z")) {
                    axis = this.GetAxis(args, 3);
                    degrees = Integer.parseInt(args[4]);
                    targetBlock = this.GetBlockByArgs(args, player);
                    if (args.length >= 6 && args[5].equalsIgnoreCase("f")) {
                        force = true;
                    }
                } else {
                    player.sendMessage("Valid Formats for Paste are:");
                    player.sendMessage("                             /fft.we.paste");
                    player.sendMessage("                             /fft.we.paste {Rotation} {Degrees}");
                    player.sendMessage("                             /fft.we.paste x y z");
                    player.sendMessage("                             /fft.we.paste x y z {Rotation} {Degrees}");
                    return true;
                }
                Location tLoc = targetBlock.getLocation();
                if (!(axis != Axis.X && axis != Axis.Z || force)) {
                    PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Paste");
                    player.sendMessage("WARNING!!! The ONLY SAFE rotation is on the Y axis, put an 'f' at the end to force the rotation.");
                    player.sendMessage("ABORTING COPY");
                    return true;
                }
                player.sendMessage(ChatColor.RED + "Starting Paste Procedure...");
                PasteTask pt = new PasteTask(player, tLoc.getBlockX(), tLoc.getBlockY(), tLoc.getBlockZ(), axis, degrees);

                pt.runTaskTimer((org.bukkit.plugin.Plugin) PluginManager.Plugin, 2, 15);

            } catch (Exception e) {
                player.sendMessage("Valid Formats for Paste are:");
                player.sendMessage("                             /fft.we.paste");
                player.sendMessage("                             /fft.we.paste {Rotation} {Degrees}");
                player.sendMessage("                             /fft.we.paste x y z");
                player.sendMessage("                             /fft.we.paste x y z {Rotation} {Degrees}");
                ServerUtil.consoleLog(e.getLocalizedMessage());
                ServerUtil.consoleLog(e.getMessage());

                PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Paste");
                ServerUtil.consoleLog(e.getLocalizedMessage());
                ServerUtil.consoleLog(e.getMessage());

            }
        }
        return true;
    }
}
