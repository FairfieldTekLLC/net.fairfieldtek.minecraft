package com.Blockelot.worldeditor.commands.tasks;

import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.http.RegisterResponse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RegisterTaskResponse
        extends BukkitRunnable {

    private final RegisterResponse RegisterResponse;

    public RegisterTaskResponse(RegisterResponse registerResponse) {
        this.RegisterResponse = registerResponse;
    }

    @Override
    public void run() {

        Player player = PluginManager.Plugin.getServer().getPlayer(UUID.fromString(this.RegisterResponse.getUuid()));

        if (player != null) {
            PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Register");
            player.sendMessage("Registration: Message: " + this.RegisterResponse.getMessage());
        }
        this.cancel();
    }
}
