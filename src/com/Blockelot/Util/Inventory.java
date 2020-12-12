//End-User License Agreement (EULA) of Blockelot
//
//This End-User License Agreement ("EULA") is a legal agreement between you and Fairfield Tek L.L.C.. Our EULA was created by EULA Template for Blockelot.
//
//This EULA agreement governs your acquisition and use of our Blockelot software ("Software") directly from Fairfield Tek L.L.C. or indirectly through a Fairfield Tek L.L.C. authorized reseller or distributor (a "Reseller"). Our Privacy Policy was created by the Privacy Policy Generator.
//
//Please read this EULA agreement carefully before completing the installation process and using the Blockelot software. It provides a license to use the Blockelot software and contains warranty information and liability disclaimers.
//
//If you register for a free trial of the Blockelot software, this EULA agreement will also govern that trial. By clicking "accept" or installing and/or using the Blockelot software, you are confirming your acceptance of the Software and agreeing to become bound by the terms of this EULA agreement.
//
//If you are entering into this EULA agreement on behalf of a company or other legal entity, you represent that you have the authority to bind such entity and its affiliates to these terms and conditions. If you do not have such authority or if you do not agree with the terms and conditions of this EULA agreement, do not install or use the Software, and you must not accept this EULA agreement.
//
//This EULA agreement shall apply only to the Software supplied by Fairfield Tek L.L.C. herewith regardless of whether other software is referred to or described herein. The terms also apply to any Fairfield Tek L.L.C. updates, supplements, Internet-based services, and support services for the Software, unless other terms accompany those items on delivery. If so, those terms apply.
//
//License Grant
//Fairfield Tek L.L.C. hereby grants you a personal, non-transferable, non-exclusive licence to use the Blockelot software on your devices 
//in accordance with the terms of this EULA agreement.
//
//You are permitted to load the Blockelot software (for example a PC, laptop, mobile or tablet) under your control. You are responsible
//for ensuring your device meets the minimum requirements of the Blockelot software.
//
//You are not permitted to:
//
//Edit, alter, modify, adapt, translate or otherwise change the whole or any part of the Software nor permit the whole or any part of
//the Software to be combined with or become incorporated in any other software, nor decompile, disassemble or reverse engineer the 
//Software or attempt to do any such things
//
//Reproduce, copy, distribute, resell or otherwise use the Software for any commercial purpose
//Allow any third party to use the Software on behalf of or for the benefit of any third party
//Use the Software in any way which breaches any applicable local, national or international law
//use the Software for any purpose that Fairfield Tek L.L.C. considers is a breach of this EULA agreement
//Intellectual Property and Ownership
//Fairfield Tek L.L.C. shall at all times retain ownership of the Software as originally downloaded by you and all subsequent downloads
// of the Software by you. The Software (and the copyright, and other intellectual property rights of whatever nature in the Software,
// including any modifications made thereto) are and shall remain the property of Fairfield Tek L.L.C..
//Fairfield Tek L.L.C. reserves the right to grant licences to use the Software to third parties.
//Termination
//This EULA agreement is effective from the date you first use the Software and shall continue until terminated. 
//You may terminate it at any time upon written notice to Fairfield Tek L.L.C..
//It will also terminate immediately if you fail to comply with any term of this EULA agreement. Upon such termination,
// the licenses granted by this EULA agreement will immediately terminate and you agree to stop all access and use of the Software.
//The provisions that by their nature continue and survive will survive any termination of this EULA agreement.
//
//Governing Law
//This EULA agreement, and any dispute arising out of or in connection with this EULA agreement, 
//shall be governed by and construed in accordance with the laws of us.
//
//By accepting this EULA, you agree to hold harmless (Blockelot) FairfieldTek in the event that the cloud storage service is discontinued.
//
//Blockelot and it's Cloud Storage is provided "as is", without warranties of any kind.
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
