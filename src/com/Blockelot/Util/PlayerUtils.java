package com.Blockelot.Util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.block.BlockFace;

public class PlayerUtils {

    public static final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator((LivingEntity) player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext() && (lastBlock = iter.next()).getType() == Material.AIR) {
        }
        return lastBlock;
    }

    /**
     * Get the cardinal compass direction of a player.
     *
     * @param player
     * @return
     */
    public static BlockFace getCardinalDirection(Player player) {
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        return getDirection(rot);
    }

    /**
     * Converts a rotation to a cardinal direction name.
     *
     * @param rot
     * @return
     */
    private static BlockFace getDirection(double rot) {

        if (0 <= rot && rot < 22.5) {
            return BlockFace.WEST;
        } else if (22.5 <= rot && rot < 67.5) {
            return BlockFace.NORTH_WEST;
        } else if (67.5 <= rot && rot < 112.5) {
            return BlockFace.NORTH;
        } else if (112.5 <= rot && rot < 157.5) {
            return BlockFace.NORTH_EAST;
        } else if (157.5 <= rot && rot < 202.5) {
            return BlockFace.EAST;
        } else if (202.5 <= rot && rot < 247.5) {
            return BlockFace.SOUTH_EAST;
        } else if (247.5 <= rot && rot < 292.5) {
            return BlockFace.SOUTH;
        } else if (292.5 <= rot && rot < 337.5) {
            return BlockFace.SOUTH_WEST;
        } else if (337.5 <= rot && rot < 360.0) {
            return BlockFace.WEST;
        } else {
            return null;
        }
    }
}
