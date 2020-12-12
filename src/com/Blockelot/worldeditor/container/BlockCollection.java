//End-User License Agreement (EULA) of Blockelot
//
//This End-User License Agreement ("EULA") is a legal agreement between you and Fairfield Tek L.L.C.. Our EULA was created by EULA Template for Blockelot.
//
//This EULA agreement governs your acquisition and use of our Blockelot software ("Software") directly from Fairfield Tek L.L.C. or indirectly through a Fairfield Tek L.L.C. authorized reseller or distributor (a "Reseller"). Our Privacy Policy was created by the Privacy Policy Generator.
//
//Please read this EULA agreement carefully before completing the installation process and using the Blockelot software. It provides a license to use the Blockelot software and contains warranty information and liability disclaimers.
//
//If you register for a free trial of the Blockelot software, this EULA agreement will also govern that trial. By clicking "accept" or installing and/or using the Blockelot software, you are confirming your acceptance of the Software and agreeing to become bound by the terms of this EULA agreement.
//
//If you are entering into this EULA agreement on behalf of a company or other legal entity, you represent that you have the authority to bind such entity and its affiliates to these terms and conditions. If you do not have such authority or if you do not agree with the terms and conditions of this EULA agreement, do not install or use the Software, and you must not accept this EULA agreement.
//
//This EULA agreement shall apply only to the Software supplied by Fairfield Tek L.L.C. herewith regardless of whether other software is referred to or described herein. The terms also apply to any Fairfield Tek L.L.C. updates, supplements, Internet-based services, and support services for the Software, unless other terms accompany those items on delivery. If so, those terms apply.
//
//License Grant
//Fairfield Tek L.L.C. hereby grants you a personal, non-transferable, non-exclusive licence to use the Blockelot software on your devices 
//in accordance with the terms of this EULA agreement.
//
//You are permitted to load the Blockelot software (for example a PC, laptop, mobile or tablet) under your control. You are responsible
//for ensuring your device meets the minimum requirements of the Blockelot software.
//
//You are not permitted to:
//
//Edit, alter, modify, adapt, translate or otherwise change the whole or any part of the Software nor permit the whole or any part of
//the Software to be combined with or become incorporated in any other software, nor decompile, disassemble or reverse engineer the 
//Software or attempt to do any such things
//
//Reproduce, copy, distribute, resell or otherwise use the Software for any commercial purpose
//Allow any third party to use the Software on behalf of or for the benefit of any third party
//Use the Software in any way which breaches any applicable local, national or international law
//use the Software for any purpose that Fairfield Tek L.L.C. considers is a breach of this EULA agreement
//Intellectual Property and Ownership
//Fairfield Tek L.L.C. shall at all times retain ownership of the Software as originally downloaded by you and all subsequent downloads
// of the Software by you. The Software (and the copyright, and other intellectual property rights of whatever nature in the Software,
// including any modifications made thereto) are and shall remain the property of Fairfield Tek L.L.C..
//Fairfield Tek L.L.C. reserves the right to grant licences to use the Software to third parties.
//Termination
//This EULA agreement is effective from the date you first use the Software and shall continue until terminated. 
//You may terminate it at any time upon written notice to Fairfield Tek L.L.C..
//It will also terminate immediately if you fail to comply with any term of this EULA agreement. Upon such termination,
// the licenses granted by this EULA agreement will immediately terminate and you agree to stop all access and use of the Software.
//The provisions that by their nature continue and survive will survive any termination of this EULA agreement.
//
//Governing Law
//This EULA agreement, and any dispute arising out of or in connection with this EULA agreement, 
//shall be governed by and construed in accordance with the laws of us.
//
//By accepting this EULA, you agree to hold harmless (Blockelot) FairfieldTek in the event that the cloud storage service is discontinued.
//
//Blockelot and it's Cloud Storage is provided "as is", without warranties of any kind.
package com.Blockelot.worldeditor.container;

import com.Blockelot.PluginManager;
import com.Blockelot.Util.ServerUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.Blockelot.worldeditor.http.SchematicDataDownloadResponse;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Door;

/**
 *
 * @author geev
 */
public class BlockCollection {

    private ArrayList<BlockInfo> Blocks = new ArrayList<>();

    public ArrayList<BlockInfo> getBlocks() {
        return this.Blocks;
    }

    public static ArrayList<BlockInfo> BlocksGetForY(ArrayList<BlockInfo> toSort, int y) {
        ArrayList<BlockInfo> Result = new ArrayList<>();
        toSort.stream().filter(pe -> (pe.getY() == y)).forEachOrdered(pe -> {
            Result.add(pe);
        });
        return Result;
    }

    public static ArrayList<BlockInfo> SortYAscending(ArrayList<BlockInfo> toSort) {
        ArrayList<BlockInfo> result = new ArrayList<>();

        int minY = 1000000000;
        int maxY = 0;

        for (BlockInfo pe : toSort) {
            if (pe.getY() <= minY) {
                minY = pe.getY();
            }
            if (pe.getY() >= maxY) {
                maxY = pe.getY();
            }
        }

        for (int i = minY; i <= maxY; i++) {
            ArrayList<BlockInfo> d = BlocksGetForY(toSort, i);
            result.addAll(d);
        }

        return result;
    }

    public ArrayList<BlockInfo> getBlocksOrderYAscending() {
        return SortYAscending(Blocks);
    }

    public void setBlocks(ArrayList<BlockInfo> blocks) {
        this.Blocks = blocks;
    }

