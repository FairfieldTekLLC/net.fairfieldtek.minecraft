package com.Blockelot.worldeditor.http;

public class CdResponse {

    private String Auth;
    private String Message;
    private String Uuid;
    private boolean WasSuccessful = false;
    private String DirectoryPath;

    public Boolean getWasSuccessful() {
        return this.WasSuccessful;
    }

    public void setWasSuccessful(Boolean status) {
        this.WasSuccessful = status;
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

    public String getAuth() {
        return Auth;
    }

    public void setAuth(String auth) {
        Auth = auth;
    }

    public String getDirectoryPath() {
        return this.DirectoryPath;
    }

    public void setDirectoryPath(String path) {
        this.DirectoryPath = path;
    }
}
