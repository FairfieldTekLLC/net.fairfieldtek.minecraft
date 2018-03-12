package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.RegisterResponse;
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
        Player player = Initialization.Plugin.getServer().getPlayer(UUID.fromString(this.RegisterResponse.getUuid()));
        if (player != null) {
            player.sendMessage("Registration: Message: " + this.RegisterResponse.getMessage());
        }
        this.cancel();
    }
}
