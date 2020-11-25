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
//import java.util.ArrayList;
//import net.fairfieldtek.minecraft.Util.EnumHelper;
//import net.fairfieldtek.minecraft.Util.MaterialUtil;
//import net.fairfieldtek.minecraft.worldeditor.serializers.ISerializer;
//import net.fairfieldtek.minecraft.worldeditor.serializers.PatternSerializer;
//import net.fairfieldtek.minecraft.worldeditor.serializers.Serializer;
//import org.bukkit.DyeColor;
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockFace;
//
///**
// *
// * @author geev
// */
//public class MatBanner extends Serializer implements ISerializer {
//    public static boolean isType(Block block) {
//        return (block.getState().getData() instanceof  org.bukkit.material.Banner);
//    }
//
//    private int DyeColor;
//    private int FacingIdx;
//    ArrayList<PatternSerializer> Patterns = new ArrayList<>();
//    public MatBanner(Block block, int xOffset, int yOffset, int zOffset, net.fairfieldtek.minecraft.worldeditor.containerA.SchematicDef schematicDef) {
//        super(block, xOffset, yOffset, zOffset, schematicDef);
//        if (!isType(block)) {
//            throw new RuntimeException("Invalid Type");
//        }
//        org.bukkit.material.Banner mBanner = (org.bukkit.material.Banner) block.getState().getData();
//        
//        setColor(((org.bukkit.block.Banner) block.getState()).getBaseColor());
//        ((org.bukkit.block.Banner) block.getState()).getPatterns().forEach((pattern) -> {
//            Patterns.add(new PatternSerializer(pattern, schematicDef));
//        });
//        
//        setFacing(mBanner.getFacing());
//        
//    }
//
//    public  final void setFacing(BlockFace facing) {
//        FacingIdx = getSchematicDef().addBlockSettingsPalette(EnumHelper.ToCodeFromBlockFace(facing));
//    }
//
//    public final  BlockFace getFacing() {
//        return EnumHelper.ToBlockFaceFromCode(getSchematicDef().getBlockSettingsPalette(FacingIdx));
//    }
//
//    public  final DyeColor getColor() {
//        return MaterialUtil.getDyeColor(getSchematicDef().getBlockSettingsPalette(DyeColor));
//    }
//    public final  void setColor(DyeColor color) {
//        DyeColor = getSchematicDef().addBlockSettingsPalette(color.name());
//    }
//
//    public PatternSerializer[] getPatterns() {
//        PatternSerializer[] patterns = new PatternSerializer[Patterns.size()];
//        Patterns.toArray(patterns);
//        return patterns;
//    }
//
//    
//
//
//}
