/*
 * Copyright (C) 2018 geev
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
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
            req.setAuthToken(PlayerInfo.getLastAuth());
            req.setCurrentDirectory(PlayerInfo.getCurrentPath());
            req.setFileName(this.Filename);
            req.setUuid(PlayerInfo.getUUID());
            Gson gson = new Gson();
            String body = gson.toJson(req);

            SchematicDataDownloadResponse response = gson.fromJson(RequestHttp(PluginManager.BaseUri + "Load", body),
                    SchematicDataDownloadResponse.class);

            PlayerInfo.setLastAuth(response.getLastAuth());
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
