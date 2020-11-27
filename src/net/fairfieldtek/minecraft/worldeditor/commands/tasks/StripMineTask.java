package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.HashMap;
import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import net.fairfieldtek.minecraft.worldeditor.container.BlockInfo;
import net.fairfieldtek.minecraft.worldeditor.container.BlockCollection;
import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import java.util.*;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author geev
 */
public class StripMineTask extends BukkitRunnable {

    private UUID PlayerId;
    private Player Player;
    private HashMap<Material, Integer> MaterialCount = new HashMap<>();
    private ChestManager ChestManager = null;
    private boolean PlacingChest = false;
    private BlockCollection Current;
    private BlockCollection Undo;

    public StripMineTask(UUID playerId) {
        PlayerId = playerId;
        PlacingChest = false;
        Player = Initialization.Plugin.getServer().getPlayer(this.PlayerId);
        PlayerInfo pi = Initialization.PlayerInfoList.get(Player);
        Undo = pi.NewUndo();
        Current = new BlockCollection();
        ChestManager = new ChestManager(Player);
    }

    public void SetBlock(Chunk chunk, int x, int y, int z, Material material) {
        Block changeBlock = chunk.getBlock(x, y, z);
        Material mat = changeBlock.getType();
        if (!MaterialCount.containsKey(mat)) {
            MaterialCount.put(mat, 0);
        }
        MaterialCount.put(mat, MaterialCount.get(mat) + 1);
        Undo.AddBlock(changeBlock,0,0,0, Undo);
        changeBlock.setType(material);
    }

    public void SetBlock(Chunk chunk, int x, int y, int z, Material material, boolean isWorldCoords) {
        Block changeBlock = Player.getWorld().getBlockAt(x, y, z);
        Material mat = changeBlock.getType();
        if (!MaterialCount.containsKey(mat)) {
            MaterialCount.put(mat, 0);
        }
        MaterialCount.put(mat, MaterialCount.get(mat) + 1);
        Undo.AddBlock(new BlockInfo(changeBlock, Undo),Undo);
        changeBlock.setType(material);
    }

    public int ClearChunk(Chunk chunk) {
        //Clear chunk
        int maxHeight = 0;
        for (int x = 0; x <= 15; x++) {
            for (int y = 2; y <= 255; y++) {
                for (int z = 0; z <= 15; z++) {
                    if (chunk.getBlock(x, y, z).getType() != Material.AIR) {
                        if (y > maxHeight) {
                            maxHeight = y;
                        }
                    }
                    SetBlock(chunk, x, y, z, Material.AIR);
                }
            }
        }
        return maxHeight;
    }

    public void DrawOutsideBoarder(Chunk chunk) {

        //Put dorite around edges
        for (int x = 0; x <= 15; x++) {
            int y = 3;
            int z = 0;
            SetBlock(chunk, x, y, z, Material.POLISHED_DIORITE);
            SetBlock(chunk, x, y, z + 15, Material.POLISHED_DIORITE);
        }
        //Put dorite around edges
        int x = 0;
        for (int z = 0; z <= 15; z++) {
            int y = 3;
            SetBlock(chunk, x, y, z, Material.POLISHED_DIORITE);
            SetBlock(chunk, x + 15, y, z, Material.POLISHED_DIORITE);

        }
    }

    public void LayLava(Chunk chunk) {
        for (int x = 0; x <= 15; x++) {
            int y = 2;
            for (int z = 0; z <= 15; z++) {
                SetBlock(chunk, x, y, z, Material.LAVA);
            }
        }
    }

    public void PutWalls(Chunk chunk, int mh, World world) {
        Block b = chunk.getBlock(0, 3, 0);
        int tx = b.getX();
        int tz = b.getZ();
        for (int y = 4; y <= mh; y++) {
            for (int z = tz; z < tz + 16; z++) {

                Location loc = new Location(world, (double) tx - 1, (double) y, (double) z);
                Block tb = world.getBlockAt(loc);
                if (tb.getType() != Material.AIR && tb.getType() != Material.CHEST) {
                    SetBlock(chunk, tx - 1, y, z, Material.STONE_BRICKS, true);
                }
                loc = new Location(world, (double) tx + 16, (double) y, (double) z);
                tb = world.getBlockAt(loc);
                if (tb.getType() != Material.AIR && tb.getType() != Material.CHEST) {
                    SetBlock(chunk, tx + 16, y, z, Material.STONE_BRICKS, Boolean.TRUE);
                }
            }
            for (int x = tx; x < tx + 16; x++) {

                Location loc = new Location(world, (double) x, (double) y, (double) tz - 1);
                Block tb = world.getBlockAt(loc);
                if (tb.getType() != Material.AIR && tb.getType() != Material.CHEST) {
                    SetBlock(chunk, x, y, tz - 1, Material.STONE_BRICKS, true);
                }
                loc = new Location(world, (double) x, (double) y, (double) tz + 16);
                tb = world.getBlockAt(loc);
                if (tb.getType() != Material.AIR && tb.getType() != Material.CHEST) {
                    SetBlock(chunk, x, y, tz + 16, Material.STONE_BRICKS, true);
                }
            }

        }
    }

