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
//import net.fairfieldtek.minecraft.worldeditor.serializers.ISerializer;
//import net.fairfieldtek.minecraft.worldeditor.serializers.Serializer;
//import org.bukkit.TreeSpecies;
//import org.bukkit.block.Block;
//
///**
// *
// * @author geev
// */
//public class MatSapling  extends Serializer implements ISerializer {
//    public static boolean isType(Block block) {
//        return block.getState().getData() instanceof org.bukkit.material.Sapling;
//    }
//     
//    private int TreeSpeciesIdx;
//    private int IsInstantGrowable;
//    public MatSapling(Block block, int xOffset, int yOffset, int zOffset, net.fairfieldtek.minecraft.worldeditor.container.SchematicDef schematicDef) {
//        super(block, xOffset, yOffset, zOffset, schematicDef);
//        if (!isType(block)) {
//            throw new RuntimeException("Invalid Type");
//        }
//        org.bukkit.material.Sapling t = (org.bukkit.material.Sapling) block.getState().getData();
//        setTreeSpecies(t.getSpecies());
//        setIsInstantGrowable(t.isInstantGrowable());
//        
//        
//    }
//    
//    public final  boolean getIsInstantGrowable(){
//        return IsInstantGrowable == 1;
//    }
//    
//    public final  void setIsInstantGrowable(boolean flag)
//    {
//        if (flag){
//            IsInstantGrowable = 1;
//        }
//        else
//            IsInstantGrowable = 0;
//    }
//    
//    public final  TreeSpecies getTreeSpecies(){
//        return TreeSpecies.valueOf(getSchematicDef().getBlockSettingsPalette(TreeSpeciesIdx));
//    }
//    
//    public final  void setTreeSpecies(TreeSpecies t){
//        getSchematicDef().addBlockSettingsPalette(t.name());
//    }
//    
//    
//}
