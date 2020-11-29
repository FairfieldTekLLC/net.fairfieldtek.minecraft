package com.Blockelot.worldeditor.commands.tasks;

import com.google.gson.Gson;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.http.AuthenticateRequest;
import com.Blockelot.worldeditor.http.AuthenticateResponse;
import com.Blockelot.worldeditor.http.RegisterResponse;
import org.bukkit.entity.Player;

public class AuthenticateTaskRequest
        extends HttpRequestor {

    private final String Uuid;
    private final Player Player;

    public AuthenticateTaskRequest(Player player) {
        this.Uuid = player.getUniqueId().toString();
        Player=player;
    }

    @Override
    public void run() {
        try {
            PluginManager.PlayerInfoList.get(Player).setIsProcessing(true, "Authenticate");
            Gson gson = new Gson();
            AuthenticateRequest authenticateRequest = new AuthenticateRequest();
            authenticateRequest.setUuid(this.Uuid);
            String body = gson.toJson(authenticateRequest);
            String hr = RequestHttp(PluginManager.BaseUri + "Authenticate", body);
            AuthenticateResponse response = gson.fromJson(
                    hr,
                    AuthenticateResponse.class);
            new AuthenticateTaskResponse(response).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        } catch (Exception e) {
            PluginManager.PlayerInfoList.get(Player).setIsProcessing(false, "Authenticate");
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(this.Uuid);
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        }
    }
}
