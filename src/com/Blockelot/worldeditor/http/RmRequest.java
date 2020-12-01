package com.Blockelot.worldeditor.http;

public class RmRequest {

    private String TargetDirectory;
    private String CurrentDirectory;
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

    public String getCurrentDirectory() {
        return this.CurrentDirectory;
    }

    public void setCurrentDirectory(String path) {
        this.CurrentDirectory = path;
    }

    public String getTargetDirectory() {
        return this.TargetDirectory;
    }

    public void setTargetDirectory(String target) {
        this.TargetDirectory = target;
    }
}
