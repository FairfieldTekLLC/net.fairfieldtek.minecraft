package com.Blockelot.worldeditor.http;

import java.util.List;

public class DirLsResponse {

    private String Uuid;

    private String Auth;

    public String getUuid() {
        return this.Uuid;
    }

    public void setUuid(String uuid) {
        this.Uuid = uuid;
    }

    public String getAuth() {
        return Auth;
    }

    public void setAuth(String auth) {
        Auth = auth;
    }

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
