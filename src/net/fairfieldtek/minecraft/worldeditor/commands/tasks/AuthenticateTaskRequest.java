package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import com.google.gson.Gson;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.AuthenticateRequest;
import net.fairfieldtek.minecraft.worldeditor.http.AuthenticateResponse;
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

public class AuthenticateTaskRequest
        extends BukkitRunnable {

    private final String Uuid;

    public AuthenticateTaskRequest(Player player) {
        this.Uuid = player.getUniqueId().toString();
    }

    @Override
    public void run() {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(Initialization.BaseUri + "Authenticate");
            Gson gson = new Gson();
            AuthenticateRequest authenticateRequest = new AuthenticateRequest();
            authenticateRequest.setUuid(this.Uuid);
            String body = gson.toJson(authenticateRequest);
            StringEntity params = new StringEntity(body);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).setConnectionRequestTimeout(120000).build();
            request.setConfig(requestConfig);
            CloseableHttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            AuthenticateResponse response = gson.fromJson(json, AuthenticateResponse.class);
            new AuthenticateTaskResponse(response).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        } catch (Exception e) {
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(this.Uuid);
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        }
    }
}
