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
import org.bukkit.material.*;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Stairs;
import org.bukkit.DyeColor;

public class BlockUtil {

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
        BlockState blockState = sourceBlock.getState();
        def.setMaterialData(blockState.getRawData());
        def.setIsStairs(false);

        if (!def.StairsGetDirectionalCond(sourceBlock)) {
            if (!def.LadderGetDirectionalCond(sourceBlock)) {
                if (!def.BedGetDirectionalCond(sourceBlock)) {
                    if (!def.GetDirectionalCond(sourceBlock)) {
                        System.out.println("No Condition");
                    }
                }
            }
        }

        return def;
    }

    private static void erase(Block changeBlock) {
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

    public static void SetBlock(Block changeBlock, BlockDef def, Player player, boolean erase) {
        Chunk chunk;
        World world = player.getWorld();

        if (!world.isChunkLoaded(chunk = world.getChunkAt(changeBlock))) {
            world.loadChunk(chunk);
        }
        if (erase) {
            erase(changeBlock);
        }

        if (!def.BedCreate(changeBlock)) {
            if (!def.StairsCreate(changeBlock)) {
                def.GeneralCreate(changeBlock);
            }
        }

    }

}
