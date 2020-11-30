package com.Blockelot.worldeditor.commands.tasks;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.ListIterator;
import com.Blockelot.PluginManager;
import com.Blockelot.Util.ServerUtil;
import com.Blockelot.worldeditor.container.BlockInfo;
import com.Blockelot.worldeditor.container.PaletteEntry;
import com.Blockelot.worldeditor.http.SchematicDataRequest;
import com.Blockelot.worldeditor.http.SchematicDataResponse;
import com.Blockelot.worldeditor.container.BlockCollection;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.entity.Player;

public class SaveClipboardTaskRequest
        extends HttpRequestor {

    
    
    private final String Filename;    
    private PlayerInfo PlayerInfo;
    private boolean FirstPass = true;
    private BlockCollection WorkArea;

    public SaveClipboardTaskRequest(PlayerInfo pi,  String filename) {
        PlayerInfo = pi;
        WorkArea=pi.ClipSchematic.Clone();
        this.Filename = filename;
    }

    @Override
    public void run() {
        try {
            int schematicId = -1;
            int total = WorkArea.Size();
            SchematicDataResponse response = new SchematicDataResponse();
            while (WorkArea.Size() > 0) {
                int maxBlocks = 10000;
                ArrayList<String> tmp = new ArrayList<>(maxBlocks);
                ListIterator<BlockInfo> iter = WorkArea.getBlocks().listIterator();
                int blockCounter = 0;
                while (iter.hasNext()) {
                    BlockInfo itm = iter.next();
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
                    schematicDataRequest.setBlockTypePalette(WorkArea.GetBlockTypePalette());
                    schematicDataRequest.setBlockDataPalette(WorkArea.GetBlockDataPalette());
                    schematicDataRequest.setBlockInvePalette(WorkArea.GetBlockInventoryPalette());
                    FirstPass = false;
                } else {
                    PaletteEntry[] e = new PaletteEntry[1];
                    e[0] = new PaletteEntry();
                    e[0].setId(0);
                    e[0].setValue("");
                    schematicDataRequest.setBlockDataPalette(e);
                    schematicDataRequest.setBlockTypePalette(e);
                    schematicDataRequest.setBlockInvePalette(e);
                }
                Gson gson = new Gson();
                
                
                
                schematicDataRequest.setAuth(PlayerInfo.getLastAuth());
                schematicDataRequest.setCurrentDirectory(PlayerInfo.getCurrentPath());
                schematicDataRequest.setUuid(PlayerInfo.getUUID());
                schematicDataRequest.setFileName(this.Filename);

                schematicDataRequest.setBlocks(blocks);

                schematicDataRequest.setSchematicId(schematicId);
                String body = gson.toJson(schematicDataRequest);
                response = gson.fromJson(RequestHttp(PluginManager.Config.BaseUri + "Save", body), SchematicDataResponse.class);
                
                response.setMessage("Saving... " + WorkArea.Size() + " blocks remaining of " + total);                
                
                PlayerInfo.setLastAuth(response.getAuth());
                
                schematicId = response.getSchematicId();
                
                response.setFinal(false);
                
                new SaveClipboardTaskResponse(response).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
                
                if (response.getWasSuccessful()) {
                    continue;
                }
                this.cancel();
            }
            response.setFinal(true);
            new SaveClipboardTaskResponse(response).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
            this.cancel();

        } catch (Exception e) {
            ServerUtil.consoleLog("ERROR");
            ServerUtil.consoleLog(e.getLocalizedMessage());
            ServerUtil.consoleLog(e.getMessage());
            ServerUtil.consoleLog(e);
            
            SchematicDataResponse response = new SchematicDataResponse();
            response.setMessage("An Error has occurred. " + e.getMessage());
            response.setWasSuccessful(false);
            response.setUuid(PlayerInfo.getUUID());
            this.cancel();
            new SaveClipboardTaskResponse(response).runTask((org.bukkit.plugin.Plugin) PluginManager.Plugin);
        }
    }
}
