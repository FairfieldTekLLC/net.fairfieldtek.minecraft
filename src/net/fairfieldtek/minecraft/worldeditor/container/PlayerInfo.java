package net.fairfieldtek.minecraft.worldeditor.container;

import java.util.ArrayList;

public class PlayerInfo {

    private String LastAuth = "";
    private String CurrentPath = "";
    public IPoint SelectStart = null;
    public IPoint SelectEnd = null;
    public ArrayList<BlockDef> ClipBoard = new ArrayList();
    public ArrayList<BlockDef> UndoBuffer = new ArrayList();
    public String Token;
    public boolean CancelLastAction = false;

    public String getCurrentPath() {
        return this.CurrentPath;
    }

    public void setCurrentPath(String path) {
        this.CurrentPath = path;
    }

    public String getLastAuth() {
        return this.LastAuth;
    }

    public void setLastAuth(String lastAuth) {
        this.LastAuth = lastAuth;
    }
}
