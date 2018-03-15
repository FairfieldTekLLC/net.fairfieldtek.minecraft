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
package net.fairfieldtek.minecraft.worldeditor.commands.filesystem;

import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.commands.tasks.LoadClipboardTaskRequest;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author geev
 */
public class LoadClipboard implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {
                if (args.length != 1) {
                    player.sendMessage("Usage: /fft.load <Schematic Name>");
                    return true;
                }
                if (Initialization.PlayerInfoList.get(player).getIsProcessing()) {
                    player.sendMessage("Please wait for last command to finish.");
                    return true;
                }
                Initialization.PlayerInfoList.get(player).setIsProcessing(true, "LoadClipboard");
                PlayerInfo pi = Initialization.PlayerInfoList.get(player);
                player.sendMessage(ChatColor.RED + "Requesting schematic load...");
                new LoadClipboardTaskRequest(player.getUniqueId().toString(), pi.getLastAuth(), pi.getCurrentPath(), args[0]).runTaskAsynchronously((org.bukkit.plugin.Plugin) Initialization.Plugin);
            } catch (Exception e) {
                Initialization.PlayerInfoList.get(player).setIsProcessing(false, "LoadClipboard");
                System.out.println(e.getLocalizedMessage());
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

}
