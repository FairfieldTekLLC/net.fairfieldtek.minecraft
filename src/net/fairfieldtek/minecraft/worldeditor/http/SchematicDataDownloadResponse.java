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

import net.fairfieldtek.minecraft.worldeditor.container.BlockDef;
import net.fairfieldtek.minecraft.worldeditor.container.PaletteEntry;

/**
 *
 * @author geev
 */
public class SchematicDataDownloadResponse
        extends BaseResponse {

    private String FileName = "";
    private PaletteEntry[] ColorPalette = null;
    private PaletteEntry[] BlockTypePalette = null;
    private BlockDef[] Blocks = null;

    public PaletteEntry[] getColorPalette() {
        return ColorPalette;
    }

    public void setColorPalette(PaletteEntry[] palette) {
        ColorPalette = palette;
    }

    public PaletteEntry[] getBlockTypePalette() {
        return BlockTypePalette;
    }

    public void setBlockTypePalette(PaletteEntry[] palette) {
        BlockTypePalette = palette;
    }

    public String getFileName() {
        return this.FileName;
    }

    public void setFileName(String name) {
        this.FileName = name;
    }

    public BlockDef[] getBlocks() {
        return this.Blocks;
    }

    public void setBlocks(BlockDef[] blocks) {
        this.Blocks = blocks;
    }
}
