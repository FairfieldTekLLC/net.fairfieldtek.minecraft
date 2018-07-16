/*
 * Copyright (C) 2018 geev
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
package net.fairfieldtek.minecraft.Util;

import org.bukkit.DyeColor;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;

/**
 *
 * @author geev
 */
public class EnumHelper {
    
    public static EntityType getEntityType(String name){
        for (EntityType e : EntityType.values()){
            if (e.name()==name)
                return e;
        }
        return EntityType.CHICKEN;
    }

    public static DyeColor GetDyeColor(String color) {
        for (DyeColor c : DyeColor.values()) {
            if (c.name().equals(color)) {
                return c;
            }
        }
        return DyeColor.RED;
    }

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
}
