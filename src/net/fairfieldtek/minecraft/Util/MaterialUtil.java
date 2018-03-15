package net.fairfieldtek.minecraft.Util;

import org.bukkit.DyeColor;
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

    public static DyeColor getDyeColor(String name) {
        for (DyeColor color : DyeColor.values()) {
            if (color.name().equals(name)) {
                return color;
            }
        }
        return DyeColor.RED;
    }
}
