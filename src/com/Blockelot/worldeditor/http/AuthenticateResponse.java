package com.Blockelot.worldeditor.http;

public class AuthenticateResponse {

    private boolean IsAuthorized = false;
    private String Auth = "";
    private String Message;
    private String Uuid;
    private boolean WasSuccessful = false;

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

    public String getAuth() {
        return this.Auth;
    }

    public void setAuth(String lastAuth) {
        this.Auth = lastAuth;
    }

    public boolean getIsAuthorized() {
        return this.IsAuthorized;
    }

    public void setIsAuthorized(boolean isAuthorized) {
        this.IsAuthorized = isAuthorized;
    }

    public String getUuid() {
        return this.Uuid;
    }

    public void setUuid(String uuid) {
        this.Uuid = uuid;
    }
}
