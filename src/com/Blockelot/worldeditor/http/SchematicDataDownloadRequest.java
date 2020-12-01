package com.Blockelot.worldeditor.http;

/**
 *
 * @author geev
 */
public class SchematicDataDownloadRequest {

    private String Uuid;
    private String Auth;
    private String FileName;
    private String CurrentDirectory;

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

    public String getFileName() {
        return this.FileName;
    }

    public void setFileName(String name) {
        this.FileName = name;
    }

}
