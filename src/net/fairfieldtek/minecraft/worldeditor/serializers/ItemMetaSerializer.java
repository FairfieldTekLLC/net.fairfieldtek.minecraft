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

import org.bukkit.inventory.meta.*;
import net.fairfieldtek.minecraft.worldeditor.container.*;
import java.util.*;

/**
 *
 * @author geev
 */
public class ItemMetaSerializer {

    private final BlockCollection SchematicDef;
    private int DisplayNameIdx;
    private int LocalizedNameIdx;
    private ArrayList<Integer> Lore = new ArrayList<>();

    public void setDisplayName(String name) {
        DisplayNameIdx = SchematicDef.addBlockSettingsPalette(name);
    }

    public void setLocalizedName(String name) {
        this.LocalizedNameIdx = SchematicDef.addBlockSettingsPalette(name);
    }

    public String getDisplayName() {
        return SchematicDef.getBlockSettingsPalette(DisplayNameIdx);

    }

    public String getLocalizedName() {
        return SchematicDef.getBlockSettingsPalette(LocalizedNameIdx);
    }
    
    public void setLore(String[] lore)
    {
        Lore.clear();
        for (String ent :lore)
        {
            Lore.add(SchematicDef.addBlockSettingsPalette(ent));
        }
    }

    public String[] getLore() {
        ArrayList<String> lore = new ArrayList<>();
        Lore.forEach((idx) -> {
            lore.add(SchematicDef.getBlockSettingsPalette(idx));
        });
        String[] loreArray = new String[Lore.size()];
        lore.toArray(loreArray);
        return loreArray;
    }

    public ItemMetaSerializer(ItemMeta meta, BlockCollection schematicDef) {
        this.SchematicDef = schematicDef;
        if (meta.hasDisplayName()) {
            this.DisplayNameIdx = schematicDef.addBlockSettingsPalette(meta.getDisplayName());
        } else {
            this.DisplayNameIdx = schematicDef.addBlockSettingsPalette("");
        }

        if (meta.hasLocalizedName()) {
            this.LocalizedNameIdx = schematicDef.addBlockSettingsPalette(meta.getLocalizedName());
        } else {
            this.LocalizedNameIdx = schematicDef.addBlockSettingsPalette("");
        }

        meta.getLore().forEach((lore) -> {
            Lore.add(SchematicDef.addBlockSettingsPalette(lore));
        });
    }
}
