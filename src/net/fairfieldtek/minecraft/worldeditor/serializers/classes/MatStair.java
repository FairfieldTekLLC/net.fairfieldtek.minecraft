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
//import org.bukkit.Material;
//import org.bukkit.TreeSpecies;
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockFace;
//import org.bukkit.material.Stairs;
//
///**
// *
// * @author geev
// */
//public class MatStair extends Serializer implements ISerializer {
//    public static boolean isType(Block block) {
//        return block.getType().name().endsWith("_STAIRS");
//    }
//    
//    private int FacingIdx;
//    private int Inverted;
//    private int MaterialIdx=-1;
//    private int TreeSpeciesIdx = -1;
//    
//    public MatStair(Block block, int xOffset, int yOffset, int zOffset, net.fairfieldtek.minecraft.worldeditor.container.SchematicDef schematicDef) {
//        super(block, xOffset, yOffset, zOffset, schematicDef);
//        if (!isType(block)) {
//            throw new RuntimeException("Invalid Type");
//        }
//        
//        try {
//            org.bukkit.material.Step step = (org.bukkit.material.Step) block.getState().getData();
//            setMaterial(step.getMaterial());
//        } catch (Exception e) {
//            
//        }
//        
//        try {
//            org.bukkit.material.Wood w = (org.bukkit.material.Wood) block.getState().getData();
//            setTreeSpecies(w.getSpecies());
//        } catch (Exception e) {
//            
//        }
//        
//        Stairs stairs = (Stairs) block.getState().getData();
//        setFacing(stairs.getDescendingDirection());
//        setInverted(stairs.isInverted());
//        
//    }
//    
//    public TreeSpecies getTreeSpecies() {
//        if (TreeSpeciesIdx==-1)
//            return null;
//        return TreeSpecies.valueOf(getSchematicDef().getBlockSettingsPalette(TreeSpeciesIdx));
//    }
//
//    public void setTreeSpecies(TreeSpecies t) {
//        TreeSpeciesIdx = getSchematicDef().addBlockSettingsPalette(t.name());
//    }
//    
//    public final Material getMaterial() {
//        if (MaterialIdx==-1)
//            return null;
//        return Material.valueOf(getSchematicDef().getBlockSettingsPalette(MaterialIdx));
//    }
//    
//    public final void setMaterial(Material m) {
//        MaterialIdx = getSchematicDef().addBlockSettingsPalette(m.name());
//    }
//    
//    public final void setFacing(BlockFace facing) {
//        FacingIdx = getSchematicDef().addBlockSettingsPalette(EnumHelper.ToCodeFromBlockFace(facing));
//    }
//    
//    public final BlockFace getFacing() {
//        return EnumHelper.ToBlockFaceFromCode(getSchematicDef().getBlockSettingsPalette(FacingIdx));
//    }
//    
//    public final boolean getInverted() {
//        return Inverted != 0;
//    }
//    
//    public final void setInverted(boolean flag) {
//        if (flag) {
//            Inverted = 1;
//        } else {
//            Inverted = 0;
//        }
//    }
//    
//}
