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

import net.fairfieldtek.minecraft.Util.MaterialUtil;
import net.fairfieldtek.minecraft.worldeditor.serializers.ISerializer;
import net.fairfieldtek.minecraft.worldeditor.serializers.Serializer;
import org.bukkit.CoalType;
import org.bukkit.block.Block;

/**
 *
 * @author geev
 */
public class MatCoal extends Serializer implements ISerializer {
    public static boolean isType(Block block) {
        return (block.getState().getData() instanceof org.bukkit.material.Coal);
    }
    
    private int CoalTypeIdx;
    public MatCoal(Block block, int xOffset, int yOffset, int zOffset, net.fairfieldtek.minecraft.worldeditor.container.SchematicDef schematicDef) {
        super(block, xOffset, yOffset, zOffset, schematicDef);
        if (!isType(block)) {
            throw new RuntimeException("Invalid Type");
        }
        org.bukkit.material.Coal coal = (org.bukkit.material.Coal)block.getState().getData();
        setCoalType(coal.getType());
    }
    
    public final  void setCoalType(CoalType t){
        CoalTypeIdx= getSchematicDef().addBlockSettingsPalette(t.name());
    }
    public final  CoalType getCoalType(){
        return MaterialUtil.getCoalType(getSchematicDef().getBlockSettingsPalette(CoalTypeIdx));
    }
    
    
    
}
