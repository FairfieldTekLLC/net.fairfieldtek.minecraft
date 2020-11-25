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
package net.fairfieldtek.minecraft.worldeditor.serializers;

import net.fairfieldtek.minecraft.Util.MaterialUtil;
import org.bukkit.block.banner.Pattern;
import net.fairfieldtek.minecraft.worldeditor.container.*;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.PatternType;

/**
 *
 * @author geev
 */
public class PatternSerializer {

    private int PatternIdx;
    private int ColorIdx;
    private BlockCollection SchematicDef;
    
    public PatternType getPattern(){
        return MaterialUtil.getPatternType(SchematicDef.getBlockSettingsPalette(PatternIdx));
    }
    public void setPattern(PatternType pat){
         this.PatternIdx = SchematicDef.addBlockSettingsPalette(pat.name());
    }
    public void setColor(DyeColor color)
    {
        this.ColorIdx = SchematicDef.addBlockSettingsPalette(color.name());
    }
    
    public DyeColor getColor(){
        return MaterialUtil.getDyeColor(SchematicDef.getBlockSettingsPalette(ColorIdx));
    }
    
    public PatternSerializer(Pattern pattern, BlockCollection schematicDef) {
        this.PatternIdx = schematicDef.addBlockSettingsPalette(pattern.getPattern().name());
        this.ColorIdx = schematicDef.addBlockSettingsPalette(pattern.getColor().name());
        this.SchematicDef = schematicDef;
    }
    
}
