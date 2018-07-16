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
import net.fairfieldtek.minecraft.worldeditor.container.SchematicDef;
import org.bukkit.block.Block;
import org.bukkit.block.Biome;

/**
 *
 * This stuff is confusing to say the least it appears that org.bukkit.block is
 * incomplete atm So I will need to drive everything off org.bukkit.Material
 * which should not be confused with org.bukkit.material
 *
 * @author geev
 */
public abstract class Serializer {

    private int X;
    private int Y;
    private int Z;
    private int MaterialTypeIdx;
    private int BiomeTypeIdx;
    private double Temperature;
    private SchematicDef SchematicDef;
    private byte Data;
    
    public final  void setData(byte data){
        this.Data = data;
    }
    public final  byte getData(){
        return this.Data;
    }
    
    public Serializer(Block block, int xOffset, int yOffset, int zOffset, SchematicDef schematicDef) {
        SchematicDef = schematicDef;
        X = block.getX() - xOffset;
        Y = block.getY() - yOffset;
        X = block.getZ() - zOffset;
        MaterialTypeIdx = schematicDef.addBlockSettingsPalette(block.getType().name());
        BiomeTypeIdx = schematicDef.addBlockSettingsPalette(block.getBiome().name());
        Temperature = block.getTemperature();
        Data = block.getData();
        
    }

    public final  Biome getBiomeType() {
        return MaterialUtil.getBiome(SchematicDef.getBlockSettingsPalette(BiomeTypeIdx));
    }

    public final  void setBiomeType(Biome biome) {
        BiomeTypeIdx = SchematicDef.addBlockSettingsPalette(biome.name());
    }
    
    public  final double getTemperature(){
        return Temperature;
    }

    public  final void setTemperature(double t){
        this .Temperature=t;
    }
    

    public final  int getX() {
        return X;
    }

    public  final void setX(int x) {
        this.X = x;
    }

    public final  int getY() {
        return Y;
    }

    public  final int getZ() {
        return Z;
    }

    public  final void setY(int y) {
        Y = y;
    }

    public final  void setZ(int z) {
        Z = z;
    }

    public final  void setMaterialType(org.bukkit.Material mat) {
        this.MaterialTypeIdx = SchematicDef.addBlockSettingsPalette(mat.name());
    }

    public  final org.bukkit.Material getMaterialType() {
        return org.bukkit.Material.getMaterial(SchematicDef.getBlockSettingsPalette(this.MaterialTypeIdx));
    }

    public final  SchematicDef getSchematicDef() {
        return this.SchematicDef;
    }

    public final  void setSchematicDef(SchematicDef def) {
        this.SchematicDef = def;
    }
}
