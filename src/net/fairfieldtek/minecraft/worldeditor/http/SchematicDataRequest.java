package net.fairfieldtek.minecraft.worldeditor.http;

import net.fairfieldtek.minecraft.worldeditor.container.BlockInfo;
import net.fairfieldtek.minecraft.worldeditor.container.PaletteEntry;

public class SchematicDataRequest
        extends LsRequest {

    private String[] Blocks;
    private String FileName;
    private int SchematicId;
    private PaletteEntry[] BlockDataPalette;
    private PaletteEntry[] BlockTypePalette;

    public PaletteEntry[] getBlockDataPalette() {
        return BlockDataPalette;
    }

    public void setBlockDataPalette(PaletteEntry[] palette) {
        BlockDataPalette = palette;
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
