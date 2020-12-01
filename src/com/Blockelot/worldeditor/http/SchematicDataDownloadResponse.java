package com.Blockelot.worldeditor.http;

import com.Blockelot.worldeditor.container.BlockInfo;
import com.Blockelot.worldeditor.container.PaletteEntry;

/**
 *
 * @author geev
 */
public class SchematicDataDownloadResponse {

    private boolean IsAuthorized = false;
    private String Auth = "";
    private String Message;
    private String Uuid;
    private boolean WasSuccessful = false;
    private String DirectoryPath;
    private String FileName = "";
    private PaletteEntry[] BlockDataPalette = null;
    private PaletteEntry[] BlockTypePalette = null;
    private PaletteEntry[] BlockInvePalette = null;
    private BlockInfo[] Blocks = null;

    public boolean getIsAuthorized() {
        return this.IsAuthorized;
    }

    public void setIsAuthorized(boolean isAuthorized) {
        this.IsAuthorized = isAuthorized;
    }

    public String getAuth() {
        return this.Auth;
    }

    public void setAuth(String lastAuth) {
        this.Auth = lastAuth;
    }

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String msg) {
        this.Message = msg;
    }

    public String getUuid() {
        return this.Uuid;
    }

    public void setUuid(String uuid) {
        this.Uuid = uuid;
    }

    public Boolean getWasSuccessful() {
        return this.WasSuccessful;
    }

    public void setWasSuccessful(Boolean status) {
        this.WasSuccessful = status;
    }

    public String getDirectoryPath() {
        return DirectoryPath;
    }

    public void setDirectoryPath(String path) {
        DirectoryPath = path;
    }

    public String getFileName() {
        return this.FileName;
    }

    public void setFileName(String name) {
        this.FileName = name;
    }

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

    public PaletteEntry[] getBlockInvePalette() {
        return BlockInvePalette;
    }

    public void setBlockInvePalette(PaletteEntry[] palette) {
        BlockInvePalette = palette;
    }

    public BlockInfo[] getBlocks() {
        return this.Blocks;
    }

    public void setBlocks(BlockInfo[] blocks) {
        this.Blocks = blocks;
    }
}
