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
//package net.fairfieldtek.minecraft.worldeditor.commands.tasks;
//
//import java.util.ArrayList;
//import java.util.ListIterator;
//import java.util.UUID;
//import net.fairfieldtek.minecraft.Initialization;
//import net.fairfieldtek.minecraft.Util.BlockUtil;
//import net.fairfieldtek.minecraft.Util.PlayerUtils;
//import net.fairfieldtek.minecraft.worldeditor.container.BlockInfo;
//import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
//import net.fairfieldtek.minecraft.worldeditor.enumeration.Axis;
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockFace;
//import org.bukkit.Location;
//import net.fairfieldtek.minecraft.worldeditor.container.IPoint;
//import org.bukkit.entity.Player;
//import org.bukkit.scheduler.BukkitRunnable;
//import net.fairfieldtek.minecraft.worldeditor.container.BlockCollection;
//import org.bukkit.Material;
//
///**
// *
// * @author geev
// */
//public class SetTask extends BukkitRunnable {
//
//    int x;
//    int y;
//    int z;
//
//    int sx;
//    int sy;
//    int sz;
//    int ex;
//    int ey;
//    int ez;
//
//    UUID PlayerId;
//    String MatName;
//
//    Material FromMat;
//    Material ToMat;
//
//    BlockCollection SchematicUndo;
//    BlockCollection EmptySchematic = new BlockCollection();
//    int MatIdx;
//    byte ToMagicNumber;
//    byte FromMagicNumber;
//
//    public SetTask(Player player, IPoint start, IPoint end, Material toMat, byte toMagicNumber, Material fromMat, byte fromMagicNumber) {
//        if (start.X < end.X) {
//            sx = start.X;
//            ex = end.X;
//        } else {
//            sx = end.X;
//            ex = start.X;
//        }
//        if (start.Y < end.Y) {
//            sy = start.Y;
//            ey = end.Y;
//        } else {
//            sy = end.Y;
//            ey = start.Y;
//        }
//
//        if (start.Z < end.Z) {
//            sz = start.Z;
//            ez = end.Z;
//        } else {
//            sz = end.Z;
//            ez = start.Z;
//        }
//
//        ToMagicNumber = toMagicNumber;
//        FromMagicNumber = fromMagicNumber;
//
//        this.PlayerId = player.getUniqueId();
//        this.FromMat = fromMat;
//        this.ToMat = toMat;
//
//        x = sx;
//        y = sy;
//        z = sz;
//        PlayerInfo pi = Initialization.PlayerInfoList.get(player);
//        this.SchematicUndo = pi.NewUndo();
//
//        MatIdx = EmptySchematic.AddBlockTypeToPalette(toMat);
//
//    }
//
//    @Override
//    public void run() {
//
//        Player player = Initialization.Plugin.getServer().getPlayer(this.PlayerId);
//        Initialization.PlayerInfoList.get(player).setIsProcessing(true, "Set");
//        if (ToMat == null) {
//            player.sendMessage("Material is Null.");
//            this.cancel();
//            return;
//
//        }
//
//        try {
//
//            PlayerInfo pi = Initialization.PlayerInfoList.get(player);
//
//            if (player == null) {
//                this.cancel();
//            }
//            int counter = 0;
//            while (y <= ey) {
//                while (x <= ex) {
//                    while (z <= ez) {
//                        counter++;
//                        Block target = player.getWorld().getBlockAt(x, y, z);
//                        SchematicUndo.AddBlock(target, 0, 0, 0, player);
//                        if (FromMat != null) {
//                            if (target.getType() == FromMat) {
//                                boolean ok = true;
//                                if (FromMagicNumber != 0) {
//                                    if (target.getData() != FromMagicNumber) {
//                                        ok = false;
//                                    }
//                                }
//                                if (ok) {
//                                    //System.out.println("Setting Block! " + x + " " + y + " " + z);
//                                    BlockInfo EmptyDef = EmptySchematic.AddBlock(target, 0, 0, 0, player);
//                                    EmptyDef.setBlockTypeIndex(MatIdx);
//                                   // EmptyDef.setMaterialData(ToMagicNumber);
//                                    EmptyDef.setX(x);
//                                    EmptyDef.setY(y);
//                                    EmptyDef.setZ(z);
//                                    EmptyDef.SetBlock(target, player, false);
//                                }
//                            } else {
//                                //System.out.println("Not Setting Block!");
//                            }
//                        } else {
//                            //System.out.println("Setting Block! " + x + " " + y + " " + z);
//                            BlockInfo EmptyDef = EmptySchematic.AddBlock(target, 0, 0, 0, player);
//                            EmptyDef.setBlockTypeIndex(MatIdx);
//                            EmptyDef.setMaterialData(ToMagicNumber);
//                            EmptyDef.setX(x);
//                            EmptyDef.setY(y);
//                            EmptyDef.setZ(z);
//                            EmptyDef.SetBlock(target, player, false);
//                        }
//                        z = z + 1;
//
//                        if (counter > 8000) {
//                            player.sendMessage("Buffering....");
//                            return;
//                        }
//                    }
//                    x = x + 1;
//                    z = sz;
//                }
//                y = y + 1;
//                x = sx;
//            }
//            this.cancel();
//            player.sendMessage("Finished setting blocks!");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getLocalizedMessage());
//            this.cancel();
//        }
//        Initialization.PlayerInfoList.get(player).setIsProcessing(false, "Set");
//    }
//
//}
