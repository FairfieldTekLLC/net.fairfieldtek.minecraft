package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import com.google.gson.Gson;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.CdRequest;
import net.fairfieldtek.minecraft.worldeditor.http.CdResponse;
import net.fairfieldtek.minecraft.worldeditor.http.RegisterResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class CdTaskRequest
        extends HttpRequestor {

    private final String Uuid;
    private final String AuthToken;
    private final String Path;
    private final String Target;

    public CdTaskRequest(String uuid, String authToken, String path, String target) {
        this.Uuid = uuid;
        this.AuthToken = authToken;
        this.Path = path;
        this.Target = target;
    }

    @Override
    public void run() {
        try {
            Gson gson = new Gson();
            CdRequest cdRequest = new CdRequest();
            cdRequest.setAuthToken(this.AuthToken);
            cdRequest.setCurrentDirectory(this.Path);
            cdRequest.setUuid(this.Uuid);
            cdRequest.setTargetDirectory(this.Target);
            String body = gson.toJson(cdRequest);
            
            CdResponse response = gson.fromJson(
                    RequestHttp(Initialization.BaseUri + "DirCd", body), 
                    CdResponse.class);
            response.setUuid(this.Uuid);
            new CdTaskResponse(response)
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
