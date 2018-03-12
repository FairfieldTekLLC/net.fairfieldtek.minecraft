package net.fairfieldtek.minecraft.Util;

import net.fairfieldtek.minecraft.worldeditor.container.BlockDef;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.Directional;
import org.bukkit.material.Ladder;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Stairs;

public class BlockUtil {

    public static BlockFace ToBlockFaceFromCode(String code) {
        if (code.length() != 3) {
            return BlockFace.SELF;
        }
        char[] let = code.toCharArray();
        int x = 0;
        int y = 0;
        int z = 0;
        switch (let[0]) {
            case '-': {
                x = -1;
                break;
            }
            case '+': {
                x = 1;
                break;
            }
            case '0': {
                x = 0;
            }
        }
        switch (let[1]) {
            case '-': {
                y = -1;
                break;
            }
            case '+': {
                y = 1;
                break;
            }
            case '0': {
                y = 0;
            }
        }
        switch (let[2]) {
            case '-': {
                z = -1;
                break;
            }
            case '+': {
                z = 1;
                break;
            }
            case '0': {
                z = 0;
            }
        }
        return MaterialUtil.getFacingByMod(x, y, z);
    }

    public static String ToCodeFromBlockFace(BlockFace bf) {
        int x = bf.getModX();
        int y = bf.getModY();
        int z = bf.getModZ();
        String xc = "*";
        String yc = "*";
        String zc = "*";
        switch (x) {
            case 0: {
                xc = "0";
                break;
            }
            case 1: {
                xc = "+";
                break;
            }
            case -1: {
                xc = "-";
                break;
            }
        }
        switch (y) {
            case 0: {
                yc = "0";
                break;
            }
            case 1: {
                yc = "+";
                break;
            }
            case -1: {
                yc = "-";
                break;
            }
        }
        switch (z) {
            case 0: {
                zc = "0";
                break;
            }
            case 1: {
                zc = "+";
                break;
            }
            case -1: {
                zc = "-";
                break;
            }
        }
        return xc + yc + zc;
    }

    public static BlockDef GetRotZ(BlockDef itm, int degrees) {
        BlockFace bf = itm.ToBlockFace();
        String j = BlockUtil.ToCodeFromBlockFace(bf);
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
                        if (itm.getIsStairs()) {
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
                        if (itm.getIsStairs()) {
                            itm.setInverted(!itm.getInverted());
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
                        if (itm.getIsStairs()) {
                            itm.setInverted(!itm.getInverted());
                        }
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00-": {
                        if (itm.getIsStairs()) {
                            itm.setInverted(!itm.getInverted());
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
                        if (itm.getIsStairs()) {
                            itm.setInverted(!itm.getInverted());
                        }
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
                        if (itm.getIsStairs()) {
                            itm.setInverted(!itm.getInverted());
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
                        if (itm.getIsStairs()) {
                            itm.setInverted(!itm.getInverted());
                        }
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
                        if (itm.getIsStairs()) {
                            x = 1;
                            y = 0;
                            z = 0;
                            break block0;
                        }
                        x = 0;
                        y = 1;
                        z = 0;
                    }
                }
            }
        }
        itm.FromBlockFace(MaterialUtil.getFacingByMod(x, y, z));
        return itm;
    }

    public static BlockDef GetRotY(BlockDef itm, int degrees) {
        BlockFace bf = itm.ToBlockFace();
        String j = BlockUtil.ToCodeFromBlockFace(bf);
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
        itm.FromBlockFace(MaterialUtil.getFacingByMod(x, y, z));
        return itm;
    }

