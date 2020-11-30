package com.Blockelot.worldeditor.http;

public class LsResponse {

    private boolean IsAuthorized = false;

    public boolean getIsAuthorized() {
        return this.IsAuthorized;
    }

    public void setIsAuthorized(boolean isAuthorized) {
        this.IsAuthorized = isAuthorized;
    }

    private String Auth;

    public String getAuth() {
        return Auth;
    }

    public void setAuth(String auth) {
        Auth = auth;
    }

    private String Message;

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String msg) {
        this.Message = msg;
    }

    private String Uuid;

    public String getUuid() {
        return this.Uuid;
    }

    public void setUuid(String uuid) {
        this.Uuid = uuid;
    }

    private boolean WasSuccessful = false;

    public Boolean getWasSuccessful() {
        return this.WasSuccessful;
    }
    private String DirectoryPath;

    public String getDirectoryPath() {
        return this.DirectoryPath;
    }

    public void setDirectoryPath(String path) {
        this.DirectoryPath = path;
    }

    private DirectoryElement[] Contents;

    public DirectoryElement[] getContents() {
        return this.Contents;
    }

    public void setContents(DirectoryElement[] elements) {
        this.Contents = elements;
    }

}
