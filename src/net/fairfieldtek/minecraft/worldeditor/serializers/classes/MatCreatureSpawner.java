///*
// * Copyright (C) 2018 geev
// *
// * This program is free software; you can redistribute it and/or
// * modify it under the terms of the GNU General Public License
// * as published by the Free Software Foundation; either version 2
// * of the License, or (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program; if not, write to the Free Software
// * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
// */
//package net.fairfieldtek.minecraft.worldeditor.serializers.classes;
//
//import net.fairfieldtek.minecraft.Util.EnumHelper;
//import net.fairfieldtek.minecraft.worldeditor.serializers.ISerializer;
//import net.fairfieldtek.minecraft.worldeditor.serializers.Serializer;
//import org.bukkit.block.Block;
//import org.bukkit.entity.EntityType;
//
///**
// *
// * @author geev
// */
//public class MatCreatureSpawner extends Serializer implements ISerializer {
//    public static boolean isType(Block block) {
//        return (block.getState() instanceof org.bukkit.block.CreatureSpawner);
//    }
//
//    private int Delay;
//    private int MaxNearbyEntities;
//    private int MaxSpawnDelay;
//    private int MinSpawnDelay;
//    private int RequiredPlayerRange;
//    private int SpawnCount;
//    private int SpawnTypeIdx;
//    public MatCreatureSpawner(Block block, int xOffset, int yOffset, int zOffset, net.fairfieldtek.minecraft.worldeditor.container.SchematicDef schematicDef) {
//        super(block, xOffset, yOffset, zOffset, schematicDef);
//        if (!isType(block)) {
//            throw new RuntimeException("Invalid Type");
//        }
//        
//        org.bukkit.block.CreatureSpawner spawner = (org.bukkit.block.CreatureSpawner) block.getState();
//        
//        
//        
//        setDelay(spawner.getDelay());
//        setMaxNearbyEntities(spawner.getMaxNearbyEntities());
//        setMaxSpawnDelay(spawner.getMaxSpawnDelay());
//        setMinSpawnDelay(spawner.getMinSpawnDelay());
//        setRequiredPlayerRange(spawner.getRequiredPlayerRange());
//        setSpawnCount(spawner.getSpawnCount());
//        setSpawnType(spawner.getSpawnedType());
//    }
//
//    public final  void setSpawnType(EntityType entity) {
//        SpawnTypeIdx = getSchematicDef().addBlockSettingsPalette(entity.name());
//    }
//
//    public final  EntityType getSpawnType() {
//        return EnumHelper.getEntityType(getSchematicDef().getBlockSettingsPalette(SpawnTypeIdx));
//    }
//
//    public final  int getSpawnCount() {
//        return SpawnCount;
//    }
//
//    public final  void setSpawnCount(int n) {
//        SpawnCount = n;
//    }
//
//    public final  void setRequiredPlayerRange(int m) {
//        RequiredPlayerRange = m;
//    }
//
//    public final  int getRequiredPlayerRange() {
//        return RequiredPlayerRange;
//    }
//
//    public  final void setMinSpawnDelay(int ticks) {
//        MinSpawnDelay = ticks;
//    }
//
//    public  final int getMinSpawnDelay() {
//        return MinSpawnDelay;
//    }
//
//    public final  void setMaxSpawnDelay(int ticks) {
//        MaxSpawnDelay = ticks;
//    }
//
//    public  final int getMaxSpawnDelay() {
//        return MaxSpawnDelay;
//    }
//
//    public  final void setMaxNearbyEntities(int num) {
//        MaxNearbyEntities = num;
//    }
//
//    public  final int getMaxNearbyEntities() {
//        return MaxNearbyEntities;
//    }
//
//    public final  void setDelay(int delay) {
//        Delay = delay;
//    }
//
//    public final  int getDelay() {
//        return Delay;
//    }
//
//
//}
