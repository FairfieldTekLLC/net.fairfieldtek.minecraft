///*
// * Copyright (C) 2020 geev
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
//package net.fairfieldtek.minecraft.worldeditor.container;
//
//import java.util.ArrayList;
//
///**
// *
// * @author geev
// */
//public class PaletteEntryCollection {
//
//    private ArrayList<PaletteEntry> Collection = new ArrayList<>();
//    private int MaxId = 0;
//
//    public int AddGet(String value) {
//        PaletteEntry pe = new PaletteEntry(MaxId, value);
//        MaxId++;
//        Collection.add(pe);
//        return MaxId;
//    }
//    
//    public int Add(int id,String value){
//          for (PaletteEntry ent : Collection) {
//            if (ent.getValue().equals(value.trim())) {
//                return ent.getId();
//            }
//        }
//        int idx = getMaxPalletId(BlockTypePalette) + 1;
//        BlockTypePalette.add(new PaletteEntry(idx, mat.name()));
//        return idx;
//        
//        
//    }
//
//    public PaletteEntry[] ToArray() {
//        PaletteEntry[] pes = new PaletteEntry[Collection.size()];
//        Collection.toArray(pes);
//        return pes;
//    }
//
//}
