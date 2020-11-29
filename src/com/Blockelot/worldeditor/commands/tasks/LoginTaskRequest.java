package com.Blockelot.worldeditor.commands.tasks;

import com.google.gson.Gson;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.http.LoginRequest;
import com.Blockelot.worldeditor.http.LoginResponse;
import com.Blockelot.worldeditor.http.RegisterResponse;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.entity.Player;

public class LoginTaskRequest
        extends HttpRequestor {
    private PlayerInfo PlayerInfo;
    private String Pass;
    public LoginTaskRequest(PlayerInfo pi,String pass) {
        PlayerInfo = pi;
        Pass=pass;
    }

    @Override
    public void run() {
        try {
            PlayerInfo.setIsProcessing(true, "Login");
            Gson gson = new Gson();
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUuid(PlayerInfo.getUUID());
            loginRequest.setAuthToken(Pass);
            String body = gson.toJson(loginRequest);
            LoginResponse response = gson.fromJson(RequestHttp(PluginManager.BaseUri + "Login", body),
                    LoginResponse.class);
            PlayerInfo.setLastAuth(response.getLastAuth());
            response.setUuid(PlayerInfo.getUUID());
            new LoginTaskResponse(response).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        } catch (Exception e) {
            PlayerInfo.setIsProcessing(false, "Login");
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(PlayerInfo.getUUID());
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        }
    }
}
