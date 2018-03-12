package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.ListIterator;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.container.BlockDef;
import net.fairfieldtek.minecraft.worldeditor.http.SchematicDataRequest;
import net.fairfieldtek.minecraft.worldeditor.http.SchematicDataResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveClipboardTaskRequest
        extends BukkitRunnable {

    private final String Uuid;
    private String AuthToken;
    private final String Path;
    private final String Filename;
    private final ArrayList<BlockDef> Clipboard;

    public SaveClipboardTaskRequest(String uuid, String authToken, String path, ArrayList<BlockDef> ClipBoard, String filename) {
        this.Uuid = uuid;
        this.Filename = filename;
        this.AuthToken = authToken;
        this.Path = path;
        this.Clipboard = ClipBoard;
    }

    @Override
    public void run() {
        try {
            int schematicId = -1;
            int total = this.Clipboard.size();
            SchematicDataResponse response = new SchematicDataResponse();
            while (this.Clipboard.size() > 0) {
                int maxBlocks = 25000;
                ArrayList<BlockDef> tmp = new ArrayList<>(maxBlocks);
                ListIterator<BlockDef> iter = this.Clipboard.listIterator();
                int blockCounter = 0;
                while (iter.hasNext()) {
                    BlockDef itm = iter.next();
                    tmp.add(itm);
                    iter.remove();
                    if (++blockCounter < maxBlocks) {
                        continue;
                    }
                    break;
                }
                BlockDef[] blocks = new BlockDef[tmp.size()];
                tmp.toArray(blocks);
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost request = new HttpPost(Initialization.BaseUri + "Save");
                Gson gson = new Gson();
                SchematicDataRequest schematicDataRequest = new SchematicDataRequest();
                schematicDataRequest.setAuthToken(this.AuthToken);
                schematicDataRequest.setCurrentDirectory(this.Path);
                schematicDataRequest.setUuid(this.Uuid);
                schematicDataRequest.setFileName(this.Filename);
                schematicDataRequest.setBlocks(blocks);
                schematicDataRequest.setSchematicId(schematicId);
                String body = gson.toJson(schematicDataRequest);
                StringEntity params = new StringEntity(body);
                request.addHeader("content-type", "application/json");
                request.setEntity(params);
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(300000).setConnectTimeout(300000).setConnectionRequestTimeout(300000).build();
                request.setConfig(requestConfig);
                CloseableHttpResponse result = httpClient.execute(request);
                String json = EntityUtils.toString(result.getEntity(), "UTF-8");
                response = gson.fromJson(json, SchematicDataResponse.class);
                response.setMessage("Saving... " + this.Clipboard.size() + " blocks remaining of " + total);
                this.AuthToken = response.getLastAuth();
                schematicId = response.getSchematicId();
                response.setFinal(false);
                new SaveClipboardTaskResponse(response).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
                if (response.getWasSuccessful()) {
                    continue;
                }
                this.cancel();
            }
            response.setMessage("Finished Saving Schematic.");
            response.setFinal(true);
            new SaveClipboardTaskResponse(response).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
            this.cancel();
        } catch (Exception e) {
            System.out.println("ERROR");
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
            SchematicDataResponse response = new SchematicDataResponse();
            response.setMessage("An Error has occurred. " + e.getMessage());
            response.setWasSuccessful(false);
            response.setUuid(this.Uuid);
            new SaveClipboardTaskResponse(response).runTask((org.bukkit.plugin.Plugin) Initialization.Plugin);
        }
    }
}
