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
import net.fairfieldtek.minecraft.worldeditor.container.SchematicDef;

public class SaveClipboardTaskRequest
        extends HttpRequestor {

    private final String Uuid;
    private String AuthToken;
    private final String Path;
    private final String Filename;
    private final SchematicDef ClipSchematic;

    private boolean FirstPass = true;

    public SaveClipboardTaskRequest(String uuid, String authToken, String path, SchematicDef schematicDef, String filename) {
        this.Uuid = uuid;
        this.Filename = filename;
        this.AuthToken = authToken;
        this.Path = path;
        this.ClipSchematic = schematicDef;
    }

    @Override
    public void run() {
        try {
            int schematicId = -1;
            int total = this.ClipSchematic.Size();
            SchematicDataResponse response = new SchematicDataResponse();
            while (this.ClipSchematic.Size() > 0) {
                int maxBlocks = 25000;
                ArrayList<String> tmp = new ArrayList<>(maxBlocks);

                ListIterator<BlockDef> iter = ClipSchematic.getBlocks().listIterator();

                int blockCounter = 0;
                while (iter.hasNext()) {
                    BlockDef itm = iter.next();
                    tmp.add(itm.toXferString());
                    iter.remove();
                    if (++blockCounter < maxBlocks) {
                        continue;
                    }
                    break;
                }

                String[] blocks = new String[tmp.size()];
                tmp.toArray(blocks);

                SchematicDataRequest schematicDataRequest = new SchematicDataRequest();
                if (FirstPass) {
                    String[] blockTypePalette = new String[ClipSchematic.getBlockTypePalette().size()];
                    ClipSchematic.getBlockTypePalette().toArray(blockTypePalette);
                    schematicDataRequest.setBlockTypePalette(blockTypePalette);

                    if (ClipSchematic.getBlockColorPalette().size() > 0) {
                        String[] blockColorPalette = new String[ClipSchematic.getBlockColorPalette().size()];
                        ClipSchematic.getBlockColorPalette().toArray(blockColorPalette);
                        schematicDataRequest.setColorPalette(blockColorPalette);
                    } else {
                        schematicDataRequest.setColorPalette(new String[]{""});
                    }
                    FirstPass = false;
                } else {
                    schematicDataRequest.setColorPalette(new String[]{""});
                    schematicDataRequest.setBlockTypePalette(new String[]{""});
                }
                Gson gson = new Gson();
                schematicDataRequest.setAuthToken(this.AuthToken);
                schematicDataRequest.setCurrentDirectory(this.Path);
                schematicDataRequest.setUuid(this.Uuid);
                schematicDataRequest.setFileName(this.Filename);
                schematicDataRequest.setBlocks(blocks);
                schematicDataRequest.setSchematicId(schematicId);
                String body = gson.toJson(schematicDataRequest);
                response = gson.fromJson(
                        RequestHttp(Initialization.BaseUri + "Save", body),
                        SchematicDataResponse.class);
                response.setMessage("Saving... " + this.ClipSchematic.Size() + " blocks remaining of " + total);
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
