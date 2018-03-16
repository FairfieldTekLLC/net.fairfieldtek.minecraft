package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import com.google.gson.Gson;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.RegisterRequest;
import net.fairfieldtek.minecraft.worldeditor.http.RegisterResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RegisterTaskRequest
        extends HttpRequestor {

    private final String Uuid;
    private final String EmailAddress;

    public RegisterTaskRequest(Player player, String emailAddress) {
        this.Uuid = player.getUniqueId().toString();
        this.EmailAddress = emailAddress;
    }

    @Override
    public void run() {
        try {
            Gson gson = new Gson();
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setEmailAddress(this.EmailAddress);
            registerRequest.setUuid(this.Uuid);
            String body = gson.toJson(registerRequest);
            RegisterResponse registerResponse = gson.fromJson(
                    RequestHttp(Initialization.BaseUri + "Register", body),
                    RegisterResponse.class);
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        } catch (Exception e) {
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(this.Uuid);
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        }
    }
}
