package org.elpomoika.customenchantment.listeners;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.RayTraceResult;
import org.elpomoika.customenchantment.CustomEnchantment;
import org.elpomoika.customenchantment.handlers.WorldGuardHandler;
import org.elpomoika.customenchantment.util.ToolUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class PickaxeEnchantListener implements Listener {

    private final WorldGuardHandler worldGuardHandler;
    private final CustomEnchantment plugin;
    private List<String> material;
    private final ToolUtil toolUtil;

    public PickaxeEnchantListener(WorldGuardHandler worldGuardHandler, CustomEnchantment plugin, ToolUtil toolUtil) {
        this.worldGuardHandler = worldGuardHandler;
        this.plugin = plugin;
        this.toolUtil = toolUtil;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        material = plugin.getConfig().getStringList("allowed-block-to-break");

        boolean has3x3 = toolUtil.isToolHaveSuperPickaxe(tool);
        boolean hasMagnetism = toolUtil.isToolHaveMagnetism(tool);

        if (!has3x3 && !hasMagnetism) return;

        Block block = event.getBlock();

        ProtectedRegion region = worldGuardHandler.getRegion(block);

        if (plugin.getConfig().getBoolean("is-ignored-by-regions")) {
            if (worldGuardHandler.isRegionIgnored(region)) {
                event.setCancelled(true);
                return;
            }
        } else if (plugin.getConfig().getBoolean("is-allowed-by-block")) {
            if (!material.contains(block.getType().name())) {
                event.setCancelled(true);
                return;
            }
        }

        if (has3x3) {
            event.setCancelled(true);
            breakBlocksAround(player, block, tool, hasMagnetism);
        }
        if (hasMagnetism) {
            event.setDropItems(false);
            processMagnetism(player, block, tool);
        }
    }

    private void breakBlocksAround(Player player, Block block, ItemStack tool, boolean hasMagnetism) {
        final BlockFace face = getBlockFace(player);

        final List<Block> blocksToBreak = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {

                    if (x == 0 && y == 0 && z == 0) continue;

                    final Block relative = getRelativeBlock(block, face, x, y, z);
                    if (relative == null) continue;

                    if (plugin.getConfig().getBoolean("is-ignored-by-regions")) {
                        final ProtectedRegion region = worldGuardHandler.getRegion(relative);
                        if (region == null || worldGuardHandler.isRegionIgnored(region)) continue;
                    }

                    if (!material.contains(relative.getType().name())) continue;

                    blocksToBreak.add(relative);
                }
            }
        }

        for (Block blockBreak : blocksToBreak) {
            if (hasMagnetism) {
                processMagnetism(player, blockBreak, tool);
            } else {
                blockBreak.breakNaturally(tool, true);
            }
        }
    }

    private void processMagnetism(Player player, Block block, ItemStack tool) {
        if (block.getType() == Material.AIR) return;

        Collection<ItemStack> drops = block.getDrops(tool, player);
        block.setType(Material.AIR, false);

        for (ItemStack item : drops) {
            if (item == null || item.getType() == Material.AIR) continue;

            HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(item);

            if (!leftover.isEmpty()) {
                for (ItemStack remainingItem : leftover.values()) {
                    if (remainingItem != null) {
                        block.getWorld().dropItemNaturally(block.getLocation(), remainingItem);
                    }
                }
            }
        }
    }

    private Block getRelativeBlock(Block block, BlockFace face, int x, int y, int z) {
        return switch (face) {
            case UP, DOWN -> block.getRelative(x, 0, z);
            case NORTH, SOUTH -> block.getRelative(x, y, 0);
            case EAST, WEST -> block.getRelative(0, y, z);
            default -> null;
        };
    }

    private BlockFace getBlockFace(Player player) {
        RayTraceResult result = player.rayTraceBlocks(5.0);

        if (result == null || result.getHitBlock() == null) {
            return BlockFace.SELF;
        }

        return result.getHitBlockFace();
    }
}
