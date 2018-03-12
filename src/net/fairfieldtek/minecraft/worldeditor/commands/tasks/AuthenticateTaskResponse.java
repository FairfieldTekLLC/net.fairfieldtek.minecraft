package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.AuthenticateResponse;
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
        Player player = Initialization.Plugin.getServer().getPlayer(UUID.fromString(this.AuthenticateResponse.getUuid()));
        if (player != null) {
            Initialization.PlayerInfoList.get(player).setIsProcessing(false,"Authenticate");
            player.sendMessage("Registration: Message: " + this.AuthenticateResponse.getMessage());
        }
        this.cancel();
    }
}
