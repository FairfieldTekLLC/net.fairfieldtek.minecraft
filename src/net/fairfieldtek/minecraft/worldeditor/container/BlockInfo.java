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

import java.util.logging.Level;
import java.util.logging.Logger;
import net.fairfieldtek.minecraft.Util.EnumHelper;
import net.fairfieldtek.minecraft.Util.MaterialUtil;
import org.bukkit.block.Block;
import org.bukkit.block.data.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.Material;
import org.bukkit.block.data.Directional;
import org.bukkit.*;
import org.bukkit.entity.Entity;

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

    public String getBlockDataString() {
        if (BlockDataIndex == -1) {
            System.out.println("******************************************************>>>> Block Data Index NOT INITIALIZED");
        }
        return BlockCollection.getBlockDataPalette(BlockDataIndex);
    }

    public BlockData getBlockData() {
        if (BlockDataIndex == -1) {
            System.out.println("******************************************************>>>> Block Data Index NOT INITIALIZED");
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
        return blockDef;
    }

    private BlockInfo(BlockCollection collection) {
        BlockCollection = collection;
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

    }

    public void EraseLiquid(Block changeBlock, int diameter, BlockCollection undoBuffer) {
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

    public boolean ApplyBlockInfoToBlock(Block target, boolean eraseWater, BlockCollection undoBuffer) {

        if (undoBuffer != null) {
            undoBuffer.AddBlock(target, 0, 0, 0, null);
        }
        if (eraseWater) {
            EraseLiquid(target, 1, undoBuffer);
        }
        try {
            BlockState state = target.getState();
            Container chest = (Container) state;
            chest.update(true);
            chest.getInventory().clear();
            target.setType(Material.AIR);
//            //target.breakNaturally();
            System.out.println("<>-------------------------------------------<>CHEST");
//            //ClearEntities(target.getChunk());
            return false;
        } catch (Exception e) {
        }

        target.setType(getBlockMaterial());
        target.setBlockData(getBlockData());
        return true;
    }

    public Material getBlockMaterial() {
        return BlockCollection.GetBlockTypePaletteEntry(this.BlockTypeIndex);
    }

    public void setBlockMaterial(Material mat) {

        this.BlockTypeIndex = BlockCollection.AddBlockTypeToPalette(mat);
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

            ((Directional) newBlockData).setFacing(face);
            try {
                setBlockData(newBlockData.getAsString());
            } catch (Exception ex) {
                Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, ex);
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
//                        if (getIsStairs()) {
//                            x = -1;
//                            y = 0;
//                            z = 0;
//                            break block0;
//                        }
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
//                        if (getIsStairs()) {
//                            setInverted(!getInverted());
//                        }
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
//                        if (getIsStairs()) {
//                            setInverted(!getInverted());
//                        }
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00-": {
//                        if (getIsStairs()) {
//                            setInverted(!getInverted());
//                        }
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
//                        if (getIsStairs()) {
//                            setInverted(!getInverted());
//                        }
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
//                        if (getIsStairs()) {
//                            setInverted(!getInverted());
//                        }
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
//                        if (getIsStairs()) {
//                            setInverted(!getInverted());
//                        }
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
//                        if (getIsStairs()) {
//                            x = 1;
//                            y = 0;
//                            z = 0;
//                            break block0;
//                        }
                        x = 0;
                        y = 1;
                        z = 0;
                    }
                }
            }
        }

        this.SetBlockFace(MaterialUtil.getFacingByMod(x, y, z));

        //FromBlockFace(MaterialUtil.getFacingByMod(x, y, z));
        //return itm;
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
        //return itm;
    }

    public void GetRotX(int degrees) {
        BlockFace bf = GetBlockFace();//ToBlockFace();
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
//                        if (getIsStairs()) {
//                            x = 0;
//                            y = 0;
//                            z = 1;
//                            break block0;
//                        }
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "00+": {
//                        if (getIsStairs()) {
//                            x = 0;
//                            y = 0;
//                            z = 1;
//                            setInverted(!getInverted());
//                            break block0;
//                        }
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
                    }
                }
                break;
            }
            case 180: {
                switch (j) {
                    case "-00": {
//                        if (getIsStairs()) {
//                            setInverted(!getInverted());
//                        }
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
//                        if (getIsStairs()) {
//                            setInverted(!getInverted());
//                        }
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00+": {
//                        if (getIsStairs()) {
//                            x = 0;
//                            y = 0;
//                            z = -1;
//                            setInverted(!getInverted());
//                        }
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
//                        if (getIsStairs()) {
//                            setInverted(!getInverted());
//                        }
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
//                        if (getIsStairs()) {
//                            x = 0;
//                            y = 0;
//                            z = -1;
//                            setInverted(!getInverted());
//                            break block0;
//                        }
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "00+": {
//                        if (getIsStairs()) {
//                            x = 0;
//                            y = 0;
//                            z = -1;
//                            break block0;
//                        }
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
        this.SetBlockFace(MaterialUtil.getFacingByMod(x, y, z));
        //return itm;
    }

    public boolean GetInverted(Block block) {
//        if (Material.name().endsWith("_BED")) {
//            //Can't be inverted
//            return false;
//        } else if (Material.name().equalsIgnoreCase("BEEHIVE")) {
//            return false;
//        } else if (Material.name().equalsIgnoreCase("BELL")) {
//            return false;
//        } else if (Material.name().equalsIgnoreCase("CAMPFIRE")) {
//            return false;
//        } else if (Material.name().equalsIgnoreCase("CHEST")) {
//            return false;
//        } else if (Material.name().equalsIgnoreCase("COCOA")) {
//            return false;
//        } else if (Material.name().equalsIgnoreCase("COMMAND_BLOCK")) {
//            return false;
//        } else if (Material.name().endsWith("_CORAL_WALL_FAN")) {
//            return false;
//        } else if (Material.name().equalsIgnoreCase("DISPENSER")) {
//            
//        } else if (Material.name().endsWith("_DOOR")) {
//        } else if (Material.name().equalsIgnoreCase("ENDER_CHEST")) {
//        } else if (Material.name().equalsIgnoreCase("END_PORTAL_FRAME")) {
//        } else if (Material.name().endsWith("_FENCE_GATE")) {
//        } else if (Material.name().equalsIgnoreCase("GRINDSTONE")) {
//        } else if (Material.name().equalsIgnoreCase("HOPPER")) {
//        } else if (Material.name().equalsIgnoreCase("LADDER")) {
//        } else if (Material.name().equalsIgnoreCase("LECTERN")) {
//        } else if (Material.name().equalsIgnoreCase("OBSERVER")) {
//        } else if (Material.name().equalsIgnoreCase("PISTON")) {
//        } else if (Material.name().equalsIgnoreCase("PISTON_HEAD")) {
//        } else if (Material.name().equalsIgnoreCase("REDSTONE_WALL_TORCH")) {
//        } else if (Material.name().equalsIgnoreCase("REPEATER")) {
//        } else if (Material.name().endsWith("_STAIRS")) {
//           Bisected.Half half = ((org.bukkit.block.data.type.Stairs)block.getState().getBlockData()).getHalf();
//        } else if (Material.name().equalsIgnoreCase("LEVER")) {
//        } else if (Material.name().endsWith("_TRAPDOOR")) {
//        } else if (Material.name().equalsIgnoreCase("TRIPWIRE_HOOK")) {
//        } else if (Material.name().endsWith("_SIGN")) {
//        }

        return false;

    }

    public int getBlockTypeIndex() {
        return this.BlockTypeIndex;
    }

    public String toXferString() {
        return Integer.toString(X) + "|"
                + Integer.toString(Y) + "|"
                + Integer.toString(Z) + "|"
                + Integer.toString(BlockTypeIndex) + "|"
                + Integer.toString(BlockDataIndex);
    }

}
