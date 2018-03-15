package net.fairfieldtek.minecraft.worldeditor.http;

import java.util.List;

public class DirLsResponse
        extends BaseResponse {

    private String DirectoryPath;
    private List<DirectoryElement> Contents;

    public List<DirectoryElement> getContents() {
        return this.Contents;
    }

    public void setContents(List<DirectoryElement> elements) {
        this.Contents = elements;
    }

    public String getDirectoryPath() {
        return this.DirectoryPath;
    }

    public void setDirectoryPath(String path) {
        this.DirectoryPath = path;
    }
}
