package com.Blockelot.worldeditor.commands.tasks;

import com.google.gson.Gson;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.container.PlayerInfo;
import com.Blockelot.worldeditor.http.CdRequest;
import com.Blockelot.worldeditor.http.CdResponse;
import com.Blockelot.worldeditor.http.RegisterResponse;

public class CdTaskRequest
        extends HttpRequestor {

    private final String Target;
    PlayerInfo PlayerInfo;

    public CdTaskRequest(PlayerInfo pi, String target) {
        PlayerInfo = pi;

        this.Target = target;
    }

    @Override
    public void run() {
        try {
            PluginManager.PlayerInfoList.get(PlayerInfo.getPlayer()).setIsProcessing(true, "Cd");
            Gson gson = new Gson();
            CdRequest cdRequest = new CdRequest();
            cdRequest.setAuth(PlayerInfo.getLastAuth());
            cdRequest.setCurrentDirectory(PlayerInfo.getCurrentPath());
            cdRequest.setUuid(PlayerInfo.getUUID());
            cdRequest.setTargetDirectory(this.Target);
            String body = gson.toJson(cdRequest);

            CdResponse response = gson.fromJson(RequestHttp(PluginManager.Config.BaseUri + "DirCd", body),
                    CdResponse.class);
            PlayerInfo.setLastAuth(response.getAuth());
            response.setUuid(PlayerInfo.getUUID());

            new CdTaskResponse(response)
                    .runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        } catch (Exception e) {
            PluginManager.PlayerInfoList.get(PlayerInfo.getPlayer()).setIsProcessing(false, "Cd");
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(PlayerInfo.getUUID());
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        }
    }
}
