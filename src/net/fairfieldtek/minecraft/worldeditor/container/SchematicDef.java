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
package net.fairfieldtek.minecraft.worldeditor.container;

import java.util.ArrayList;
import net.fairfieldtek.minecraft.Util.MaterialUtil;
import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.Bed;
import org.bukkit.material.MaterialData;

/**
 *
 * @author geev
 */
public class SchematicDef {

    private ArrayList<BlockDef> Blocks = new ArrayList<>();
    private String Name;
    private ArrayList<String> BlockTypePalette = new ArrayList<>();
    private ArrayList<String> BlockColorPalette = new ArrayList<>();
    
    
    public DyeColor GetColorPaletteEntry(int i)
    {
        return MaterialUtil.getDyeColor(BlockColorPalette.get(i));
        
    }

    public int Size(){
        return Blocks.size();
    }
    
    public void Clear() {
        Blocks.clear();
        BlockTypePalette.clear();
        BlockColorPalette.clear();
    }

    public boolean IsEmpty(){
        return Blocks.isEmpty();
    }
    
    public ArrayList<String> getBlockColorPalette() {
        return this.BlockColorPalette;
    }

    public void setBlockColorPalette(ArrayList<String> palette) {
        this.BlockColorPalette = palette;
    }

    public ArrayList<BlockDef> getBlocks() {
        return this.Blocks;
    }

    public void setBlocks(ArrayList<BlockDef> blocks) {
        this.Blocks = blocks;
    }

    public BlockDef AddBlock(Block sourceBlock, int offsetX, int offsetY, int offsetZ, Player player) {
        Chunk chunk;
        World world = player.getWorld();
        if (!world.isChunkLoaded(chunk = world.getChunkAt(sourceBlock))) {
            world.loadChunk(chunk);
        }
        BlockDef def = new BlockDef();
        def.setBlockColorIndex(-1);
        
        def.SchematicOwner=this;

        //def.setMaterialType(sourceBlock.getType().name());

        def.setBlockTypeIndex(AddBlockTypeToPalette(sourceBlock));

        def.setX(sourceBlock.getX() - offsetX);
        def.setY(sourceBlock.getY() - offsetY);
        def.setZ(sourceBlock.getZ() - offsetZ);
        def.setBlockFaceCode("");
        BlockState blockState = sourceBlock.getState();
        def.setMaterialData(blockState.getRawData());
        def.setIsStairs(false);

        MaterialData sMat = sourceBlock.getState().getData();
        
        if (sMat instanceof Bed) {
            this.AddBlockColorToPalette(((org.bukkit.block.Bed) sourceBlock.getState()).getColor());
        }

        if (!def.StairsGetDirectionalCond(sourceBlock)) {
            if (!def.LadderGetDirectionalCond(sourceBlock)) {
                if (!def.BedGetDirectionalCond(sourceBlock)) {
                    if (!def.GetDirectionalCond(sourceBlock)) {
                        //System.out.println("No Condition");
                    }
                }
            }
        }
        Blocks.add(def);
        return def;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<String> getBlockTypePalette() {
        return this.BlockTypePalette;
    }

    public void setBlockTypePalette(ArrayList<String> palette) {
        this.BlockTypePalette = palette;
    }

    public int AddBlockTypeToPalette(Block sourceBlock) {
        if (!BlockTypePalette.contains(sourceBlock.getType().name())) {
            BlockTypePalette.add(sourceBlock.getType().name());
        }
        return BlockTypePalette.indexOf(sourceBlock.getType().name());
    }
    
    public int AddBlockTypeToPalette(Material mat) {
        if (!BlockTypePalette.contains(mat.name())) {
            BlockTypePalette.add(mat.name());
        }
        return BlockTypePalette.indexOf(mat.name());
    }

    public int AddBlockColorToPalette(DyeColor color) {
        
        if (!BlockColorPalette.contains(color.name())) {
            BlockColorPalette.add(color.name());
        }

        return BlockColorPalette.indexOf(color.name());
    }

}