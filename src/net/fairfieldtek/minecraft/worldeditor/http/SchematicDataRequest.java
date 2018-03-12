package net.fairfieldtek.minecraft.worldeditor.http;

import net.fairfieldtek.minecraft.worldeditor.container.BlockDef;

public class SchematicDataRequest
extends LsRequest {
    private BlockDef[] Blocks;
    private String FileName;
    private int SchematicId;

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

    public BlockDef[] getBlocks() {
        return this.Blocks;
    }

    public void setBlocks(BlockDef[] blocks) {
        this.Blocks = blocks;
    }
}