    public void PutFloor(Chunk chunk) {
        //Clear chunk
        for (int x = 1; x <= 14; x++) {
            {
                int y = 3;
                if (x % 2 == 0) {
                    SetBlock(chunk, x, y, 1, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 3, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 5, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 7, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 9, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 11, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 13, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 2, Material.GLASS);
                    SetBlock(chunk, x, y, 4, Material.GLASS);
                    SetBlock(chunk, x, y, 6, Material.GLASS);
                    SetBlock(chunk, x, y, 8, Material.GLASS);
                    SetBlock(chunk, x, y, 10, Material.GLASS);
                    SetBlock(chunk, x, y, 12, Material.GLASS);
                    SetBlock(chunk, x, y, 14, Material.GLASS);
                } else {
                    SetBlock(chunk, x, y, 1, Material.GLASS);
                    SetBlock(chunk, x, y, 3, Material.GLASS);
                    SetBlock(chunk, x, y, 5, Material.GLASS);
                    SetBlock(chunk, x, y, 7, Material.GLASS);
                    SetBlock(chunk, x, y, 9, Material.GLASS);
                    SetBlock(chunk, x, y, 11, Material.GLASS);
                    SetBlock(chunk, x, y, 13, Material.GLASS);
                    SetBlock(chunk, x, y, 2, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 4, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 6, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 8, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 10, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 12, Material.POLISHED_DIORITE);
                    SetBlock(chunk, x, y, 14, Material.POLISHED_DIORITE);
                }

            }
        }

    }

    private class ChestManager {

        private int cx = 1;
        private int cy = 5;
        private int cz = 1;
        int CurrentSlot = 0;
        Chest Chest = null;
        Chunk Chunk = null;
        Player Player = null;
        World World = null;

        public ChestManager(Player player) {
            CurrentSlot = 0;
            Player = player;
            World = player.getWorld();
            Location loc = player.getLocation();
            Chunk = loc.getChunk();
            AddChest();
        }

        private void AddChest() {
            cx = cx + 1;
            if (cx > 15) {
                cx = 1;
                cz = cz + 1;
            }
            if (cz >= 15) {
                cx = 1;
                cz = 1;
                cy = cy + 1;
            }
            Block b = Chunk.getBlock(cx, cy, cz);
            b.setType(Material.CHEST);
            Chest = (Chest) b.getState();
            Chest.update(true);
            CurrentSlot = 0;
        }

        public void AddItemStack(Material mat, int count) {
            if (CurrentSlot >= 27) {
                AddChest();
            }
            Chest.getInventory().setItem(CurrentSlot, new ItemStack(mat, count));
            CurrentSlot++;
        }

    }

    public boolean SetChest(Chunk chunk, Player player) {

        Iterator it = MaterialCount.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            Material mat = (Material) pair.getKey();
            if (mat == Material.WATER) {
                mat = Material.WATER_BUCKET;
            }
            if (mat == Material.LAVA) {
                mat = Material.LAVA_BUCKET;
            }
            if (mat == Material.AIR || mat == Material.BEDROCK) {
                continue;
            }

            int amount = (int) pair.getValue();
            int maxStackSize = mat.getMaxStackSize();
            int stacks = (int) (amount / maxStackSize);
            int rmdr = amount % maxStackSize;

            while (stacks > 0) {
                ChestManager.AddItemStack(mat, mat.getMaxStackSize());
                stacks--;
            }
            if (rmdr > 0) {
                ChestManager.AddItemStack(mat, rmdr);
            }

            it.remove(); // avoids a ConcurrentModificationException
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        Player player = Initialization.Plugin.getServer().getPlayer(this.PlayerId);
//        try {

        PlayerInfo pi = Initialization.PlayerInfoList.get(player);
        World world = player.getWorld();
        Location loc = player.getLocation();
        Chunk chunk = loc.getChunk();
        if (!PlacingChest) {
            MaterialCount = new HashMap<>();
            int mh = ClearChunk(chunk);
            LayLava(chunk);
            DrawOutsideBoarder(chunk);
            PutFloor(chunk);
            PutWalls(chunk, mh, world);
            PlacingChest = true;
        }
        if (!SetChest(chunk, player)) {
            return;
        } else {
            Initialization.PlayerInfoList.get(player).setIsProcessing(false, "StripMine");
            this.cancel();
        }
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//            System.out.println(e.getMessage());
//            Initialization.PlayerInfoList.get(player).setIsProcessing(false, "StripMine");
//        }

    }

}
