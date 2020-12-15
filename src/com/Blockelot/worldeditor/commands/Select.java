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
import com.Blockelot.worldeditor.container.IPoint;
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
        if (sender instanceof Player && ((player = (Player) sender).hasPermission(PluginManager.Config.Permission_Select) || (player = (Player) sender).hasPermission(PluginManager.Config.Permission_User) || player.isOp())) {
            if ("".equals(PluginManager.GetPlayerInfo(player.getUniqueId()).getLastAuth())) {
                player.sendMessage("Please use /b.reg [email] first.");
                return true;
            }
            if (PluginManager.GetPlayerInfo(player.getUniqueId()).getIsProcessing()) {
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
            if (PluginManager.GetPlayerInfo(player.getUniqueId()).SelectStart == null) {
                PluginManager.GetPlayerInfo(player.getUniqueId()).SelectStart = point;
                player.sendMessage(ChatColor.YELLOW + "Selected Start Point " + point.toString());
                return true;
            }
            if (PluginManager.GetPlayerInfo(player.getUniqueId()).SelectEnd == null) {
                PluginManager.GetPlayerInfo(player.getUniqueId()).SelectEnd = point;
                player.sendMessage(ChatColor.YELLOW + "Selected End Point " + point.toString());
                return true;
            }
            player.sendMessage(ChatColor.RED + "Positions already set, clear them first!");
            return true;
        }
        return true;
    }
}
