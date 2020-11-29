package com.Blockelot.worldeditor.commands.tasks;

import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.container.PlayerInfo;
import com.Blockelot.worldeditor.http.LoginResponse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LoginTaskResponse
        extends BukkitRunnable {

    private final LoginResponse LoginResponse;

    public LoginTaskResponse(LoginResponse loginResponse) {
        this.LoginResponse = loginResponse;
    }

    @Override
    public void run() {
        Player player = PluginManager.Plugin.getServer().getPlayer(UUID.fromString(this.LoginResponse.getUuid()));

        try {
            if (this.LoginResponse == null || this.LoginResponse.getUuid() == null) {
                return;
            }

            if (player != null) {
                PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Login");
                PlayerInfo pi = PluginManager.PlayerInfoList.get(player);
                if (this.LoginResponse.getIsAuthorized()) {
                    pi.setLastAuth(this.LoginResponse.getLastAuth());
                    pi.setCurrentPath(this.LoginResponse.getCurrentPath());
                } else {
                    pi.setLastAuth("");
                    pi.setCurrentPath("");
                }
                player.sendMessage("Login: Message: " + this.LoginResponse.getMessage());
                player.sendMessage("Current Remote Directory: " + pi.getCurrentPath());
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
        }
        PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Login");
        this.cancel();
    }
}
