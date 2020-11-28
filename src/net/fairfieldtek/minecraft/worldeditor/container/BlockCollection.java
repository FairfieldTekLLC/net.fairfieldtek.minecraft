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
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.fairfieldtek.minecraft.worldeditor.http.SchematicDataDownloadResponse;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

/**
 *
 * @author geev
 */
public class BlockCollection {

    private ArrayList<BlockInfo> Blocks = new ArrayList<>();
    private ArrayList<PaletteEntry> BlockTypePalette = new ArrayList<>();
    private ArrayList<PaletteEntry> BlockDataPalette = new ArrayList<>();
    private String Name;

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
        BlockTypePalette = new ArrayList<>();
        BlockDataPalette = new ArrayList<>();
        Name = response.getFileName();
        for (PaletteEntry pe : response.getBlockTypePalette()) {
            BlockTypePalette.add(pe.Clone());
        }
        for (PaletteEntry pe : response.getBlockDataPalette()) {
            BlockDataPalette.add(pe.Clone());
        }

        for (BlockInfo ent : response.getBlocks()) {
            ent.setBlockCollection(this);
            try {
                Blocks.add(ent);
            } catch (Exception ex) {
                Logger.getLogger(BlockCollection.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

       

    }

    /**
     *
     * @param i
     * @return
     */
    public Material GetBlockTypePaletteEntry(int id) {
        for (PaletteEntry ent : BlockTypePalette) {
            if (ent.getId() == id) {
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

    public PaletteEntry[] GetBlockDataPalette() {
        PaletteEntry[] blockDataPalette = new PaletteEntry[BlockDataPalette.size()];
        BlockDataPalette.toArray(blockDataPalette);
        return blockDataPalette;
    }

    public void Clear() {
        Blocks.clear();
        BlockTypePalette.clear();
        BlockDataPalette.clear();
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

    public BlockInfo AddBlock(Block sourceBlock, int offsetX, int offsetY, int offsetZ, BlockCollection undo) {
        if (undo != null) {
            undo.AddBlock(sourceBlock, offsetX, offsetY, offsetZ, null);
        }

        Chunk chunk = sourceBlock.getChunk();
        World world = chunk.getWorld();
        if (!world.isChunkLoaded(chunk = world.getChunkAt(sourceBlock))) {
            world.loadChunk(chunk);
        }
        BlockInfo def = new BlockInfo(sourceBlock, this);

        def.setX(sourceBlock.getX() + offsetX);
        def.setY(sourceBlock.getY() + offsetY);
        def.setZ(sourceBlock.getZ() + offsetZ);

        Blocks.add(def);
        return def;
    }

    public BlockInfo AddBlock(BlockInfo blockDef, BlockCollection undo) {
        if (undo != null) {
            undo.AddBlock(blockDef, null);
        }
        BlockInfo clone;
        try {

            clone = blockDef.Clone(this);
            this.Blocks.add(clone);
            return clone;

        } catch (Exception ex) {
            Logger.getLogger(BlockCollection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public int AddBlockTypeToPalette(Block sourceBlock) {
        return AddBlockTypeToPalette(sourceBlock.getType());
    }

    public int getMaxPalletId(ArrayList<PaletteEntry> palette) {
        int start = 0;
        for (PaletteEntry ent : palette) {
            if (ent.getId() > start) {
                start = ent.getId();
            }
        }
        return start;
    }

    public int AddBlockTypeToPalette(Material mat) {
        for (PaletteEntry ent : BlockTypePalette) {
            if (ent.getValue().equals(mat.name().trim())) {
                return ent.getId();
            }
        }
        int idx = getMaxPalletId(BlockTypePalette) + 1;
        BlockTypePalette.add(new PaletteEntry(idx, mat.name()));
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

        BlockTypePalette.forEach((matType) -> {
            newSchematicDef.BlockTypePalette.add(matType.Clone());
        });
        BlockDataPalette.forEach((Data) -> {
            newSchematicDef.BlockDataPalette.add(Data.Clone());
        });

        Blocks.forEach((BlockInfo def) -> {
            try {
                newSchematicDef.Blocks.add(def.Clone(newSchematicDef));
            } catch (Exception ex) {
                Logger.getLogger(BlockCollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Validate();
        newSchematicDef.Validate();

        return newSchematicDef;
    }

    public boolean Validate() {
        Blocks.forEach((BlockInfo def) -> {
            if (def.getBlockData() == null) {
                System.out.println("------------------------------------->NULL BLOCk data");
            }
            if (def.getBlockMaterial() == null) {
                System.out.println("------------------------------------->NULL BLOCk Material");
            }
        });

        return true;
    }

    public String getBlockDataPalette(int i) {
        for (PaletteEntry ent : BlockDataPalette) {
            if (ent.getId() == i) {
                return ent.getValue();
            }
        }
        return "";
    }

    public int addBlockDataPalette(String value) {
        if (value == null) {
            value = "";
        }
        for (PaletteEntry ent : BlockDataPalette) {
            if (ent.getValue().equals(value.trim())) {
                return ent.getId();
            }
        }
        int idx = getMaxPalletId(BlockDataPalette) + 1;
        BlockDataPalette.add(new PaletteEntry(idx, value));
        return idx;
    }
}
