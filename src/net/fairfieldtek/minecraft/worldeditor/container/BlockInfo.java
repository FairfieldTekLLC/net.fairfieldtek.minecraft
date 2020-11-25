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

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.Util.EnumHelper;
import net.fairfieldtek.minecraft.Util.MaterialUtil;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.block.data.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.block.data.Directional;
import org.bukkit.*;
import org.bukkit.block.Chest;

/**
 *
 * @author geev
 */
public class BlockInfo {

    private int X;
    private int Y;
    private int Z;
//    private String BlockFaceCode;
    private Material Material;
    //private BlockData BlockData = null;
    private String BlockData;
    private boolean IsWallSign = false;
    private int BlockTypeIndex;
    private BlockCollection BlockCollection;
    private boolean IsDirectional = false;

    public BlockCollection getBlockCollection() {
        return this.BlockCollection;
    }

    public void setBlockCollection(BlockCollection bc) {
        this.BlockCollection = bc;
    }

    public BlockInfo Clone(BlockCollection owner) {
        BlockInfo blockDef = new BlockInfo(this.BlockCollection);
        //blockDef.BlockFaceCode = this.BlockFaceCode;
        blockDef.BlockTypeIndex = this.BlockTypeIndex;
        blockDef.Material = this.Material;
        blockDef.BlockCollection = this.BlockCollection;
        blockDef.BlockData = this.BlockData;

        // blockDef.Inverted = this.Inverted;
        blockDef.setBlockCollection(owner);
        blockDef.X = this.X;
        blockDef.Y = this.Y;
        blockDef.Z = this.Z;
        return blockDef;
    }

    private BlockInfo(BlockCollection collection) {
        BlockCollection = collection;
    }

    public BlockInfo(Block block, BlockCollection collection) {
        X = block.getX();
        Y = block.getY();
        Z = block.getZ();
        Material = block.getType();
        BlockData = block.getBlockData().getAsString();
        BlockCollection = collection;

//        if (block.getBlockData() instanceof Directional) {
//            BlockFaceCode = EnumHelper.ToCodeFromBlockFace(((Directional) block.getBlockData()).getFacing());
//        } else {
//            BlockFaceCode = "";
//        }
    }

    public void EraseLiquid(Block changeBlock, int diameter, Player player, BlockCollection undoBuffer) {
        if (diameter == 0) {
            return;
        }

        Block s1 = changeBlock;

        if (s1.isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

        s1 = changeBlock.getRelative(BlockFace.UP, 1);
        if (s1.isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.DOWN, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.EAST, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.WEST, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.NORTH, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.SOUTH, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

    }

    public void SetBlock(Block block, Player player, boolean eraseWater) {
        // System.out.println("Begin Setting Block.");
//        if (eraseWater) {
//            EraseLiquid(block, 1, player, Initialization.PlayerInfoList.get(player).GetUndo());
//        }
        Block target = player.getWorld().getBlockAt(X, Y, Z);

        if (target instanceof Container) {
            BlockState state = target.getState();
            Container chest = (Container) state;
            chest.update();
            chest.getInventory().clear();
            target.breakNaturally();
            target = player.getWorld().getBlockAt(X, Y, Z);
        }
        Server server = Bukkit.getServer();
        BlockData newBlockData = server.createBlockData(BlockData);
        target.setType(Material, false);
        target.setBlockData(newBlockData, false);

    }

    public Material getBlockMaterial() {
        return BlockCollection.GetBlockTypePaletteEntry(this.BlockTypeIndex);
    }

    public String getBlockFaceCode() {
        Server server = Bukkit.getServer();
        BlockData newBlockData = server.createBlockData(BlockData);
        return EnumHelper.ToCodeFromBlockFace(((Directional) newBlockData).getFacing());
    }

    public BlockFace GetBlockFace() {
        Server server = Bukkit.getServer();
        BlockData newBlockData = server.createBlockData(BlockData);
        return ((Directional) newBlockData).getFacing();
    }

    public void SetBlockFace(BlockFace face) {
        Server server = Bukkit.getServer();
        BlockData newBlockData = server.createBlockData(BlockData);
        ((Directional) newBlockData).setFacing(face);
        BlockData = newBlockData.getAsString();
    }
    
    public void SetBlockFaceCode(String code){
         Server server = Bukkit.getServer();
        BlockData newBlockData = server.createBlockData(BlockData);
        ((Directional) newBlockData).setFacing(EnumHelper.ToBlockFaceFromCode(code));
         BlockData = newBlockData.getAsString();
    }

//    public void setBlockFaceCode(String code) {
//        Server server = Bukkit.getServer();
//        BlockData newBlockData = server.createBlockData(BlockData);
//        BlockFace face =  EnumHelper.ToCodeFromBlockFace(((Directional)newBlockData).getFacing());
//        
//        
//        this.BlockFaceCode = code;
//    }
//    public BlockFace ToBlockFace() {
//        return EnumHelper.ToBlockFaceFromCode(this.BlockFaceCode);
//    }
//    
//    public void FromBlockFace(BlockFace face) {
//        this.BlockFaceCode = EnumHelper.ToCodeFromBlockFace(face);
//    }
//    
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
        BlockFace bf =  GetBlockFace();//ToBlockFace();
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
        BlockFace bf =  GetBlockFace();//ToBlockFace();
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

    public void setBlockTypeIndex(int idx) {
        this.BlockTypeIndex = idx;
    }

    public String toXferString() {
        return  Integer.toString(X) + "|"
                + Integer.toString(Y) + "|"
                + Integer.toString(Z) + "|"
                + Integer.toString(BlockTypeIndex) + "|"
                +   this.BlockData;
    }

}
