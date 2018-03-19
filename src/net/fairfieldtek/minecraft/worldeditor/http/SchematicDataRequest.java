package net.fairfieldtek.minecraft.worldeditor.http;

import net.fairfieldtek.minecraft.worldeditor.container.BlockDef;
import net.fairfieldtek.minecraft.worldeditor.container.PaletteEntry;

public class SchematicDataRequest
        extends LsRequest {

    private String[] Blocks;
    private String FileName;
    private int SchematicId;
    private PaletteEntry[] ColorPalette;
    private PaletteEntry[] BlockTypePalette;

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

    public int getSchematicId() {
        return this.SchematicId;
    }

    public void setSchematicId(int id) {
        this.SchematicId = id;
    }

    public String getFileName() {
        return this.FileName;
    }

    public void setFileName(String name) {
        this.FileName = name;
    }

    public String[] getBlocks() {
        return this.Blocks;
    }

    public void setBlocks(String[] blocks) {
        this.Blocks = blocks;
    }
}
