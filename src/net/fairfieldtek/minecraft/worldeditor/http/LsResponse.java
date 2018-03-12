package net.fairfieldtek.minecraft.worldeditor.http;

public class LsResponse
extends BaseResponse {
    private String DirectoryPath;
    private DirectoryElement[] Contents;

    public DirectoryElement[] getContents() {
        return this.Contents;
    }

    public void setContents(DirectoryElement[] elements) {
        this.Contents = elements;
    }

    public String getDirectoryPath() {
        return this.DirectoryPath;
    }

    public void setDirectoryPath(String path) {
        this.DirectoryPath = path;
    }
}

