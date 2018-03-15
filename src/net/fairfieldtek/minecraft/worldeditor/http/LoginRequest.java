package net.fairfieldtek.minecraft.worldeditor.http;

public class LoginRequest {

    private String Uuid;
    private String AuthToken;

    public String getUuid() {
        return this.Uuid;
    }

    public void setUuid(String uuid) {
        this.Uuid = uuid;
    }

    public String getAuthToken() {
        return this.AuthToken;
    }

    public void setAuthToken(String authToken) {
        this.AuthToken = authToken;
    }
}
