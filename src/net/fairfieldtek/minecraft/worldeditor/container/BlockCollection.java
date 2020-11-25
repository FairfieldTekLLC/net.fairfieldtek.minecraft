/*
 * Copyright (C) 2020 geev
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
import java.util.HashMap;
import net.fairfieldtek.minecraft.worldeditor.http.SchematicDataDownloadResponse;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.Directional;


/**
 *
 * @author geev
 */
public class BlockCollection {

    private ArrayList<BlockInfo> Blocks = new ArrayList<>();
    private final ArrayList<PaletteEntry> BlockTypePalette = new ArrayList<>();
    private final ArrayList<PaletteEntry> BlockSettingsPalette = new ArrayList<>();
    private String Name;
    
    private int blockTypeCounter = 0;
    private int BlockSettingsPaletteCounter = 0;

    public HashMap<String, Integer> GetBlockMaterialCounts() {
        HashMap<String, Integer> map = new HashMap<>();
        Blocks.stream().map((def) -> GetBlockTypePaletteEntry(def.getBlockTypeIndex()).name()).forEachOrdered((matName) -> {
            if (!map.containsKey(matName)) {
                map.put(matName, 1);
            } else {
                int count = map.get(matName) + 1;
                map.remove(matName);
                map.put(matName, count);
            }
        });
        return map;
    }
    
    
    public void LoadResponse(SchematicDataDownloadResponse response) {
        Blocks.clear();
        BlockTypePalette.clear();
        blockTypeCounter = 0;
        Name = response.getFileName();

        for (BlockInfo def : response.getBlocks()) {
            //def.SchematicOwner = this;
            Blocks.add(def);
        }

        for (PaletteEntry pe : response.getBlockTypePalette()) {
            this.BlockTypePalette.add(pe.Clone());
        }

//        if (response.getColorPalette() != null) {
//            for (PaletteEntry pe : response.getColorPalette()) {
//                this.BlockColorPalette.add(pe.Clone());
//            }
//        }
    }


    public Material GetBlockTypePaletteEntry(int i) {
        for (PaletteEntry ent : BlockTypePalette) {
            if (ent.getId() == i) {
                return Material.getMaterial(ent.getValue());
            }
        }
        return Material.AIR;
    }

    public int Size() {
        return Blocks.size();
    }

    public PaletteEntry[] GetBlockTypePalette() {
        PaletteEntry[] blockTypePalette = new PaletteEntry[BlockTypePalette.size()];
        BlockTypePalette.toArray(blockTypePalette);
        return blockTypePalette;
    }

    public void Clear() {
        Blocks.clear();
        BlockTypePalette.clear();
    }

    public boolean IsEmpty() {
        return Blocks.isEmpty();
    }

    public ArrayList<BlockInfo> getBlocks() {
        return this.Blocks;
    }

    public void setBlocks(ArrayList<BlockInfo> blocks) {
        this.Blocks = blocks;
    }

    public BlockInfo AddBlock(Block sourceBlock, int offsetX, int offsetY, int offsetZ, Player player) {
        Chunk chunk;
        World world = player.getWorld();

        if (!world.isChunkLoaded(chunk = world.getChunkAt(sourceBlock))) {
            world.loadChunk(chunk);
        }

        BlockInfo def = new BlockInfo(sourceBlock,this);
        def.setBlockTypeIndex(AddBlockTypeToPalette(sourceBlock));
        def.setX(sourceBlock.getX() - offsetX);
        def.setY(sourceBlock.getY() - offsetY);
        def.setZ(sourceBlock.getZ() - offsetZ);
        //def.setBlockFaceCode("");        
//        BlockState blockState = sourceBlock.getState();
//        def.setMaterialData(blockState.getRawData());
//        MaterialData sMat = sourceBlock.getState().getData();
//        if (sMat instanceof Bed) {
//            this.AddBlockColorToPalette(((org.bukkit.block.Bed) sourceBlock.getState()).getColor());
//        }
//
//        def.GetColor(sourceBlock);
//
//        if (!def.StairsGetDirectionalCond(sourceBlock)) {
//            if (!def.LadderGetDirectionalCond(sourceBlock)) {
//                if (!def.BedGetDirectionalCond(sourceBlock)) {
//                    if (!def.DoorsGetDirectionalCond(sourceBlock)) {
//                        if (!def.GetDirectionalCond(sourceBlock)) {
//                            //System.out.println("No Condition");
//                        }
//                    }
//                }
//            }
//        }
        Blocks.add(def);
        return def;
    }

    public BlockInfo AddBlock(BlockInfo blockDef) {
        int matIdx = blockDef.getBlockTypeIndex();
        int btid = this.AddBlockTypeToPalette(GetBlockTypePaletteEntry(matIdx));        

        BlockInfo clone = blockDef.Clone(this);

        clone.setBlockCollection(this);
        clone.setBlockTypeIndex(btid);
        this.Blocks.add(clone);
        return clone;
    }

    public int AddBlockTypeToPalette(Block sourceBlock) {
        return AddBlockTypeToPalette(sourceBlock.getType());
    }

    public int AddBlockTypeToPalette(Material mat) {
        for (PaletteEntry ent : BlockTypePalette) {
            if (ent.getValue().equals(mat.name().trim())) {
                return ent.getId();
            }
        }
        int idx = blockTypeCounter;
        BlockTypePalette.add(new PaletteEntry(idx, mat.name()));
        blockTypeCounter++;
        return idx;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public BlockCollection Clone() {
        BlockCollection newSchematicDef = new BlockCollection();
        Blocks.forEach((def) -> {
            newSchematicDef.Blocks.add(def.Clone(this));
        });
        BlockTypePalette.forEach((matType) -> {
            newSchematicDef.BlockTypePalette.add(matType.Clone());
        });

        BlockSettingsPalette.forEach((colorType) -> {
            newSchematicDef.BlockSettingsPalette.add(colorType.Clone());
        });

        return newSchematicDef;
    }
    

    public String getBlockSettingsPalette(int i) {
        for (PaletteEntry ent : BlockSettingsPalette) {
            if (ent.getId() == i) {
                return ent.getValue();
            }
        }
        return "";
    }

    public int addBlockSettingsPalette(String value) {
        for (PaletteEntry ent : BlockSettingsPalette) {
            if (ent.getValue().equals(value.trim())) {
                return ent.getId();
            }
        }
        int idx = BlockSettingsPaletteCounter;
        BlockSettingsPalette.add(new PaletteEntry(idx, value));
        BlockSettingsPaletteCounter++;
        return idx;
    }
}
