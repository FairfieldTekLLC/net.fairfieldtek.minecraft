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
package net.fairfieldtek.minecraft.worldeditor.http;

/**
 *
 * @author geev
 */
public class BlockDefXfer {
     byte M;//MaterialData;
     int X;
     int Y;
     int Z;
     boolean I;//Inverted;
     boolean S;//IsStairs;
     String B;//BlockFaceCode;
     int T;//BlockTypeIndex;
     int C;//BlockColorIndex;
     public BlockDefXfer(byte materialData,int x,int y,int z,boolean inverted,boolean isStairs,String blockFaceCode,int blockTypeIndex, int blockColorIndex)
     {
         M=materialData;
         X=x;
         Y=y;
         Z=z;
         I=inverted;
         S=isStairs;
         B=blockFaceCode;
         T=blockTypeIndex;
         C=blockColorIndex;
         
     }
}
