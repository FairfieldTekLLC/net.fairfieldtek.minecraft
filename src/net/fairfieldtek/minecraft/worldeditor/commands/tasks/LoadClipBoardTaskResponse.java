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
package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.http.SchematicDataDownloadResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.fairfieldtek.minecraft.worldeditor.container.*;

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

        Player player = Initialization.Plugin.getServer().getPlayer(UUID.fromString(this.Response.getUuid()));

        if (player == null) {
            return;
        }
        Initialization.PlayerInfoList.get(player).setIsProcessing(false, "Load Clipboard");
        PlayerInfo pi = Initialization.PlayerInfoList.get(player);
        pi.setLastAuth(this.Response.getLastAuth());
        pi.SelectEnd = null;
        pi.SelectStart = null;

        System.out.println("Loading Clip Schematic");
        pi.ClipSchematic.LoadResponse(Response);
        System.out.println("Done Loading Clip Schematic");

        if (!this.Response.getWasSuccessful()) {
            player.sendMessage(ChatColor.YELLOW + "File not loaded.");
            player.sendMessage(ChatColor.RED + this.Response.getMessage());
        } else {
            player.sendMessage(ChatColor.GREEN + this.Response.getMessage());
        }
        this.cancel();
    }
}
