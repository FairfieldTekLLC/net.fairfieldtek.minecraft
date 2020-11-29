/*
 * Copyright (C) 2018 geev
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.Blockelot.worldeditor.commands;

import com.Blockelot.PluginManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 *
 * @author geev
 */
public class MatList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission("fft.we.editor") || player.isOp())) {
            if (PluginManager.PlayerInfoList.get(player).getIsProcessing()) {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }

            String match = "";

            if (args.length == 1) {
                match = args[0];
            }

            player.sendMessage(ChatColor.YELLOW + "==========================");
            player.sendMessage(ChatColor.YELLOW + "       Material List");
            player.sendMessage(ChatColor.YELLOW + "  Filter is '" + match + "'");
            player.sendMessage(ChatColor.YELLOW + "==========================");

            System.out.println("Filter is '" + match + "'.");
            for (Material mat : Material.values()) {
                System.out.println(mat.name());
                if (mat.name().toLowerCase().startsWith(match.toLowerCase())) {
                    player.sendMessage(ChatColor.YELLOW + "--->" + mat.name());
                }
            }

        }
        return true;

    }
}
