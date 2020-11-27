///*
// * Copyright (C) 2018 geev
// *
// * This program is free software; you can redistribute it and/or
// * modify it under the terms of the GNU General Public License
// * as published by the Free Software Foundation; either version 2
// * of the License, or (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program; if not, write to the Free Software
// * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
// */
//package net.fairfieldtek.minecraft.worldeditor.commands;
//
//import net.fairfieldtek.minecraft.Initialization;
//import net.fairfieldtek.minecraft.Util.PlayerUtils;
//import net.fairfieldtek.minecraft.worldeditor.container.BlockInfo;
//import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
//import org.bukkit.block.Block;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.bukkit.ChatColor;
//import java.util.*;
//import net.fairfieldtek.minecraft.worldeditor.container.BlockCollection;
//
///**
// *
// * @author geev
// */
//public class EraseLiquid implements CommandExecutor {
//
//    @Override
//    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        Player player;
//        if (sender instanceof Player && ((player = (Player) sender).hasPermission("fft.we.editor")
//                || player.isOp()) && Initialization.PlayerInfoList.containsKey(player)) {
//            PlayerInfo info = Initialization.PlayerInfoList.get(player);
//            if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
//                player.sendMessage("Please wait for last command to finish.");
//                return true;
//            }
//            if (args.length == 0) {
//                player.sendMessage(ChatColor.YELLOW + "Usage: /EraseLiquid X Y Z (Distance Out)");
//                player.sendMessage(ChatColor.YELLOW + "Usage: /EraseLiquid (Distance Out)");
//                return true;
//            } else if (args.length == 1) {
//                try {
//                    Block target = PlayerUtils.getTargetBlock(player, 10);
//                    int dist = Integer.parseInt(args[0]);
//                    BlockCollection undo = info.NewUndo();
//                    
//                    ((BlockInfo)target).EraseLiquid(target, dist, player,undo);
//                    
////                    BlockInfo.EraseLiquid(target, dist, player,undo);
//                    player.sendMessage("Water/Lava removed.");
//                } catch (Exception e) {
//                    player.sendMessage(ChatColor.YELLOW + "Usage: /EraseLiquid X Y Z (Distance Out)");
//                    player.sendMessage(ChatColor.YELLOW + "Usage: /EraseLiquid (Distance Out)");
//                    return true;
//                }
//            } else if (args.length == 4) {
//                try {
//                    int x = Integer.parseInt(args[0]);
//                    int y = Integer.parseInt(args[1]);
//                    int z = Integer.parseInt(args[2]);
//                    int dist = Integer.parseInt(args[3]);
//                    Block target = player.getWorld().getBlockAt(x, y, z);
//                     BlockCollection undo = info.NewUndo();
//                     ((BlockInfo)target).EraseLiquid(target, dist, player,undo);
////                    BlockDef.EraseLiquid(target, dist, player,undo);
//                    player.sendMessage("Water/Lava removed.");
//                    return true;
//                } catch (Exception e) {
//                    player.sendMessage(ChatColor.YELLOW + "Usage: /EraseLiquid X Y Z (Distance Out)");
//                    player.sendMessage(ChatColor.YELLOW + "Usage: /EraseLiquid (Distance Out)");
//                }
//            }
//
//        }
//        return true;
//    }
//}
