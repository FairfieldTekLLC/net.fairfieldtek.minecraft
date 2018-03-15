package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import net.fairfieldtek.minecraft.worldeditor.http.LoginResponse;
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
        if (this.LoginResponse == null || this.LoginResponse.getUuid() == null) {
            return;
        }
        Player player = Initialization.Plugin.getServer().getPlayer(UUID.fromString(this.LoginResponse.getUuid()));
        if (player != null) {
            Initialization.PlayerInfoList.get(player).setIsProcessing(false, "Login");
            PlayerInfo pi = Initialization.PlayerInfoList.get(player);
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
        this.cancel();
    }
}