    public static BlockDef GetRotX(BlockDef itm, int degrees) {
        BlockFace bf = itm.ToBlockFace();
        String j = BlockUtil.ToCodeFromBlockFace(bf);
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
                        if (itm.getIsStairs()) {
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
                        if (itm.getIsStairs()) {
                            x = 0;
                            y = 0;
                            z = 1;
                            itm.setInverted(!itm.getInverted());
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
                    }
                }
                break;
            }
            case 180: {
                switch (j) {
                    case "-00": {
                        if (itm.getIsStairs()) {
                            itm.setInverted(!itm.getInverted());
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
                        if (itm.getIsStairs()) {
                            itm.setInverted(!itm.getInverted());
                        }
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00+": {
                        if (itm.getIsStairs()) {
                            x = 0;
                            y = 0;
                            z = -1;
                            itm.setInverted(!itm.getInverted());
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
                        if (itm.getIsStairs()) {
                            itm.setInverted(!itm.getInverted());
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
                        if (itm.getIsStairs()) {
                            x = 0;
                            y = 0;
                            z = -1;
                            itm.setInverted(!itm.getInverted());
                            break block0;
                        }
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "00+": {
                        if (itm.getIsStairs()) {
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
        itm.FromBlockFace(MaterialUtil.getFacingByMod(x, y, z));
        return itm;
    }

    public static BlockDef GetBlockDef(Block sourceBlock, int offsetX, int offsetY, int offsetZ, Player player) {
        Chunk chunk;
        World world = player.getWorld();
        if (!world.isChunkLoaded(chunk = world.getChunkAt(sourceBlock))) {
            world.loadChunk(chunk);
        }
        BlockDef def = new BlockDef();
        def.setMaterialType(sourceBlock.getType().name());
        def.setX(sourceBlock.getX() - offsetX);
        def.setY(sourceBlock.getY() - offsetY);
        def.setZ(sourceBlock.getZ() - offsetZ);
        def.setBlockFaceCode("");
        MaterialData sMat = sourceBlock.getState().getData();
        BlockState blockState = sourceBlock.getState();
        def.setMaterialData(blockState.getRawData());
        def.setIsStairs(false);
        if (sMat instanceof Stairs) {
            def.setIsStairs(true);
            Stairs stairs = (Stairs) sMat;
            def.FromBlockFace(stairs.getDescendingDirection());
            def.setInverted(stairs.isInverted());
        } else if (sMat instanceof Ladder) {
            Ladder ladder = (Ladder) sMat;
            def.FromBlockFace(ladder.getAttachedFace());
        } else if (sMat instanceof Directional) {
            Directional d = (Directional) sMat;
            def.FromBlockFace(d.getFacing());
        }
        return def;
    }

    public static void SetBlock(Block changeBlock, BlockDef def, Player player, boolean erase) {
        Chunk chunk;
        World world = player.getWorld();
        if (!world.isChunkLoaded(chunk = world.getChunkAt(changeBlock))) {
            world.loadChunk(chunk);
        }
        changeBlock.setType(Material.getMaterial((String) def.getMaterialType()), true);
        if (erase) {
            Block s1 = changeBlock.getRelative(BlockFace.UP, 1);
            if (s1.isLiquid()) {
                s1.setType(Material.AIR, true);
            }
            if ((s1 = changeBlock.getRelative(BlockFace.DOWN, 1)).isLiquid()) {
                s1.setType(Material.AIR, true);
            }
            if ((s1 = changeBlock.getRelative(BlockFace.EAST, 1)).isLiquid()) {
                s1.setType(Material.AIR, true);
            }
            if ((s1 = changeBlock.getRelative(BlockFace.WEST, 1)).isLiquid()) {
                s1.setType(Material.AIR, true);
            }
            if ((s1 = changeBlock.getRelative(BlockFace.NORTH, 1)).isLiquid()) {
                s1.setType(Material.AIR, true);
            }
            if ((s1 = changeBlock.getRelative(BlockFace.SOUTH, 1)).isLiquid()) {
                s1.setType(Material.AIR, true);
            }
        }
        BlockState state = changeBlock.getState();
        state.setRawData(def.getMaterialData());
        state.update(true);
        if (!def.getBlockFaceCode().equals("")) {
            state = changeBlock.getState();
            MaterialData tMat = state.getData();
            if (tMat instanceof Stairs) {
                state = changeBlock.getState();
                tMat = state.getData();
                Stairs stairs = (Stairs) tMat;
                switch (def.ToBlockFace()) {
                    case EAST: {
                        stairs.setFacingDirection(BlockFace.WEST);
                        break;
                    }
                    case WEST: {
                        stairs.setFacingDirection(BlockFace.EAST);
                        break;
                    }
                    case NORTH: {
                        stairs.setFacingDirection(BlockFace.SOUTH);
                        break;
                    }
                    case SOUTH: {
                        stairs.setFacingDirection(BlockFace.NORTH);
                    }
                }
                state.setData((MaterialData) stairs);
                state.update(true);
                //stairs = (Stairs)changeBlock.getState().getData();
                state = changeBlock.getState();
                tMat = state.getData();
                stairs = (Stairs) tMat;
                stairs.setInverted(def.getInverted());
                state.setData((MaterialData) stairs);
                state.update(true);
                state = changeBlock.getState();
                tMat = state.getData();
                //stairs = (Stairs)tMat;
            } else if (tMat instanceof Directional) {
                Directional td = (Directional) tMat;
                td.setFacingDirection(def.ToBlockFace());
                state.setData((MaterialData) td);
                state.update(true);
            }
        }
    }

}
