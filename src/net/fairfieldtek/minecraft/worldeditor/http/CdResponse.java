package net.fairfieldtek.minecraft.worldeditor.http;

public class CdResponse
extends BaseResponse {
    private String DirectoryPath;

    public String getDirectoryPath() {
        return this.DirectoryPath;
    }

    public void setDirectoryPath(String path) {
        this.DirectoryPath = path;
    }
}

