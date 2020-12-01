package com.Blockelot.worldeditor.listeners;

import com.Blockelot.Util.ServerUtil;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

/**
 *
 * @author geev
 */
public class ChunkEvents implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onChunkUnloadEvent(ChunkUnloadEvent event) {
        ServerUtil.consoleLog("Chunk Unloading . . .");
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onChunkloadEvent(ChunkLoadEvent event) {
        ServerUtil.consoleLog("Chunk loading . . .");
    }
}
