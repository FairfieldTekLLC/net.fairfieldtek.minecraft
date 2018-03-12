package net.fairfieldtek.minecraft.worldeditor.http;

public class RegisterRequest {
    private String Uuid;
    private String EmailAddress;

    public String getEmailAddress() {
        return this.EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.EmailAddress = emailAddress;
    }

    public String getUuid() {
        return this.Uuid;
    }

    public void setUuid(String uuid) {
        this.Uuid = uuid;
    }
}