    public BlockInfo AddBlock(Block sourceBlock, int offsetX, int offsetY, int offsetZ, BlockCollection undo) throws Exception {

        if (Blocks.size() > PluginManager.Config.MaxClipboardSize) {
            throw new Exception("Schematic size exceeds server max.");
        }

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

//        If it is the top of a door, we don't want to collect it.
//        Since it will be generated auto when we create the bottom of the
//        door.
        if (sourceBlock.getBlockData() instanceof Door) {
            if (((Door) sourceBlock.getBlockData()).getHalf() == Half.TOP) {
                return def;
            }
        }

        Blocks.add(def);
        return def;
    }

    public BlockInfo AddBlock(BlockInfo blockDef, BlockCollection undo) throws Exception {

        if (Blocks.size() > PluginManager.Config.MaxClipboardSize) {
            throw new Exception("Schematic size exceeds server max.");
        }
        if (undo != null) {
            undo.AddBlock(blockDef, null);
        }
        BlockInfo clone;
        try {

            clone = blockDef.Clone(this);

            //If it is the top of a door, we don't want to collect it.
            //Since it will be generated auto when we create the bottom of the
            //door.
            if (clone.getBlockData() instanceof Door) {
                if (((Door) clone.getBlockData()).getHalf() == Half.TOP) {
                    return clone;
                }
            }

            this.Blocks.add(clone);
            return clone;

        } catch (Exception ex) {
            Logger.getLogger(BlockCollection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private ArrayList<PaletteEntry> BlockTypePalette = new ArrayList<>();

    public int AddBlockTypeToPalette(Material mat) {
        for (PaletteEntry ent : BlockTypePalette) {
            if (ent.getValue().equals(mat.name().trim())) {
                return ent.getId();
            }
        }
        int idx = getMaxPalletId(BlockTypePalette) + 1;
        BlockTypePalette.add(new PaletteEntry(idx, mat.name().trim()));
        return idx;
    }

    public int AddBlockTypeToPalette(Block sourceBlock) {
        return AddBlockTypeToPalette(sourceBlock.getType());
    }

    public PaletteEntry[] GetBlockTypePalette() {
        PaletteEntry[] blockTypePalette = new PaletteEntry[BlockTypePalette.size()];
        BlockTypePalette.toArray(blockTypePalette);
        return blockTypePalette;
    }

    public Material GetBlockTypePaletteEntry(int id) {
        for (PaletteEntry ent : BlockTypePalette) {
            if (ent.getId() == id) {
                return Material.getMaterial(ent.getValue());
            }
        }
        return Material.AIR;
    }

    private ArrayList<PaletteEntry> BlockDataPalette = new ArrayList<>();

    public PaletteEntry[] GetBlockDataPalette() {
        PaletteEntry[] blockDataPalette = new PaletteEntry[BlockDataPalette.size()];
        BlockDataPalette.toArray(blockDataPalette);
        return blockDataPalette;
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

    private ArrayList<PaletteEntry> BlockInventoryPalette = new ArrayList<>();

    public PaletteEntry[] GetBlockInventoryPalette() {
        PaletteEntry[] blockInventoryPalette = new PaletteEntry[BlockInventoryPalette.size()];
        BlockInventoryPalette.toArray(blockInventoryPalette);
        return blockInventoryPalette;
    }

    public String getBlockInventoryPalette(int i) {
        for (PaletteEntry ent : BlockInventoryPalette) {
            if (ent.getId() == i) {
                return ent.getValue();
            }
        }
        return "";
    }

    public int addBlockInventoryPalette(String value) {
        if (value == null) {
            value = "";
        }
        for (PaletteEntry ent : BlockInventoryPalette) {
            if (ent.getValue().equals(value.trim())) {
                return ent.getId();
            }
        }
        int idx = getMaxPalletId(BlockInventoryPalette) + 1;
        BlockInventoryPalette.add(new PaletteEntry(idx, value));
        return idx;
    }

    private String Name;

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
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

    public void LoadResponse(SchematicDataDownloadResponse response) throws Exception {

        Blocks.clear();
        BlockTypePalette = new ArrayList<>();
        BlockDataPalette = new ArrayList<>();
        BlockInventoryPalette = new ArrayList<>();

        Name = response.getFileName();

        for (PaletteEntry pe : response.getBlockTypePalette()) {
            BlockTypePalette.add(pe.Clone());
        }

        for (PaletteEntry pe : response.getBlockDataPalette()) {
            BlockDataPalette.add(pe.Clone());
        }

        for (PaletteEntry pe : response.getBlockInvePalette()) {
            BlockInventoryPalette.add(pe.Clone());
        }

        if (response.getBlocks().length > PluginManager.Config.MaxClipboardSize) {
            throw new Exception("Schematic size exceeds server max.");
        }

        for (BlockInfo ent : response.getBlocks()) {
            ent.setBlockCollection(this);
            Blocks.add(ent);
        }

    }

    public int Size() {
        return Blocks.size();
    }

    public void Clear() {
        Blocks.clear();
        BlockTypePalette.clear();
        BlockDataPalette.clear();
    }

    public boolean IsEmpty() {
        return Blocks.isEmpty();
    }

    private int getMaxPalletId(ArrayList<PaletteEntry> palette) {
        int start = 0;
        for (PaletteEntry ent : palette) {
            if (ent.getId() > start) {
                start = ent.getId();
            }
        }
        return start;
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
                ServerUtil.consoleLog("------------------------------------->NULL BLOCk data");
            }
            if (def.getBlockMaterial() == null) {
                ServerUtil.consoleLog("------------------------------------->NULL BLOCk Material");
            }
        });

        return true;
    }

}
