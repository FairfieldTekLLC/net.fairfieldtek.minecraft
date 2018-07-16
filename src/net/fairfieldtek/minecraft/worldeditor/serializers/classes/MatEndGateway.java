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
package net.fairfieldtek.minecraft.worldeditor.serializers.classes;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.serializers.ISerializer;
import net.fairfieldtek.minecraft.worldeditor.serializers.Serializer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 *
 * @author geev
 */
public class MatEndGateway extends Serializer implements ISerializer {
    public static boolean isType(Block block) {
        return block.getState() instanceof org.bukkit.block.EndGateway;
    }

    private int IsExactTeleport;
    private int ExitLocationX;
    private int ExitLocationY;
    private int ExitLocationZ;
    private int ExitLocationWorldUUIDIdx;
    public MatEndGateway(Block block, int xOffset, int yOffset, int zOffset, net.fairfieldtek.minecraft.worldeditor.container.SchematicDef schematicDef) {
        super(block, xOffset, yOffset, zOffset, schematicDef);
        if (!isType(block)) {
            throw new RuntimeException("Invalid Type");
        }
        
        org.bukkit.block.EndGateway g = (org.bukkit.block.EndGateway)block.getState();
        setIsExactTeleport(g.isExactTeleport());
        setExitLocation(g.getExitLocation());
        
    }

    public final  boolean getIsExactTeleport() {
        return IsExactTeleport == 1;
    }

    public final  void setIsExactTeleport(boolean exact) {
        if (exact) {
            IsExactTeleport = 1;
        } else {
            IsExactTeleport = 0;
        }
    }

    public  final Location getExitLocation() {
        World world = Initialization.Plugin.getServer().getWorld(getSchematicDef().getBlockSettingsPalette(ExitLocationWorldUUIDIdx));
        Location loc = new Location(world, ExitLocationX, ExitLocationY, ExitLocationZ);
        return loc;
    }

    public  final void setExitLocation(Location loc) {
        ExitLocationWorldUUIDIdx = getSchematicDef().addBlockSettingsPalette(loc.getWorld().getUID().toString());
        ExitLocationX = loc.getBlockX();
        ExitLocationY = loc.getBlockY();
        ExitLocationZ = loc.getBlockZ();
    }


}
