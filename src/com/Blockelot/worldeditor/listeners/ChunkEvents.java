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
package com.Blockelot.worldeditor.listeners;

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
        System.out.println("Chunk Unloading . . .");
        // Bukkit.getConsoleSender().sendMessage("Chunk Unload " + event.getChunk().getX() + ", " + event.getChunk().getZ());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onChunkloadEvent(ChunkLoadEvent event) {
        System.out.println("Chunk loading . . .");
        //Bukkit.getConsoleSender().sendMessage("Chunk Unload " + event.getChunk().getX() + ", " + event.getChunk().getZ());
    }
}