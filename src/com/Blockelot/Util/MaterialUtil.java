package com.Blockelot.Util;

import org.bukkit.block.BlockFace;

public class MaterialUtil {

    public static BlockFace getFacingByName(String name) {
        for (BlockFace face : BlockFace.values()) {
            if (!face.name().equals(name)) {
                continue;
            }
            return face;
        }
        return null;
    }

    public static BlockFace getFacingByMod(int x, int y, int z) {
        for (BlockFace face : BlockFace.values()) {
            if (face.getModX() != x || face.getModY() != y || face.getModZ() != z) {
                continue;
            }
            return face;
        }
        return null;
    }

}
