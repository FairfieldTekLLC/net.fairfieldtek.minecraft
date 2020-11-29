/*
 * Copyright (C) 2020 geev
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
package com.Blockelot.Util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import java.io.*;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.*;
import org.bukkit.event.inventory.InventoryType;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.Blockelot.worldeditor.container.BlockInfo;
import org.bukkit.entity.Player;

/**
 *
 * @author geev
 */
public class Inventory {

    /**
     * Converts the player inventory to a String array of Base64 strings. First
     * string is the content and second string is the armor.
     *
     * @param playerInventory to turn into an array of strings.
     * @return Array of strings: [ main content, armor content ]
     * @throws IllegalStateException
     */
    public static String[] playerInventoryToBase64(Player player) throws IllegalStateException {
        //get the main content part, this doesn't return the armor
        String content = itemStackArrayToBase64(player.getInventory().getContents());
        String armor = itemStackArrayToBase64(player.getInventory().getArmorContents());
        String ender = itemStackArrayToBase64(player.getEnderChest().getContents());

        return new String[]{content, armor, ender};
    }

    /**
     * A method to serialize an inventory to Base64 string.
     *
     * <p />
     *
     * Special thanks to Comphenix in the Bukkit forums or also known as aadnk
     * on GitHub.
     *
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param inventory to serialize
     * @return Base64 string of the provided inventory
     * @throws IllegalStateException
     */
    public static String toBase64(org.bukkit.inventory.Inventory inventory) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());
            dataOutput.writeObject(inventory.getType());

            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     *
     * A method to get an {@link Inventory} from an encoded, Base64, string.
     *
     * <p />
     *
     * Special thanks to Comphenix in the Bukkit forums or also known as aadnk
     * on GitHub.
     *
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param data Base64 string of data containing an inventory.
     * @return Inventory created from the Base64 string.
     * @throws IOException
     */
    public static org.bukkit.inventory.Inventory fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
//            org.bukkit.inventory.Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());
            InventoryType type = (InventoryType) dataInput.readObject();
            org.bukkit.inventory.Inventory inventory = Bukkit.getServer().createInventory(null, type);

            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static ItemStack[] itemStackfromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
//            org.bukkit.inventory.Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());
            InventoryType type = (InventoryType) dataInput.readObject();
            org.bukkit.inventory.Inventory inventory = Bukkit.getServer().createInventory(null, type);

            // Read the serialized inventory
            ArrayList<ItemStack> itms = new ArrayList<ItemStack>();

            for (int i = 0; i < inventory.getSize(); i++) {
                itms.add((ItemStack) dataInput.readObject());
                //inventory.setItem(i, (ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return itms.toArray(new ItemStack[itms.size()]);
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * Gets an array of ItemStacks from Base64 string.
     *
     * <p />
     *
     * Base off of {@link #fromBase64(String)}.
     *
     * @param data Base64 string to convert to ItemStack array.
     * @return ItemStack array created from the Base64 string.
     * @throws IOException
     */
    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, e);
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     *
     * A method to serialize an {@link ItemStack} array to Base64 String.
     *
     * <p />
     *
     * Based off of {@link #toBase64(Inventory)}.
     *
     * @param items to turn into a Base64 String.
     * @return Base64 string of the items.
     * @throws IllegalStateException
     */
    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (int i = 0; i < items.length; i++) {

                dataOutput.writeObject(items[i]);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, e);
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

}
