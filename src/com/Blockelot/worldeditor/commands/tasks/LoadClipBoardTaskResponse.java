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
package com.Blockelot.worldeditor.commands.tasks;

import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.http.SchematicDataDownloadResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.Blockelot.worldeditor.container.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author geev
 */
public class LoadClipBoardTaskResponse
        extends BukkitRunnable {

    public SchematicDataDownloadResponse Response;

    public LoadClipBoardTaskResponse(SchematicDataDownloadResponse response) {
        this.Response = response;
    }

    @Override
    public void run() {
       // try {
            Player player = PluginManager.Plugin.getServer().getPlayer(UUID.fromString(this.Response.getUuid()));
            if (player == null) {
                this.cancel();
                return;
            }
            PluginManager.PlayerInfoList.get(player).setIsProcessing(true, "Load Clipboard");            
            PlayerInfo pi = PluginManager.PlayerInfoList.get(player);
            pi.setLastAuth(this.Response.getAuth());
            pi.SelectEnd = null;
            pi.SelectStart = null;
            
        try {
            // System.out.println("inv pallet? " + Response.getBlockInvePalette().length);
            
            pi.ClipSchematic.LoadResponse(Response);
        } catch (Exception ex) {
            player.sendMessage(ChatColor.YELLOW + ex.getMessage());
            Logger.getLogger(LoadClipBoardTaskResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
            if (!this.Response.getWasSuccessful()) {
                player.sendMessage(ChatColor.YELLOW + "File not loaded.");
                player.sendMessage(ChatColor.RED + this.Response.getMessage());
            } else {
                player.sendMessage(ChatColor.GREEN + this.Response.getMessage());
            }
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//            System.out.println(e.getMessage());
//        }
        PluginManager.PlayerInfoList.get(PluginManager.Plugin.getServer().getPlayer(UUID.fromString(this.Response.getUuid()))).setIsProcessing(false, "Load Clipboard");
        this.cancel();
    }
}
