package org.elpomoika.customenchantment.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.elpomoika.customenchantment.CustomEnchantment;

import java.util.ArrayList;
import java.util.List;

public class PickaxeEnchantListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();

        if (!isToolHaveCustomEnchant(tool)) return;

        Block block = event.getBlock();
        List<Block> blocks = new ArrayList<>();

        var blockFaces = BlockFace.values();

        for (BlockFace blockFace : blockFaces) {
            blocks.add(block.getRelative(blockFace));
        }

        for (var b : blocks) {
            b.breakNaturally(tool, true);
        }
    }

    private boolean isToolHaveCustomEnchant(ItemStack tool) {
        ItemMeta meta = tool.getItemMeta();

        if (!tool.hasItemMeta() && meta == null && !meta.hasEnchant(CustomEnchantment.ULTRA_PICKAXE)) {
            return false;
        }
        return true;
    }
}
