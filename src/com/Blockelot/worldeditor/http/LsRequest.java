package com.Blockelot.worldeditor.http;

public class LsRequest {

    private String Uuid;
    private String AuthToken;
    private String CurrentDirectory;

    public String getCurrentDirectory() {
        return this.CurrentDirectory;
    }

    public void setCurrentDirectory(String path) {
        this.CurrentDirectory = path;
    }

    public String getAuthToken() {
        return this.AuthToken;
    }

    public void setAuthToken(String token) {
        this.AuthToken = token;
    }

    public String getUuid() {
        return this.Uuid;
    }

    public void setUuid(String uuid) {
        this.Uuid = uuid;
    }
}
