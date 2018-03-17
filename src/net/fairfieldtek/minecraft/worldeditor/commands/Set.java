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
package net.fairfieldtek.minecraft.worldeditor.commands;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.SetTask;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Material;

/**
 *
 * @author geev
 */
public class Set implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player && ((player = (Player) sender).hasPermission("fft.we.editor") || player.isOp())) {
            if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
                player.sendMessage("Please wait for last command to finish.");
                return true;
            }

            PlayerInfo pi = Initialization.PlayerInfoList.get(player);
            if (pi.SelectStart == null || pi.SelectEnd == null) {
                player.sendMessage(ChatColor.YELLOW + "Please select something first!");
                return true;
            }
            Material toMat = null;
            byte toMagicNumber = 0;
            byte fromMagicNumber = 0;
            Material fromMat = null;
            switch (args.length) {
                case 0:
                    player.sendMessage(ChatColor.YELLOW + "Usage: /fft.we.set <To Material Name>");
                    player.sendMessage(ChatColor.YELLOW + "Usage: /fft.we.set <To Material Name> <Magic#>");
                    player.sendMessage(ChatColor.YELLOW + "Usage: /fft.we.set <To Material Name> <From Material>");
                    player.sendMessage(ChatColor.YELLOW + "Usage: /fft.we.set <To Material Name> <Magic#> <From Material>");
                    player.sendMessage(ChatColor.YELLOW + "Usage: /fft.we.set <To Material Name> <Magic#> <From Material> <Magic#>");
                    player.sendMessage(ChatColor.YELLOW + "To see a list of materials use command /fft.we.matlist");
                    return true;
                case 1:

                    for (Material mat : Material.values()) {
                        if (mat.name().toLowerCase().trim().equals(args[0].toLowerCase().trim())) {
                            toMat = mat;
                            break;
                        }
                    }
                    if (toMat == null) {
                        player.sendMessage("Invalid Material Name (To).");
                        return true;
                    }
                    break;
                case 2:
                    for (Material mat : Material.values()) {
                        if (mat.name().toLowerCase().trim().equals(args[0].toLowerCase().trim())) {
                            System.out.println("Found Material!");
                            toMat = mat;
                            break;
                        }
                    }
                    if (toMat == null) {
                        player.sendMessage("Invalid Material Name (To).");
                        return true;
                    }

                    try {
                        toMagicNumber = Byte.parseByte(args[1]);
                    } catch (Exception e) {
                        //If this failed then it must be the next material.
                        toMagicNumber = 0;
                        for (Material mat : Material.values()) {
                            if (mat.name().toLowerCase().trim().equals(args[1].toLowerCase().trim())) {
                                fromMat = mat;
                                break;
                            }
                        }

                        if (fromMat == null) {
                            player.sendMessage("Invalid Material Name (From).");
                            return true;
                        }
                    }
                    break;
                case 3:
                    for (Material mat : Material.values()) {
                        if (mat.name().toLowerCase().trim().equals(args[0].toLowerCase().trim())) {
                            System.out.println("Found Material!");
                            toMat = mat;
                            break;
                        }
                    }
                    if (toMat == null) {
                        player.sendMessage("Invalid Material Name (To).");
                        return true;
                    }
                    try {
                        toMagicNumber = Byte.parseByte(args[1]);
                    } catch (Exception e) {
                        player.sendMessage("Invalid <To Magic Number>");
                        return true;
                    }

                    for (Material mat : Material.values()) {
                        if (mat.name().toLowerCase().trim().equals(args[2].toLowerCase().trim())) {
                            fromMat = mat;
                            break;
                        }
                    }
                    if (fromMat == null) {
                        player.sendMessage("Invalid Material Name (From).");
                        return true;
                    }
                    break;
                case 4:
                    for (Material mat : Material.values()) {
                        if (mat.name().toLowerCase().trim().equals(args[0].toLowerCase().trim())) {
                            System.out.println("Found Material!");
                            toMat = mat;
                            break;
                        }
                    }
                    if (toMat == null) {
                        player.sendMessage("Invalid Material Name (To).");
                        return true;
                    }
                    try {
                        toMagicNumber = Byte.parseByte(args[1]);
                    } catch (Exception e) {
                        player.sendMessage("Invalid <To Magic Number>");
                        return true;
                    }

                    for (Material mat : Material.values()) {
                        if (mat.name().toLowerCase().trim().equals(args[2].toLowerCase().trim())) {
                            fromMat = mat;
                            break;
                        }
                    }
                    if (fromMat == null) {
                        player.sendMessage("Invalid Material Name (From).");
                        return true;
                    }
                    try {
                        fromMagicNumber = Byte.parseByte(args[3]);
                    } catch (Exception e) {
                        player.sendMessage("Invalid <From Magic Number>");
                        return true;
                    }
            }

            SetTask pt = new SetTask(player, pi.SelectStart, pi.SelectEnd, toMat, toMagicNumber, fromMat, fromMagicNumber);
            pt.runTaskTimer((org.bukkit.plugin.Plugin) Initialization.Plugin, 1, 15);
        }
        return true;
    }
}
