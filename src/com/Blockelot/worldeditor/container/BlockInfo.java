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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.Blockelot.Util.EnumHelper;
import static com.Blockelot.Util.Inventory.itemStackArrayToBase64;
import com.Blockelot.Util.MaterialUtil;
import static com.Blockelot.Util.MiscUtil.ByteArrayToInt;
import static com.Blockelot.Util.MiscUtil.intToByteArray;
import com.Blockelot.Util.ServerUtil;
import com.sun.tools.javac.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.block.Block;
import org.bukkit.block.data.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.Material;
import org.bukkit.block.data.Directional;
import org.bukkit.*;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author geev
 */
public final class BlockInfo {

    private int X;
    private int Y;
    private int Z;
    private BlockCollection BlockCollection;
    private int BlockTypeIndex = -1;
    private int BlockDataIndex = -1;
    private int BlockStorageIndex = -1;
    private int BlockContentsIndex = -1;

    public String getBlockDataString() {
        if (BlockDataIndex == -1) {
            ServerUtil.consoleLog("******************************************************>>>> Block Data Index NOT INITIALIZED");
        }
        return BlockCollection.getBlockDataPalette(BlockDataIndex);
    }

    public BlockData getBlockData() {
        if (BlockDataIndex == -1) {
            ServerUtil.consoleLog("******************************************************>>>> Block Data Index NOT INITIALIZED");
        }

        return Bukkit.getServer().createBlockData(BlockCollection.getBlockDataPalette(BlockDataIndex));
    }

    public final void setBlockData(String data) throws Exception {
        if (data.isEmpty()) {
            throw new Exception("DATA IS EMPTY!");
        }
        BlockDataIndex = BlockCollection.addBlockDataPalette(data);
    }

    public BlockCollection getBlockCollection() {
        return this.BlockCollection;
    }

    public void setBlockCollection(BlockCollection bc) {
        this.BlockCollection = bc;
    }

    public BlockInfo Clone(BlockCollection targetCollection) throws Exception {
        BlockInfo blockDef = new BlockInfo(targetCollection);
        blockDef.setBlockCollection(targetCollection);
        blockDef.X = this.X;
        blockDef.Y = this.Y;
        blockDef.Z = this.Z;
        blockDef.setBlockData(this.getBlockData().getAsString());
        blockDef.setBlockMaterial(this.getBlockMaterial());
        blockDef.setInventoryContentsString(this.getInventoryContentsString());
        blockDef.setInventoryStorageString(this.getInventoryStorageString());
        return blockDef;
    }

    private BlockInfo(BlockCollection collection) {
        BlockCollection = collection;
        BlockTypeIndex = -1;
        BlockDataIndex = -1;
        BlockStorageIndex = -1;
        BlockContentsIndex = -1;
    }

