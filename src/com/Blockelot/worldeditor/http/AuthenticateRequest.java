package com.Blockelot.worldeditor.http;

public class AuthenticateRequest {

    private String Uuid;
    private String Auth;
    private String Wid;

    public String getUuid() {
        return this.Uuid;
    }

    public void setUuid(String uuid) {
        this.Uuid = uuid;
    }

    public void setAuth(String auth) {
        Auth = auth;
    }

    public String getAuth() {
        return Auth;
    }

    public String getWid() {
        return this.Wid;
    }

    public void SetWid(String wid) {
        this.Wid = wid;
    }
}
