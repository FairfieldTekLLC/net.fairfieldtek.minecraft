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
//import java.util.*;
//import net.fairfieldtek.minecraft.worldeditor.serializers.ISerializer;
//import net.fairfieldtek.minecraft.worldeditor.serializers.Serializer;
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockFace;
//import org.bukkit.material.types.MushroomBlockTexture;
//
///**
// *
// * @author geev
// */
//public class MatMushroom  extends Serializer implements ISerializer {
//    public static boolean isType(Block block) {
//        return block.getState().getData() instanceof org.bukkit.material.Mushroom;
//    }
//    
//    private int BlockTextureIdx;
//    private ArrayList<Integer> PaintedFaces = new ArrayList<>();
//    public MatMushroom(Block block, int xOffset, int yOffset, int zOffset, net.fairfieldtek.minecraft.worldeditor.container.SchematicDef schematicDef) {
//        super(block, xOffset, yOffset, zOffset, schematicDef);
//        
//        if (!isType(block)) {
//            throw new RuntimeException("Invalid Type");
//        }
//        
//        org.bukkit.material.Mushroom mushroom = (org.bukkit.material.Mushroom)block.getState().getData();
//        setBlockTexture(mushroom.getBlockTexture());
//        
//        PaintedFaces.clear();
//        mushroom.getPaintedFaces().forEach((face) -> {
//            AddPaintedFace(face);
//        });
//        
//        
//    }
//    
//    
//    public final  MushroomBlockTexture getBlockTexture(){
//        return MushroomBlockTexture.valueOf(getSchematicDef().getBlockSettingsPalette(BlockTextureIdx));
//    }
//    public  final void setBlockTexture(MushroomBlockTexture texture)
//    {
//        BlockTextureIdx = getSchematicDef().addBlockSettingsPalette(texture.name());
//    }
//    
//    public  final void AddPaintedFace(BlockFace face){
//        PaintedFaces.add(getSchematicDef().addBlockSettingsPalette(face.name()));
//    }
//    
//
//    
//    
//    
//}
