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
//import net.fairfieldtek.minecraft.worldeditor.container.SchematicDef;
//import net.fairfieldtek.minecraft.worldeditor.serializers.*;
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockFace;
//import org.bukkit.material.Door;
//import org.bukkit.material.MaterialData;
//
///**
// * Note, this class does not have a org.bukkit.block.Door object!
// *
// * @author geev
// */
//public class MatDoor extends Serializer implements ISerializer {
//    public static boolean isType(Block block) {
//        return block.getType().name().endsWith("_DOOR");
//    }
//
//    private int FacingIdx;
//    private int HingeIdx;
//    
//    public MatDoor(Block block, int xOffset, int yOffset, int zOffset, SchematicDef schematicDef) {
//        super(block, xOffset, yOffset, zOffset, schematicDef);
//        if (!isType(block)) {
//            throw new RuntimeException("Invalid Type");
//        }
//        
//        MaterialData sMat = block.getState().getData();
//        org.bukkit.material.Door door = (Door) sMat;
//        
//        setFacing(door.getFacing());
//        setHinge(door.getHinge());
//        
//    }
//
//    public final  void setFacing(BlockFace facing) {
//        FacingIdx = getSchematicDef().addBlockSettingsPalette(EnumHelper.ToCodeFromBlockFace(facing));
//    }
//
//    public final  BlockFace getFacing() {
//        return EnumHelper.ToBlockFaceFromCode(getSchematicDef().getBlockSettingsPalette(FacingIdx));
//    }
//
//    public  final boolean getHinge() {
//        return Boolean.parseBoolean(getSchematicDef().getBlockSettingsPalette(HingeIdx));
//    }
//
//    public final  void setHinge(boolean hingeOnRight) {
//        HingeIdx = getSchematicDef().addBlockSettingsPalette(Boolean.toString(hingeOnRight));
//    }
//
//
//}
