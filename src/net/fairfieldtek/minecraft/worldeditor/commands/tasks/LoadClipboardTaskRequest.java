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
package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import com.google.gson.Gson;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.SchematicDataDownloadRequest;
import net.fairfieldtek.minecraft.worldeditor.http.SchematicDataDownloadResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author geev
 */
public class LoadClipboardTaskRequest
        extends BukkitRunnable {

    private final String Uuid;
    private String AuthToken;
    private final String Path;
    private final String Filename;

    public LoadClipboardTaskRequest(String uuid, String authToken, String path, String filename) {
        this.Uuid = uuid;
        this.Filename = filename;
        this.AuthToken = authToken;
        this.Path = path;

    }

    @Override
    public void run() {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(Initialization.BaseUri + "Load");
            Gson gson = new Gson();

            SchematicDataDownloadRequest req = new SchematicDataDownloadRequest();
            req.setAuthToken(this.AuthToken);
            req.setCurrentDirectory(this.Path);
            req.setFileName(this.Filename);
            req.setUuid(this.Uuid);

            String body = gson.toJson(req);
            StringEntity params = new StringEntity(body);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).setConnectionRequestTimeout(120000).build();
            request.setConfig(requestConfig);
            CloseableHttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            SchematicDataDownloadResponse response = gson.fromJson(json, SchematicDataDownloadResponse.class);
            response.setUuid(this.Uuid);

            new LoadClipBoardTaskResponse(response).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        } catch (Exception e) {
            SchematicDataDownloadResponse resp = new SchematicDataDownloadResponse();
            resp.setFileName(this.Filename);
            resp.setWasSuccessful(false);
            resp.setUuid(this.Uuid);
            resp.setMessage("Unknown Error.");
            new LoadClipBoardTaskResponse(resp).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        }
    }
}
