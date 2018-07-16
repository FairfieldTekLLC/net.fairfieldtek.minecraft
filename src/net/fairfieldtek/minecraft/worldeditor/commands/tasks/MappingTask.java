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
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.Stack;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Material;

/**
 *
 * @author geev
 */
public class MappingTask extends BukkitRunnable {

    UUID PlayerId;

    int StartX;
    int StartZ;
    int EndX;
    int EndZ;
    int CurX;
    int CurZ;

    boolean gennedData = false;

    public MappingTask(UUID playerId, int numOfChunks) {
        this.PlayerId = playerId;
        Player player = Initialization.Plugin.getServer().getPlayer(this.PlayerId);
        StartX = CurX = player.getLocation().getBlockX() - (numOfChunks * 16);
        StartZ = CurZ = player.getLocation().getBlockZ() - (numOfChunks * 16);
        EndX = player.getLocation().getBlockX() + (numOfChunks * 16);
        EndZ = player.getLocation().getBlockZ() + (numOfChunks * 16);
    }

    @Override
    public void run() {
        Player player = Initialization.Plugin.getServer().getPlayer(this.PlayerId);
        int counter = 0;
        try {

            player.sendMessage("Mapping...");
            World world = player.getWorld();
            while (CurX < EndX) {
                while (CurZ < EndZ) {
                    Block b = world.getBlockAt(CurX, 1, CurZ);
                    if (!b.getChunk().isLoaded()) {
                        b.getChunk().load();
                    }
                    b.setType(Material.BEDROCK);
                    CurZ += 15;
                    counter++;
                    if (counter > 100) {
                        player.sendMessage("Finished up at: " + CurX + ", 1, " + CurZ);
                        return;
                    }
                }
                CurZ = StartZ;
                CurX += 15;
            }

        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("I crashed. " + e.getMessage());

        }
        player.sendMessage("Mapping Finished...");
        this.cancel();

    }

}
