package com.Blockelot.worldeditor.commands.tasks;

import com.google.gson.Gson;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.http.RegisterRequest;
import com.Blockelot.worldeditor.http.RegisterResponse;
import org.bukkit.entity.Player;

public class RegisterTaskRequest
        extends HttpRequestor {

    private final String Uuid;
    private final String EmailAddress;
    private Player Player;

    public RegisterTaskRequest(Player player, String emailAddress) {
        this.Uuid = player.getUniqueId().toString();
        this.EmailAddress = emailAddress;
        Player=player;
    }

    @Override
    public void run() {
        try {
            PluginManager.PlayerInfoList.get(Player).setIsProcessing(true, "Register");
            Gson gson = new Gson();
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setEmailAddress(this.EmailAddress);
            registerRequest.setUuid(this.Uuid);
            String body = gson.toJson(registerRequest);
            RegisterResponse registerResponse = gson.fromJson(RequestHttp(PluginManager.BaseUri + "Register", body),
                    RegisterResponse.class);
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        } catch (Exception e) {
            PluginManager.PlayerInfoList.get(Player).setIsProcessing(false, "Register");
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(this.Uuid);
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        }
    }
}
