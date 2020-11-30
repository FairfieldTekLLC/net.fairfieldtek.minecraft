package com.Blockelot.worldeditor.http;

public class RegisterRequest {

    private String EmailAddress;
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

    public String getEmailAddress() {
        return this.EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.EmailAddress = emailAddress;
    }

}
