package com.Blockelot.Util;

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

    public static String[] playerInventoryToBase64(Player player) throws IllegalStateException {
        //get the main content part, this doesn't return the armor
        String content = itemStackArrayToBase64(player.getInventory().getContents());
        String armor = itemStackArrayToBase64(player.getInventory().getArmorContents());
        String ender = itemStackArrayToBase64(player.getEnderChest().getContents());

        return new String[]{content, armor, ender};
    }

    public static String toBase64(org.bukkit.inventory.Inventory inventory) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
                dataOutput.writeInt(inventory.getSize());
                dataOutput.writeObject(inventory.getType());
                for (int i = 0; i < inventory.getSize(); i++) {
                    dataOutput.writeObject(inventory.getItem(i));
                }
            }
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static org.bukkit.inventory.Inventory fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            org.bukkit.inventory.Inventory inventory;
            try (BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
                InventoryType type = (InventoryType) dataInput.readObject();
                inventory = Bukkit.getServer().createInventory(null, type);
                for (int i = 0; i < inventory.getSize(); i++) {
                    inventory.setItem(i, (ItemStack) dataInput.readObject());
                }
            }
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static ItemStack[] itemStackfromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            ArrayList<ItemStack> itms;
            try (BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
                InventoryType type = (InventoryType) dataInput.readObject();
                org.bukkit.inventory.Inventory inventory = Bukkit.getServer().createInventory(null, type);
                // Read the serialized inventory
                itms = new ArrayList<>();
                for (int i = 0; i < inventory.getSize(); i++) {
                    itms.add((ItemStack) dataInput.readObject());
                }
            }
            return itms.toArray(new ItemStack[itms.size()]);
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            ItemStack[] items;
            try (BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
                items = new ItemStack[dataInput.readInt()];
                for (int i = 0; i < items.length; i++) {
                    items[i] = (ItemStack) dataInput.readObject();
                }
            }
            return items;
        } catch (ClassNotFoundException e) {
            Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, e);
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
                dataOutput.writeInt(items.length);
                for (ItemStack item : items) {
                    dataOutput.writeObject(item);
                }
            }
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (IOException e) {
            Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, e);
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

}
