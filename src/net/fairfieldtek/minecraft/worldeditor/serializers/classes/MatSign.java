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
//package net.fairfieldtek.minecraft.worldeditor.serializers.classes;
//
//import net.fairfieldtek.minecraft.Util.EnumHelper;
//import net.fairfieldtek.minecraft.worldeditor.serializers.ISerializer;
//import net.fairfieldtek.minecraft.worldeditor.serializers.Serializer;
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockFace;
//
///**
// *
// * @author geev
// */
//public class MatSign extends Serializer implements ISerializer {
//    public static boolean isType(Block block) {
//        return block.getState() instanceof org.bukkit.block.Sign;
//    }
//
//    private int FacingIdx;
//
//    private int IsWallSign;
//
//    private int Line0Idx;
//    private int Line1Idx;
//    private int Line2Idx;
//    private int Line3Idx;
//    public MatSign(Block block, int xOffset, int yOffset, int zOffset, net.fairfieldtek.minecraft.worldeditor.container.SchematicDef schematicDef) {
//        super(block, xOffset, yOffset, zOffset, schematicDef);
//        if (!isType(block)) {
//            throw new RuntimeException("Invalid Type");
//        }
//        
//        org.bukkit.block.Sign t = (org.bukkit.block.Sign) block.getState();
//        org.bukkit.material.Sign tt = (org.bukkit.material.Sign) block.getState().getData();
//        
//        setFacing(tt.getFacing());
//        setIsWallSign(tt.isWallSign());
//        setLine0(t.getLine(0));
//        setLine1(t.getLine(1));
//        setLine2(t.getLine(2));
//        setLine3(t.getLine(3));
//    }
//
//    public final  String getLine0() {
//        return getSchematicDef().getBlockSettingsPalette(Line0Idx);
//    }
//
//    public final  void setLine0(String line) {
//        Line0Idx = getSchematicDef().addBlockSettingsPalette(line);
//    }
//
//    public final  String getLine1() {
//        return getSchematicDef().getBlockSettingsPalette(Line1Idx);
//    }
//
//    public final  void setLine1(String line) {
//        Line1Idx = getSchematicDef().addBlockSettingsPalette(line);
//    }
//    
//    public final  String getLine2() {
//        return getSchematicDef().getBlockSettingsPalette(Line2Idx);
//    }
//
//    public final  void setLine2(String line) {
//        Line2Idx = getSchematicDef().addBlockSettingsPalette(line);
//    }    
//    
//    public final  String getLine3() {
//        return getSchematicDef().getBlockSettingsPalette(Line3Idx);
//    }
//
//    public final  void setLine3(String line) {
//        Line3Idx = getSchematicDef().addBlockSettingsPalette(line);
//    }    
//
//    public final  boolean getIsWallSign() {
//        return IsWallSign == 1;
//    }
//
//    public final  void setIsWallSign(boolean flag) {
//        if (flag) {
//            IsWallSign = 1;
//        } else {
//            IsWallSign = 0;
//        }
//
//    }
//
//    public void setFacing(BlockFace facing) {
//        FacingIdx = getSchematicDef().addBlockSettingsPalette(EnumHelper.ToCodeFromBlockFace(facing));
//    }
//
//    public BlockFace getFacing() {
//        return EnumHelper.ToBlockFaceFromCode(getSchematicDef().getBlockSettingsPalette(FacingIdx));
//    }
//
//
//}
