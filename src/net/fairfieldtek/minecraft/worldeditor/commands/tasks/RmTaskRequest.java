package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import com.google.gson.Gson;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.RegisterResponse;
import net.fairfieldtek.minecraft.worldeditor.http.RmRequest;
import net.fairfieldtek.minecraft.worldeditor.http.RmResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class RmTaskRequest
        extends HttpRequestor {

    private final String Uuid;
    private final String AuthToken;
    private final String Path;
    private final String Target;

    public RmTaskRequest(String uuid, String authToken, String path, String target) {
        this.Uuid = uuid;
        this.AuthToken = authToken;
        this.Path = path;
        this.Target = target;
    }

    @Override
    public void run() {
        try {
            Gson gson = new Gson();
            RmRequest rmRequest = new RmRequest();
            rmRequest.setAuthToken(this.AuthToken);
            rmRequest.setCurrentDirectory(this.Path);
            rmRequest.setUuid(this.Uuid);
            rmRequest.setTargetDirectory(this.Target);
            String body = gson.toJson(rmRequest);
            RmResponse response = gson.fromJson(
                    RequestHttp(Initialization.BaseUri + "DirRm", body),
                    RmResponse.class);
            response.setUuid(this.Uuid);
            new RmTaskResponse(response).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        } catch (Exception e) {
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(this.Uuid);
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        }
    }
}
