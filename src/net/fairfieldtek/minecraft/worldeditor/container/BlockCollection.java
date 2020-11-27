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

        Blocks.addAll(Arrays.asList(response.getBlocks())); //def.SchematicOwner = this;

        for (PaletteEntry pe : response.getBlockTypePalette()) {
            this.BlockTypePalette.add(pe.Clone());
        }

//        if (response.getColorPalette() != null) {
//            for (PaletteEntry pe : response.getColorPalette()) {
//                this.BlockColorPalette.add(pe.Clone());
//            }
//        }
    }

    /**
     *
     * @param i
     * @return
     */
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

        BlockTypePalette.forEach((matType) -> {
            newSchematicDef.BlockTypePalette.add(matType.Clone());
        });
        BlockSettingsPalette.forEach((colorType) -> {
            newSchematicDef.BlockSettingsPalette.add(colorType.Clone());
        });

        Blocks.forEach((BlockInfo def) -> {
            try {
                newSchematicDef.Blocks.add(def.Clone(newSchematicDef));
            } catch (Exception ex) {
                Logger.getLogger(BlockCollection.class.getName()).log(Level.SEVERE, null, ex);
            }
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
