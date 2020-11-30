package com.Blockelot.worldeditor.commands.tasks;

import com.google.gson.Gson;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.http.LsRequest;
import com.Blockelot.worldeditor.http.LsResponse;
import com.Blockelot.worldeditor.http.RegisterResponse;
import com.Blockelot.worldeditor.container.PlayerInfo;

public class LsTaskRequest
        extends HttpRequestor {

//    private final String Uuid;
//    private final String AuthToken;
//    private final String Path;
    private PlayerInfo PlayerInfo;

    public LsTaskRequest(PlayerInfo pi) {
        PlayerInfo = pi;
    }

    @Override
    public void run() {
        try {
            PlayerInfo.setIsProcessing(true, "LS");
            Gson gson = new Gson();
            LsRequest lsRequest = new LsRequest();
            lsRequest.setAuth(PlayerInfo.getLastAuth());
            lsRequest.setCurrentDirectory(PlayerInfo.getCurrentPath());
            lsRequest.setUuid(PlayerInfo.getUUID());
            String body = gson.toJson(lsRequest);
            LsResponse response = gson.fromJson(RequestHttp(PluginManager.Config.BaseUri + "DirLs", body),
                    LsResponse.class);
            PlayerInfo.setLastAuth(response.getAuth());
            response.setUuid(PlayerInfo.getUUID());
            new LsTaskResponse(response).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        } catch (Exception e) {
            PlayerInfo.setIsProcessing(false, "LS");
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(PlayerInfo.getUUID());
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        }
    }
}
