package com.Blockelot.worldeditor.http;

public class LoginRequest {

    private String Uuid;

    private String Auth;
    
    private String Wid;
    
    public String getWid(){
        return Wid;
    }

    public void setWid(String g){
        Wid = g;
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

}
