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
import com.Blockelot.worldeditor.container.PlayerInfo;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Help
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            ArrayList<String> lines = new ArrayList<>();
            lines.add(ChatColor.BLUE + "-----------------BLOCKELOT-HELP----------------------");
            lines.add(ChatColor.YELLOW + "/b.about - Shows this about information.");
            lines.add(ChatColor.YELLOW + "/b.help - Shows this help information.");
            lines.add(ChatColor.YELLOW + "/b.bbinv - Shows the players Blockelot Bank Balance.");
            lines.add(ChatColor.YELLOW + "/b.bbwd [Material] [Amount] - Withdrawl blocks from bank.");
            lines.add(ChatColor.YELLOW + "/b.bbdep [Material] [Amount] - Deposit blocks in bank.");
            lines.add(ChatColor.YELLOW + "/b.bbdep all - Deposit all blocks in bank.");
            lines.add(ChatColor.YELLOW + "/b.we.clear - Clears the Selections and Clipboard.");
            lines.add(ChatColor.YELLOW + "/b.we.clearHistory - Clears Undo buffers.");
            lines.add(ChatColor.YELLOW + "/b.we.size - Shows the dimensions of your selection.");
            lines.add(ChatColor.YELLOW + "/b.we.print - Shows your corner selection points");
            lines.add(ChatColor.YELLOW + "/b.we.select - Selects the specified world position your looking at.");
            lines.add(ChatColor.YELLOW + "/b.we.select [X] [Y] [Z] - Selects the specified world positions");
            lines.add(ChatColor.YELLOW + "/b.we.copy - Copies the blocks in your selection into the clipboard.");
            lines.add(ChatColor.YELLOW + "/b.we.del - Sets blocks in your selection to air.");
            lines.add(ChatColor.YELLOW + "/b.we.delete - Sets blocks in your selection to air.");
            lines.add(ChatColor.YELLOW + "/b.we.distr - Gets the block type Distribution of clipboard.");
            lines.add(ChatColor.YELLOW + "/b.we.paste - Pastes your clipboard where you are pointing.");
            lines.add(ChatColor.YELLOW + "/b.we.paste [Rotational Axis XYZ] [Degrees 90 180 270] - Pastes your clipboard rotating accordingly.");
            lines.add(ChatColor.YELLOW + "/b.we.paste [X] [Y] [Z] - Pastes your clipboard at specified location.");
            lines.add(ChatColor.YELLOW + "/b.we.paste [X] [Y] [Z] [Rotational Axis XYZ] [Degrees 90 180 270] - Pastes your clipboard rotating accordingly.");
            lines.add(ChatColor.YELLOW + "/b.we.stripmine - Clears the chunk and puts all items in chests at bottom.");
            lines.add(ChatColor.YELLOW + "/b.we.stripmine [true] - Clears the chunk, put blocks in bank, chests at bottom for non-blocks.");
            lines.add(ChatColor.YELLOW + "/b.we.undo - Undo you last action.");
            lines.add(ChatColor.YELLOW + "/b.reg [Email Address] - Registers your player with Blockelot.");
            lines.add(ChatColor.YELLOW + "/b.auth - Re-Authorizes your player against Blockelot.");
            lines.add(ChatColor.YELLOW + "/b.ls - Show the current contents of the remote directory.");
            lines.add(ChatColor.YELLOW + "/b.cd [Directory] - Changes to directory.");
            lines.add(ChatColor.YELLOW + "/b.rm [Filename or Foldername] - Removes the file or folder from your storage.");
            lines.add(ChatColor.YELLOW + "/b.mk [Foldername] - Creates a new folder.");
            lines.add(ChatColor.YELLOW + "/b.save [Filename] - Saves the clipboard into the cloud, names file.");
            lines.add(ChatColor.YELLOW + "/b.load [Filename] - Loads the contents into your clipboard.");
            PluginManager.GetPlayerInfo(player.getUniqueId()).SendBankMessageHeader(lines, true, false);
        }
        return true;
    }
}
