package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import com.google.gson.Gson;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.LsRequest;
import net.fairfieldtek.minecraft.worldeditor.http.LsResponse;
import net.fairfieldtek.minecraft.worldeditor.http.RegisterResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class LsTaskRequest
        extends HttpRequestor {

    private final String Uuid;
    private final String AuthToken;
    private final String Path;

    public LsTaskRequest(String uuid, String authToken, String path) {
        this.Uuid = uuid;
        this.AuthToken = authToken;
        this.Path = path;
    }

    @Override
    public void run() {
        try {
            Gson gson = new Gson();
            LsRequest lsRequest = new LsRequest();
            lsRequest.setAuthToken(this.AuthToken);
            lsRequest.setCurrentDirectory(this.Path);
            lsRequest.setUuid(this.Uuid);
            String body = gson.toJson(lsRequest);
            LsResponse response = gson.fromJson(
                    RequestHttp(Initialization.BaseUri + "DirLs", body),
                    LsResponse.class);
            response.setUuid(this.Uuid);
            new LsTaskResponse(response).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        } catch (Exception e) {
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(this.Uuid);
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        }
    }
}
