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

import net.fairfieldtek.minecraft.Util.EnumHelper;
import net.fairfieldtek.minecraft.worldeditor.serializers.ISerializer;
import net.fairfieldtek.minecraft.worldeditor.serializers.Serializer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 *
 * @author geev
 */
public class MatDiode   extends Serializer implements ISerializer {
    public static boolean isType(Block block) {
        return block.getState().getData() instanceof org.bukkit.material.Diode;
    }
    private int FacingIdx;
    private int Delay;
    public MatDiode(Block block, int xOffset, int yOffset, int zOffset, net.fairfieldtek.minecraft.worldeditor.container.SchematicDef schematicDef) {
        super(block, xOffset, yOffset, zOffset, schematicDef);
        if (!isType(block)) {
            throw new RuntimeException("Invalid Type");
        }
        
        org.bukkit.material.Diode d = (org.bukkit.material.Diode)block.getState().getData();
        setFacing(d.getFacing());
        setDelay(d.getDelay());
        
        
        
        
        
    }
    public int getDelay(){
        return Delay;
    }
    public final  void setDelay(int ticks)
    {
        Delay=ticks;
    }

    public final  void setFacing(BlockFace facing) {
        FacingIdx = getSchematicDef().addBlockSettingsPalette(EnumHelper.ToCodeFromBlockFace(facing));
    }

    public final  BlockFace getFacing() {
        return EnumHelper.ToBlockFaceFromCode(getSchematicDef().getBlockSettingsPalette(FacingIdx));
    }
    
    
}
