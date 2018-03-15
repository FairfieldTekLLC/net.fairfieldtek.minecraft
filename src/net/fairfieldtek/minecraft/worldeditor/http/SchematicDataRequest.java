package net.fairfieldtek.minecraft.worldeditor.http;

import net.fairfieldtek.minecraft.worldeditor.container.BlockDef;

public class SchematicDataRequest
extends LsRequest {
    private String[] Blocks;
    private String FileName;
    private int SchematicId;
    private String[] ColorPalette;
    private String[] BlockTypePalette;
    
    public String[] getColorPalette(){
        return ColorPalette;
    }
    
    public void setColorPalette(String[] palette)
    {
        ColorPalette = palette;
    }
    
    public String[] getBlockTypePalette(){
        return BlockTypePalette;
    }
    
    public void setBlockTypePalette(String[] palette)
    {
     BlockTypePalette=palette;   
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

