package com.Blockelot.worldeditor.container;

import java.util.*;
import org.bukkit.entity.Player;

public class PlayerInfo {

    public PlayerInfo(Player player) {
        Player = player;
        setUUID(player.getUniqueId().toString());
    }

    private String UUID;

    private Player Player;

    public Player getPlayer() {
        return Player;
    }

    public void setPlayer(Player player) {
        Player = player;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String uuid) {
        UUID = uuid;
    }

    private String LastAuth = "";
    private String CurrentPath = "";
    public IPoint SelectStart = null;
    public IPoint SelectEnd = null;

    public BlockCollection ClipSchematic = new BlockCollection();

    private BlockCollection UndoSchematic = new BlockCollection();

    private final Stack<BlockCollection> UndoHistory = new Stack<>();

    public BlockCollection NewUndo() {
        UndoHistory.push(UndoSchematic);
        UndoSchematic = new BlockCollection();
        return UndoSchematic;
    }

    public BlockCollection GetUndo() {
        BlockCollection current = UndoSchematic;
        if (!UndoHistory.empty()) {
            UndoSchematic = UndoHistory.pop();
        } else {
            UndoSchematic = new BlockCollection();
        }
        return current;
    }

    public void ClearHistory() {
        UndoSchematic = new BlockCollection();
        UndoHistory.empty();
    }

    public String Token;
    public boolean CancelLastAction = false;

    private boolean IsProcessing;

    public boolean getIsProcessing() {
        return this.IsProcessing;
    }

    public void setIsProcessing(boolean flag, String caller) {
        IsProcessing = flag;
        //ServerUtil.consoleLog(caller + " is setting Busy: " + flag);
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
