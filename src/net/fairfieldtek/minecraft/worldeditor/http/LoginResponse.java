package net.fairfieldtek.minecraft.worldeditor.http;

public class LoginResponse
        extends BaseResponse {

    private String CurrentPath = "Not Set";

    public String getCurrentPath() {
        return this.CurrentPath;
    }

    public void setCurrentPath(String path) {
        this.CurrentPath = path;
    }
}
