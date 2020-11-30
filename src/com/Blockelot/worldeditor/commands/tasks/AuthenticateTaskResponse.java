package com.Blockelot.worldeditor.commands.tasks;

import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.Util.ServerUtil;
import com.Blockelot.worldeditor.http.AuthenticateResponse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AuthenticateTaskResponse
        extends BukkitRunnable {

    private final AuthenticateResponse AuthenticateResponse;

    public AuthenticateTaskResponse(AuthenticateResponse authenticateResponse) {
        this.AuthenticateResponse = authenticateResponse;
    }

    @Override
    public void run() {
        Player player = PluginManager.Plugin.getServer().getPlayer(UUID.fromString(this.AuthenticateResponse.getUuid()));
        try {
            
            if (player != null) {
                if (!AuthenticateResponse.getMessage().equalsIgnoreCase(""))
                {
                player.sendMessage("Registration: Message: " + this.AuthenticateResponse.getMessage());    
                }
                
            }
        } catch (Exception e) {
            ServerUtil.consoleLog(e.getLocalizedMessage());
            ServerUtil.consoleLog(e.getMessage());
        }
        PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Authenticate");
        this.cancel();
    }
}
