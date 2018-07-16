package net.fairfieldtek.minecraft.Util;

import org.bukkit.DyeColor;
import org.bukkit.block.BlockFace;
import org.bukkit.TreeSpecies;
import org.bukkit.block.banner.PatternType;
import org.bukkit.CoalType;
import org.bukkit.block.Biome;
import org.bukkit.CropState;
import org.bukkit.GrassSpecies;
import org.bukkit.Note;
import org.bukkit.Note.Tone;

public class MaterialUtil {

      public static org.bukkit.Note.Tone getTone(String name){
        for (org.bukkit.Note.Tone s : org.bukkit.Note.Tone.values()) {
            if (s.name().equals(name)) {
                return s;
            }
        }
        return org.bukkit.Note.Tone.A;
    }
      
    
    public static org.bukkit.GrassSpecies getGrassSpecies(String name){
        for (org.bukkit.GrassSpecies s : org.bukkit.GrassSpecies.values()) {
            if (s.name().equals(name)) {
                return s;
            }
        }
        return org.bukkit.GrassSpecies.NORMAL;
    }
    
    public static org.bukkit.CropState getCropState(String name) {
        for (org.bukkit.CropState s : org.bukkit.CropState.values()) {
            if (s.name().equals(name)) {
                return s;
            }
        }
        return org.bukkit.CropState.GERMINATED;
    }

    public static org.bukkit.block.Biome getBiome(String name) {
        for (org.bukkit.block.Biome s : org.bukkit.block.Biome.values()) {
            if (s.name().equals(name)) {
                return s;
            }
        }
        return org.bukkit.block.Biome.PLAINS;
    }

    public static org.bukkit.material.CocoaPlant.CocoaPlantSize getCocoaPlantSize(String name) {
        for (org.bukkit.material.CocoaPlant.CocoaPlantSize s : org.bukkit.material.CocoaPlant.CocoaPlantSize.values()) {
            if (s.name().equals(name)) {
                return s;
            }
        }
        return org.bukkit.material.CocoaPlant.CocoaPlantSize.SMALL;
    }

    public static CoalType getCoalType(String name) {
        for (CoalType t : CoalType.values()) {
            if (t.name().equals(name)) {
                return t;
            }
        }
        return CoalType.CHARCOAL;
    }

    public static PatternType getPatternType(String name) {
        for (PatternType pt : PatternType.values()) {
            if (pt.name().equals(name)) {
                return pt;
            }
        }
        return PatternType.BASE;
    }

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

    public static TreeSpecies getTreeSpecies(String name) {
        for (TreeSpecies color : TreeSpecies.values()) {
            if (color.name().equals(name)) {
                return color;
            }
        }
        return TreeSpecies.GENERIC;
    }
}
