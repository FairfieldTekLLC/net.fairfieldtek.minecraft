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

import net.fairfieldtek.minecraft.worldeditor.container.SchematicDef;
import org.bukkit.block.Biome;

/**
 *
 * @author geev
 */
public interface ISerializer {

    public Biome getBiomeType();

    public void setBiomeType(Biome biome);

    public double getTemperature();

    public void setTemperature(double t);

    public int getX();

    public void setX(int x);

    public int getY();

    public int getZ();

    public void setY(int y);

    public void setZ(int z);

    public void setMaterialType(org.bukkit.Material mat);

    public org.bukkit.Material getMaterialType();

    public SchematicDef getSchematicDef();

    public void setSchematicDef(SchematicDef def);
}
