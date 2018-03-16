package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import com.google.gson.Gson;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.MkRequest;
import net.fairfieldtek.minecraft.worldeditor.http.MkResponse;
import net.fairfieldtek.minecraft.worldeditor.http.RegisterResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class MkTaskRequest
        extends HttpRequestor {

    private final String Uuid;
    private final String AuthToken;
    private final String Path;
    private final String Target;

    public MkTaskRequest(String uuid, String authToken, String path, String target) {
        this.Uuid = uuid;
        this.AuthToken = authToken;
        this.Path = path;
        this.Target = target;
    }

    @Override
    public void run() {
        try {
            Gson gson = new Gson();
            MkRequest mkRequest = new MkRequest();
            mkRequest.setAuthToken(this.AuthToken);
            mkRequest.setCurrentDirectory(this.Path);
            mkRequest.setUuid(this.Uuid);
            mkRequest.setTargetDirectory(this.Target);
            String body = gson.toJson(mkRequest);
            MkResponse response = gson.fromJson(
                    RequestHttp(Initialization.BaseUri + "DirMk", body),
                    MkResponse.class);
            response.setUuid(this.Uuid);
            new MkTaskResponse(response)
                    .runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        } catch (Exception e) {
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(this.Uuid);
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        }
    }
}
