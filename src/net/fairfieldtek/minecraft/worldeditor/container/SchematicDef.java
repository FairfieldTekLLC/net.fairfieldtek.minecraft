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

import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import net.fairfieldtek.minecraft.Util.MaterialUtil;
import net.fairfieldtek.minecraft.worldeditor.http.SchematicDataDownloadResponse;
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
    private final ArrayList<PaletteEntry> BlockTypePalette = new ArrayList<>();
    private final ArrayList<PaletteEntry> BlockColorPalette = new ArrayList<>();
    private int blockTypeCounter = 0;
    private int colorTypeCounter = 0;

    public SchematicDef() {
        //BlockColorPalette.add(new PaletteEntry(0, ""));
    }

    public SchematicDef Clone() {
        SchematicDef newSchematicDef = new SchematicDef();
        Blocks.forEach((def) -> {
            newSchematicDef.Blocks.add(def.Clone(this));
        });
        BlockTypePalette.forEach((matType) -> {
            newSchematicDef.BlockTypePalette.add(matType.Clone());
        });
        BlockColorPalette.forEach((colorType) -> {
            newSchematicDef.BlockColorPalette.add(colorType.Clone());
        });
        return newSchematicDef;
    }

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
        BlockColorPalette.clear();
        blockTypeCounter = 0;
        colorTypeCounter = 0;

        Name = response.getFileName();

        for (BlockDef def : response.getBlocks()) {
            def.SchematicOwner = this;
            Blocks.add(def);
        }

//        System.out.println("Dumping Block Types:");
        for (PaletteEntry pe : response.getBlockTypePalette()) {
//            System.out.println(pe.getId() + " = " + pe.getValue());
            this.BlockTypePalette.add(pe.Clone());
        }

        if (response.getColorPalette() != null) {
//        System.out.println("Dumping Color Types:");
            for (PaletteEntry pe : response.getColorPalette()) {
//            System.out.println(pe.getId() + " = " + pe.getValue());
                this.BlockColorPalette.add(pe.Clone());
            }
        }
    }

    public int GetColorPaletteEntrySize() {
        return BlockColorPalette.size();
    }

    public int GetBlockTypePaletteEntrySize() {
        return BlockTypePalette.size();
    }

    public PaletteEntry[] GetColorPalette() {
        PaletteEntry[] blockSolorPalette = new PaletteEntry[BlockColorPalette.size()];
        BlockColorPalette.toArray(blockSolorPalette);
        return blockSolorPalette;
    }

    public PaletteEntry[] GetBlockTypePalette() {
        PaletteEntry[] blockTypePalette = new PaletteEntry[BlockTypePalette.size()];
        BlockTypePalette.toArray(blockTypePalette);
        return blockTypePalette;
    }

    public DyeColor GetColorPaletteEntry(int i) {
        for (PaletteEntry ent : BlockColorPalette) {
            if (ent.getId() == i) {
//                System.out.println("Found Entry: " + ent.getId() + " - " + ent.getValue());
                return MaterialUtil.getDyeColor(ent.getValue());
            }
        }
//        System.out.println("Color: No Entry for " + i);
        return DyeColor.WHITE;
    }

    public Material GetBlockTypePaletteEntry(int i) {
        for (PaletteEntry ent : BlockTypePalette) {
            if (ent.getId() == i) {
//                System.out.println("Found Entry: " + ent.getId() + " - " + ent.getValue());
                return Material.getMaterial(ent.getValue());
            }
        }
//        System.out.println("Material: No Entry for " + i);
        return Material.AIR;
    }

    public int Size() {
        return Blocks.size();
    }

    public void Clear() {
        Blocks.clear();
        BlockTypePalette.clear();
        BlockColorPalette.clear();
    }

    public boolean IsEmpty() {
        return Blocks.isEmpty();
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
        def.setBlockColorIndex(0);

        def.SchematicOwner = this;

        //def.setMaterialType(sourceBlock.getType().name());
        def.setBlockTypeIndex(AddBlockTypeToPalette(sourceBlock));

        def.setX(sourceBlock.getX() - offsetX);
        def.setY(sourceBlock.getY() - offsetY);
        def.setZ(sourceBlock.getZ() - offsetZ);
        def.setBlockFaceCode("");
        BlockState blockState = sourceBlock.getState();
        def.setMaterialData(blockState.getRawData());

        MaterialData sMat = sourceBlock.getState().getData();

        if (sMat instanceof Bed) {
            this.AddBlockColorToPalette(((org.bukkit.block.Bed) sourceBlock.getState()).getColor());
        }

        def.GetColor(sourceBlock);

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

    public BlockDef AddBlock(BlockDef blockDef) {
        int matIdx = blockDef.getBlockTypeIndex();
        int colIdx = blockDef.getBlockColorIndex();

        int btid = this.AddBlockTypeToPalette(blockDef.SchematicOwner.GetBlockTypePaletteEntry(matIdx));
        int cid = this.AddBlockColorToPalette(blockDef.SchematicOwner.GetColorPaletteEntry(colIdx));

        BlockDef clone = blockDef.Clone(this);

        clone.SchematicOwner = this;
        clone.setBlockTypeIndex(btid);
        clone.setBlockColorIndex(cid);
        this.Blocks.add(clone);
        return clone;

    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int AddBlockTypeToPalette(Block sourceBlock) {
        return AddBlockTypeToPalette(sourceBlock.getType());
    }

    public int AddBlockTypeToPalette(Material mat) {
//        System.out.println("Looking for: '" + mat.name() + "'");
        for (PaletteEntry ent : BlockTypePalette) {
//            System.out.println("Found '" + ent.getValue() + "'");
            if (ent.getValue().equals(mat.name().trim())) {
//                System.out.println("Match: " + ent.getId() + " - '" + ent.getValue() + "'");
                return ent.getId();
            }
        }
//        System.out.println("Didn't find '" + mat.name() + "'");
        int idx = blockTypeCounter;
//        System.out.println("Creating: " + idx + " '" + mat.name() + "'");
        BlockTypePalette.add(new PaletteEntry(idx, mat.name()));
        blockTypeCounter++;
        return idx;
    }

    public int AddBlockColorToPalette(DyeColor color) {
//        System.out.println("Looking for: '" + color.name() + "'");
        for (PaletteEntry ent : BlockColorPalette) {
//            System.out.println("Found '" + ent.getValue() + "'");
            if (ent.getValue().equals(color.name().trim())) {
//                System.out.println("Match: " + ent.getId() + " - " + ent.getValue());
                return ent.getId();
            }
        }
//        System.out.println("Didn't find '" + color.name() + "'");
        int idx = colorTypeCounter;
//        System.out.println("Creating: " + idx + " '" + color.name() + "'");
        BlockColorPalette.add(new PaletteEntry(idx, color.name()));
        colorTypeCounter++;
        return idx;

    }

}
