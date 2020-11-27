package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import com.google.gson.Gson;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.LoginRequest;
import net.fairfieldtek.minecraft.worldeditor.http.LoginResponse;
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

public class LoginTaskRequest
        extends HttpRequestor {

    private final String Uuid;
    private final String AuthToken;

    public LoginTaskRequest(Player player, String authToken) {
        this.Uuid = player.getUniqueId().toString();
        this.AuthToken = authToken;
    }

    @Override
    public void run() {
        try {
            Gson gson = new Gson();
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUuid(this.Uuid);
            loginRequest.setAuthToken(this.AuthToken);
            String body = gson.toJson(loginRequest);
            LoginResponse response = gson.fromJson(
                    RequestHttp(Initialization.BaseUri + "Login", body),
                    LoginResponse.class);
            response.setUuid(this.Uuid);
            new LoginTaskResponse(response).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        } catch (Exception e) {
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(this.Uuid);
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        }
    }
}
