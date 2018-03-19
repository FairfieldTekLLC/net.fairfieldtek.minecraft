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
package net.fairfieldtek.minecraft.worldeditor.container;

/**
 *
 * @author geev
 */
public class PaletteEntry {
    
    private int Id;
    private String Value;
    
    public PaletteEntry(){}
    
    public PaletteEntry Clone(){
        PaletteEntry e = new PaletteEntry();
        e.Id=this.Id;
        e.Value=this.Value;
        return e;
    }
    
    public PaletteEntry(int i,String v)
    {
        this.Id=i;
        this.Value = v.trim();
    }
    
    
    public int getId(){
        return this.Id;
    }
    public void setId(int i)
    {
        this.Id=i;
    }
    
    public String getValue(){
        return this.Value;
    }
    public void setValue(String v)
    {
        this.Value = v.trim();
    }
    
}
