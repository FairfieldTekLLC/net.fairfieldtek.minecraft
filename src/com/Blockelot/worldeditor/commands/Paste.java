//End-User License Agreement (EULA) of Blockelot
//
//This End-User License Agreement ("EULA") is a legal agreement between you and Fairfield Tek L.L.C.. Our EULA was created by EULA Template for Blockelot.
//
//This EULA agreement governs your acquisition and use of our Blockelot software ("Software") directly from Fairfield Tek L.L.C. or indirectly through a Fairfield Tek L.L.C. authorized reseller or distributor (a "Reseller"). Our Privacy Policy was created by the Privacy Policy Generator.
//
//Please read this EULA agreement carefully before completing the installation process and using the Blockelot software. It provides a license to use the Blockelot software and contains warranty information and liability disclaimers.
//
//If you register for a free trial of the Blockelot software, this EULA agreement will also govern that trial. By clicking "accept" or installing and/or using the Blockelot software, you are confirming your acceptance of the Software and agreeing to become bound by the terms of this EULA agreement.
//
//If you are entering into this EULA agreement on behalf of a company or other legal entity, you represent that you have the authority to bind such entity and its affiliates to these terms and conditions. If you do not have such authority or if you do not agree with the terms and conditions of this EULA agreement, do not install or use the Software, and you must not accept this EULA agreement.
//
//This EULA agreement shall apply only to the Software supplied by Fairfield Tek L.L.C. herewith regardless of whether other software is referred to or described herein. The terms also apply to any Fairfield Tek L.L.C. updates, supplements, Internet-based services, and support services for the Software, unless other terms accompany those items on delivery. If so, those terms apply.
//
//License Grant
//Fairfield Tek L.L.C. hereby grants you a personal, non-transferable, non-exclusive licence to use the Blockelot software on your devices 
//in accordance with the terms of this EULA agreement.
//
//You are permitted to load the Blockelot software (for example a PC, laptop, mobile or tablet) under your control. You are responsible
//for ensuring your device meets the minimum requirements of the Blockelot software.
//
//You are not permitted to:
//
//Edit, alter, modify, adapt, translate or otherwise change the whole or any part of the Software nor permit the whole or any part of
//the Software to be combined with or become incorporated in any other software, nor decompile, disassemble or reverse engineer the 
//Software or attempt to do any such things
//
//Reproduce, copy, distribute, resell or otherwise use the Software for any commercial purpose
//Allow any third party to use the Software on behalf of or for the benefit of any third party
//Use the Software in any way which breaches any applicable local, national or international law
//use the Software for any purpose that Fairfield Tek L.L.C. considers is a breach of this EULA agreement
//Intellectual Property and Ownership
//Fairfield Tek L.L.C. shall at all times retain ownership of the Software as originally downloaded by you and all subsequent downloads
// of the Software by you. The Software (and the copyright, and other intellectual property rights of whatever nature in the Software,
// including any modifications made thereto) are and shall remain the property of Fairfield Tek L.L.C..
//Fairfield Tek L.L.C. reserves the right to grant licences to use the Software to third parties.
//Termination
//This EULA agreement is effective from the date you first use the Software and shall continue until terminated. 
//You may terminate it at any time upon written notice to Fairfield Tek L.L.C..
//It will also terminate immediately if you fail to comply with any term of this EULA agreement. Upon such termination,
// the licenses granted by this EULA agreement will immediately terminate and you agree to stop all access and use of the Software.
//The provisions that by their nature continue and survive will survive any termination of this EULA agreement.
//
//Governing Law
//This EULA agreement, and any dispute arising out of or in connection with this EULA agreement, 
//shall be governed by and construed in accordance with the laws of us.
//
//By accepting this EULA, you agree to hold harmless (Blockelot) FairfieldTek in the event that the cloud storage service is discontinued.
//
//Blockelot and it's Cloud Storage is provided "as is", without warranties of any kind.
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
        if (!PluginManager.HasPlayer(player)) {
            player.sendMessage("Select something first!");
            return false;
        }
        return true;
    }

    private boolean CheckClipBoard(Player player) {
        PlayerInfo pi =PluginManager.GetPlayerInfo(player.getUniqueId());
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
          if ("".equals(PluginManager.GetPlayerInfo(player.getUniqueId()).getLastAuth())) {
                player.sendMessage("Please use /b.reg [email] first.");
                return true;
            }
            try {
                if (PluginManager.GetPlayerInfo(player.getUniqueId()).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }
                if (PluginManager.GetPlayerInfo(player.getUniqueId()).ClipSchematic.IsEmpty()) {
                    player.sendMessage("Nothing in clipboard.");
                    PluginManager.GetPlayerInfo(player.getUniqueId()).setIsProcessing(false, "Paste");
                    return true;
                }
                PluginManager.GetPlayerInfo(player.getUniqueId()).setIsProcessing(true, "Paste");

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
                    PluginManager.GetPlayerInfo(player.getUniqueId()).setIsProcessing(false, "Paste");
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

                PluginManager.GetPlayerInfo(player.getUniqueId()).setIsProcessing(false, "Paste");
                ServerUtil.consoleLog(e.getLocalizedMessage());
                ServerUtil.consoleLog(e.getMessage());

            }
        }
        return true;
    }
}
