package com.Blockelot.worldeditor.commands.tasks;

import com.google.gson.Gson;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.http.SchematicDataDownloadRequest;
import com.Blockelot.worldeditor.http.SchematicDataDownloadResponse;
import com.Blockelot.worldeditor.container.PlayerInfo;

/**
 *
 * @author geev
 */
public class LoadClipboardTaskRequest
        extends HttpRequestor {

    private PlayerInfo PlayerInfo;

    private final String Filename;

    public LoadClipboardTaskRequest(PlayerInfo playerInfo, String filename) {

        this.Filename = filename;
        this.PlayerInfo = playerInfo;

    }

    @Override
    public void run() {
        try {
            SchematicDataDownloadRequest req = new SchematicDataDownloadRequest();
            req.setAuth(PlayerInfo.getLastAuth());
            req.setCurrentDirectory(PlayerInfo.getCurrentPath());
            req.setFileName(this.Filename);
            req.setUuid(PlayerInfo.getUUID());
            Gson gson = new Gson();
            String body = gson.toJson(req);

            String json = RequestHttp(PluginManager.Config.BaseUri + "Load", body);
            SchematicDataDownloadResponse response = gson.fromJson(json, SchematicDataDownloadResponse.class);
            //System.out.println(json);

            PlayerInfo.setLastAuth(response.getAuth());
            response.setUuid(PlayerInfo.getUUID());

            new LoadClipBoardTaskResponse(response).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        } catch (Exception e) {
            PlayerInfo.setIsProcessing(false, "LoadClipboard");
            SchematicDataDownloadResponse resp = new SchematicDataDownloadResponse();
            resp.setFileName(this.Filename);
            resp.setWasSuccessful(false);
            resp.setUuid(PlayerInfo.getUUID());
            resp.setMessage("Unknown Error.");
            new LoadClipBoardTaskResponse(resp).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        }
    }
}
