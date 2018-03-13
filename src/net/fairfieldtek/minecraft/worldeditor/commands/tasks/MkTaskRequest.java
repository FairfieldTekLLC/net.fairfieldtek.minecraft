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
        extends BukkitRunnable {

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
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(Initialization.BaseUri + "DirMk");
            Gson gson = new Gson();
            MkRequest mkRequest = new MkRequest();
            mkRequest.setAuthToken(this.AuthToken);
            mkRequest.setCurrentDirectory(this.Path);
            mkRequest.setUuid(this.Uuid);
            mkRequest.setTargetDirectory(this.Target);
            String body = gson.toJson(mkRequest);
            StringEntity params = new StringEntity(body);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).setConnectionRequestTimeout(120000).build();
            request.setConfig(requestConfig);
            CloseableHttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            MkResponse response = gson.fromJson(json, MkResponse.class);
            response.setUuid(this.Uuid);
            new MkTaskResponse(response).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        } catch (Exception e) {
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setMessage("An Error has occurred.");
            registerResponse.setWasSuccessful(false);
            registerResponse.setUuid(this.Uuid);
            new RegisterTaskResponse(registerResponse).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        }
    }
}
