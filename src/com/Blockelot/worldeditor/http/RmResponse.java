package com.Blockelot.worldeditor.http;

public class RmResponse {

    private boolean IsAuthorized = false;

    public boolean getIsAuthorized() {
        return this.IsAuthorized;
    }

    public void setIsAuthorized(boolean isAuthorized) {
        this.IsAuthorized = isAuthorized;
    }
    private String Auth = "";

    public String getAuth() {
        return this.Auth;
    }

    public void setAuth(String lastAuth) {
        this.Auth = lastAuth;
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

    public void setWasSuccessful(Boolean status) {
        this.WasSuccessful = status;
    }
    private String DirectoryPath;

    public String getDirectoryPath() {
        return this.DirectoryPath;
    }

    public void setDirectoryPath(String path) {
        this.DirectoryPath = path;
    }
}