    public BlockInfo(Block block, BlockCollection collection) {
        BlockCollection = collection;
        X = block.getX();
        Y = block.getY();
        Z = block.getZ();

        setBlockMaterial(block.getType());
        try {
            setBlockData(block.getBlockData().getAsString());
        } catch (Exception ex) {
            Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        String contentsString = "";
        String storageString = "";

        BlockState state = block.getState();
        if (state instanceof Container) {
            ItemStack[] ContentsElements = ((Container) state).getInventory().getContents();
            ItemStack[] StorageElements = ((Container) state).getInventory().getStorageContents();
            contentsString = itemStackArrayToBase64(ContentsElements);
            storageString = itemStackArrayToBase64(StorageElements);
        }
        this.setInventoryContentsString(contentsString);
        this.setInventoryStorageString(storageString);

    }

    public void EraseLiquid(Block changeBlock, int diameter, BlockCollection undoBuffer) throws Exception {
        if (diameter == 0) {
            return;
        }

        Block s1 = changeBlock;

        if (s1.isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

        s1 = changeBlock.getRelative(BlockFace.UP, 1);
        if (s1.isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.DOWN, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.EAST, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.WEST, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.NORTH, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.SOUTH, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

    }

    public boolean ApplyBlockInfoToBlock(Block target, boolean eraseWater, BlockCollection undoBuffer, PlayerInfo pi) throws Exception {
        return ApplyBlockInfoToBlock(target, eraseWater, undoBuffer, true, pi);
    }

    public boolean ApplyBlockInfoToBlock(Block target, boolean eraseWater, BlockCollection undoBuffer, boolean applyPhysics, PlayerInfo pi) throws Exception {

        if (!target.getChunk().isLoaded()) {
            target.getChunk().load();
        }

        if (undoBuffer != null) {
            undoBuffer.AddBlock(target, 0, 0, 0, null);
        }
        if (eraseWater) {
            EraseLiquid(target, 1, undoBuffer);
        }

        //Inventory handler to prevent bug.
        try {
            BlockState state = target.getState();
            Container chest = (Container) state;
            chest.update(true);
            chest.getInventory().clear();
            target.setType(Material.AIR);
            return false;
        } catch (Exception e) {
        }

        if (this.getBlockData() instanceof Door) {
            final Block bottom = target;
            final Block top = bottom.getRelative(BlockFace.UP, 1);

            Door door = (Door) Bukkit.createBlockData(this.getBlockMaterial());
            door.setHalf(Half.BOTTOM);
            bottom.setBlockData(door, false);
            door.setHalf(Half.TOP);
            top.setBlockData(door, false);

        } else {
            if (PluginManager.Config.NonPastableBlockArray.contains(getBlockMaterial())) {
                pi.getPlayer().sendMessage("The schematic you are pasting contains materials blocked by your server administrator.  The Material was '" + getBlockMaterial().name() + "' and was replaced with 'STONE'.");
                target.setType(Material.STONE, applyPhysics);
            } else {
                target.setType(getBlockMaterial(), applyPhysics);
                target.setBlockData(getBlockData(), applyPhysics);
            }
            target.getState().update();

            if (PluginManager.Config.IncludeInventoryWhenPasting) {
                if (target.getState() instanceof Container) {
                    try {
                        if (!"".equals(this.getInventoryContentsString())) {
                            ((Container) target.getState()).getInventory().setContents(com.Blockelot.Util.Inventory.itemStackArrayFromBase64(this.getInventoryContentsString()));
                        }
                        if (!"".equals(this.getInventoryStorageString())) {
                            ((Container) target.getState()).getInventory().setStorageContents(com.Blockelot.Util.Inventory.itemStackArrayFromBase64(this.getInventoryStorageString()));
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(BlockInfo.class.getName()).log(Level.WARNING, null, ex);
                    }
                }
            }
        }

        return true;
    }

    public Material getBlockMaterial() {
        return BlockCollection.GetBlockTypePaletteEntry(this.BlockTypeIndex);
    }

    public void setBlockMaterial(Material mat) {

        this.BlockTypeIndex = BlockCollection.AddBlockTypeToPalette(mat);
    }

    public void setInventoryContentsString(String inv) {
        this.BlockContentsIndex = BlockCollection.addBlockInventoryPalette(inv);
    }

    public String getInventoryContentsString() {
        return BlockCollection.getBlockInventoryPalette(this.BlockContentsIndex);
    }

    public void setInventoryStorageString(String inv) {
        this.BlockStorageIndex = BlockCollection.addBlockInventoryPalette(inv);
    }

    public String getInventoryStorageString() {
        return BlockCollection.getBlockInventoryPalette(this.BlockStorageIndex);
    }

    public String getBlockFaceCode() {

        BlockData newBlockData = this.getBlockData();

        if (newBlockData instanceof Directional) {
            return EnumHelper.ToCodeFromBlockFace(((Directional) newBlockData).getFacing());
        }
        return "";
    }

    public BlockFace GetBlockFace() {

        BlockData newBlockData = this.getBlockData();
        if (newBlockData instanceof Directional) {
            return ((Directional) newBlockData).getFacing();
        }

        return BlockFace.SELF;

    }

    public void SetBlockFace(BlockFace face) {

        BlockData newBlockData = this.getBlockData();
        if (newBlockData instanceof Directional) {
            try {
                ((Directional) newBlockData).setFacing(face);
            } catch (Exception ex) {
                //Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                setBlockData(newBlockData.getAsString());
            } catch (Exception ex) {
                // Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void SetBlockFaceCode(String code) {

        try {
            BlockData newBlockData = this.getBlockData();
            if (newBlockData instanceof Directional) {
                ((Directional) newBlockData).setFacing(EnumHelper.ToBlockFaceFromCode(code));
                setBlockData(newBlockData.getAsString());
            }
        } catch (Exception ex) {
            Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ClearEntities(Chunk chunk) {
        try {

            //Lets clear any entities int he chunk....
            Entity[] ent = chunk.getEntities();
            for (Entity ent1 : ent) {
                ent1.remove();
            }
        } catch (Exception itm) {
            // empty catch block
        }
    }

    public int getX() {
        return this.X;
    }

    public void setX(int x) {
        this.X = x;
    }

    public int getY() {
        return this.Y;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public int getZ() {
        return this.Z;
    }

    public void setZ(int z) {
        this.Z = z;
    }

    public void GetRotZ(int degrees) {
        BlockFace bf = GetBlockFace();
        String j = EnumHelper.ToCodeFromBlockFace(bf);
        int x = bf.getModX();
        int y = bf.getModY();
        int z = bf.getModZ();
        block0:
        switch (degrees) {
            case 90: {
                switch (j) {
                    case "00+": {
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00-": {
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "0+0": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        if (this.IsStairs()) {
                            x = -1;
                            y = 0;
                            z = 0;
                            break block0;
                        }
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = 0;
                        y = -1;
                        z = 0;
                    }
                }
                break;
            }
            case 180: {
                switch (j) {
                    case "00+": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00-": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = 1;
                        y = 0;
                        z = 0;
                    }
                }
                break;
            }
            case 270: {
                switch (j) {
                    case "00+": {
                        x = 1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "00-": {
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "0+0": {
                        x = 1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
                        x = 0;
                        y = 1;
                        z = 0;
                    }
                }
            }
        }

        this.SetBlockFace(MaterialUtil.getFacingByMod(x, y, z));
    }

    public void GetRotY(int degrees) {
        BlockFace bf = GetBlockFace();//ToBlockFace();
        String j = EnumHelper.ToCodeFromBlockFace(bf);
        int x = bf.getModX();
        int y = bf.getModY();
        int z = bf.getModZ();
        block0:
        switch (degrees) {
            case 90: {
                switch (j) {
                    case "00+": {
                        x = 1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "00-": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "-00": {
                        x = 0;
                        y = 0;
                        z = 1;
                    }
                }
                break;
            }
            case 180: {
                switch (j) {
                    case "00+": {
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "00-": {
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
                        x = 1;
                        y = 0;
                        z = 0;
                    }
                }
                break;
            }
            case 270: {
                switch (j) {
                    case "00+": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "00-": {
                        x = 1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "-00": {
                        x = 0;
                        y = 0;
                        z = -1;
                    }
                }
            }
        }
        this.SetBlockFace(MaterialUtil.getFacingByMod(x, y, z));

    }

    public void GetRotX(int degrees) {
        BlockFace bf = GetBlockFace();
        String j = EnumHelper.ToCodeFromBlockFace(bf);
        int x = bf.getModX();
        int y = bf.getModY();
        int z = bf.getModZ();
        block0:
        switch (degrees) {
            case 90: {
                switch (j) {
                    case "-00": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "00-": {
                        if (this.IsStairs()) {
                            x = 0;
                            y = 0;
                            z = 1;
                            break block0;
                        }
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "00+": {
                        if (this.IsStairs()) {
                            x = 0;
                            y = 0;
                            z = 1;
                            this.Invert();
                            break block0;
                        }
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "+00": {
                        x = 1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                }
                break;
            }
            case 180: {
                switch (j) {
                    case "-00": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "00-": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00+": {
                        if (IsStairs()) {
                            x = 0;
                            y = 0;
                            z = -1;
                            this.Invert();
                            break block0;
                        }
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        if (IsStairs()) {
                            this.Invert();
                        }
                        x = 1;
                        y = 0;
                        z = 0;
                    }
                }
                break;
            }
            case 270: {
                switch (j) {
                    case "-00": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00-": {
                        if (IsStairs()) {
                            x = 0;
                            y = 0;
                            z = -1;
                            this.Invert();
                            break block0;
                        }
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "00+": {
                        if (IsStairs()) {
                            x = 0;
                            y = 0;
                            z = -1;
                            break block0;
                        }
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "+00": {
                        x = 1;
                        y = 0;
                        z = 0;
                    }
                }
            }
        }
        try {
            this.SetBlockFace(MaterialUtil.getFacingByMod(x, y, z));
        } catch (Exception e) {
            //
        }

    }

    public int getBlockTypeIndex() {
        return this.BlockTypeIndex;
    }

    private static PassParam GetInt(byte[] data) {
        int counter = -1;
        byte[] num = new byte[4];

        for (byte b : data) {
            counter = counter + 1;
            num[counter] = b;
            if (counter == 3) {
                int number = ByteArrayToInt(num);
                byte[] dat = new byte[0];
                if (data.length > 4) {
                    dat = Arrays.copyOfRange(data, 4, data.length - 4);
                }

                PassParam ret = new PassParam(number, dat);
                return ret;
            }
        }
        return new PassParam(0, null);
    }

    public static Pair<Integer, char[]> GetInt(char[] feed) {
        String number = "";
        for (int i = 0; i < feed.length; i++) {
            if (feed[i] == '|') {
                int v = Integer.parseInt(number);
                char[] part = new char[0];
                if (feed.length > i + 1) {
                    part = Arrays.copyOfRange(feed, i + 1, feed.length - i + 1);
                }
                return new Pair<>(v, part);
            } else {
                number = number + feed[i];
            }
        }
        return new Pair<>(0, new char[0]);
    }

    public static Pair<BlockInfo, char[]> fromXferString(char[] data) {
        BlockInfo block = new BlockInfo(null);

        Pair<Integer, char[]> dat = GetInt(data);
        block.X = dat.fst;
        dat = GetInt(dat.snd);
        block.Y = (dat.fst);
        dat = GetInt(dat.snd);
        block.Z = (dat.fst);

        dat = GetInt(dat.snd);
        block.BlockTypeIndex = dat.fst;

        dat = GetInt(dat.snd);
        block.BlockDataIndex = dat.fst;

        dat = GetInt(dat.snd);
        block.BlockContentsIndex = dat.fst;

        dat = GetInt(dat.snd);
        block.BlockStorageIndex = dat.fst;

        return new Pair<BlockInfo, char[]>(block, dat.snd);
    }

    public static Pair<BlockInfo, byte[]> fromXferBytes(byte[] data) {

        BlockInfo block = new BlockInfo(null);

        PassParam dat = GetInt(data);
        block.X = dat.Value;

        dat = GetInt(dat.Stream);
        block.Y = (dat.Value);

        dat = GetInt(dat.Stream);
        block.Z = (dat.Value);

        dat = GetInt(dat.Stream);
        block.BlockTypeIndex = dat.Value;

        dat = GetInt(dat.Stream);
        block.BlockDataIndex = dat.Value;

        dat = GetInt(dat.Stream);
        block.BlockContentsIndex = dat.Value;

        dat = GetInt(dat.Stream);
        block.BlockStorageIndex = dat.Value;

        return new Pair<BlockInfo, byte[]>(block, dat.Stream);

    }

    public byte[] toXferBytes() {

        try {

            ArrayList<byte[]> data = new ArrayList<byte[]>();

            data.add(intToByteArray(X));

            data.add(intToByteArray(Y));

            data.add(intToByteArray(Z));

            data.add(intToByteArray(BlockTypeIndex));

            data.add(intToByteArray(BlockDataIndex));

            data.add(intToByteArray(BlockContentsIndex));

            data.add(intToByteArray(BlockStorageIndex));

            byte[] result = new byte[(data.size() * 4) + 1];

            int counter = 0;
            for (byte[] ent : data) {
                for (byte b : ent) {
                    {
                        result[counter] = b;
                        counter++;
                    }
                }
            }
            return result;
        } catch (Exception e) {
            ServerUtil.consoleLog("ERROR");
            ServerUtil.consoleLog(e.getLocalizedMessage());
            ServerUtil.consoleLog(e.getMessage());
            ServerUtil.consoleLog(e);
        }
        return null;

    }

    public String toXferString() {

        return Integer.toString(X) + "|"
                + Integer.toString(Y) + "|"
                + Integer.toString(Z) + "|"
                + Integer.toString(BlockTypeIndex) + "|"
                + Integer.toString(BlockDataIndex) + "|"
                + Integer.toString(BlockContentsIndex) + "|"
                + Integer.toString(BlockStorageIndex) + "|";
    }

    public boolean IsDoor() {
        if (this.getBlockData().getMaterial().name().toLowerCase().endsWith("_door")) {
            return true;
        }

        return false;
    }

    public boolean IsStairs() {
        if (this.getBlockData().getMaterial().name().toLowerCase().endsWith("_STAIRS".toLowerCase())) {
            return true;
        }
        return false;
    }

    public void Invert() {
        BlockData data = this.getBlockData();

        if (data instanceof Bisected) {
            Bisected.Half h = ((Bisected) data).getHalf();
            if (h == Half.BOTTOM) {
                ((Bisected) data).setHalf(Half.TOP);
            } else {
                ((Bisected) data).setHalf(Half.BOTTOM);
            }
        }

        try {
            this.setBlockData(data.getAsString());
        } catch (Exception ex) {
            Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
