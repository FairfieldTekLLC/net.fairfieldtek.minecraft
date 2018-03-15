package net.fairfieldtek.minecraft.worldeditor.container;

import java.util.ArrayList;
import java.util.*;

public class PlayerInfo {

    private String LastAuth = "";
    private String CurrentPath = "";
    public IPoint SelectStart = null;
    public IPoint SelectEnd = null;

    public SchematicDef ClipSchematic = new SchematicDef();

    private SchematicDef UndoSchematic = new SchematicDef();

    private Stack<SchematicDef> UndoHistory = new Stack<SchematicDef>();

    public SchematicDef NewUndo() {
        UndoHistory.push(UndoSchematic);
        UndoSchematic = new SchematicDef();
        return UndoSchematic;
    }

    public SchematicDef GetUndo() {
        SchematicDef current = UndoSchematic;
        if (!UndoHistory.empty()) {
            UndoSchematic = UndoHistory.pop();
        } else {
            UndoSchematic = new SchematicDef();
        }
        return current;
    }
    
    public void ClearHistory(){
        UndoSchematic = new SchematicDef();
        UndoHistory.empty();
    }

    public String Token;
    public boolean CancelLastAction = false;

    private boolean IsProcessing;

    public boolean getIsProcessing() {
        System.out.println("Checking Busy? " + IsProcessing);
        return this.IsProcessing;
    }

    public void setIsProcessing(boolean flag, String caller) {
        IsProcessing = flag;
        System.out.println(caller + " is setting Busy: " + flag);
    }

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
