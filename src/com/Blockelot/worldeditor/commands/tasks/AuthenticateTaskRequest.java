package com.Blockelot.worldeditor.commands.tasks;

import com.google.gson.Gson;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.http.AuthenticateRequest;
import com.Blockelot.worldeditor.http.AuthenticateResponse;
import com.Blockelot.worldeditor.http.RegisterResponse;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.entity.Player;

public class AuthenticateTaskRequest
        extends HttpRequestor {
    
    PlayerInfo PlayerInfo;
    
    public AuthenticateTaskRequest(PlayerInfo pi) {
        this.PlayerInfo = pi;
    }
    
    @Override
    public void run() {
        try {
            PlayerInfo.setIsProcessing(true, "Authenticate");
            Gson gson = new Gson();
            AuthenticateRequest authenticateRequest = new AuthenticateRequest();            
            authenticateRequest.setUuid(PlayerInfo.getUUID());            
            authenticateRequest.SetWid(PluginManager.getWorldId());
            
            String hr = RequestHttp(PluginManager.Config.BaseUri + "Authenticate", gson.toJson(authenticateRequest));
            AuthenticateResponse response = gson.fromJson(hr, AuthenticateResponse.class);      
            //This shouldn't be here, cause really it shouldn't be.
            PlayerInfo.setLastAuth(response.getAuth());
            
            response.setUuid(PlayerInfo.getUUID());
            
            new AuthenticateTaskResponse(response).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        } catch (Exception e) {
            PlayerInfo.setIsProcessing(false, "Authenticate");
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(PlayerInfo.getUUID());
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        }
    }
}
