package org.elpomoika.customenchantment.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.elpomoika.customenchantment.CustomEnchantment;

public class ToolUtil {
    public boolean isToolHaveMagnetism(ItemStack tool) {
        if (!(tool.getType() == Material.NETHERITE_PICKAXE) && !tool.hasItemMeta()) return false;

        ItemMeta toolMeta = tool.getItemMeta();

        return toolMeta.hasEnchant(CustomEnchantment.MAGNETISM);
    }

    public boolean isToolHaveSuperPickaxe(ItemStack tool) {
        if (!(tool.getType() == Material.NETHERITE_PICKAXE) && !tool.hasItemMeta()) return false;

        ItemMeta toolMeta = tool.getItemMeta();

        return toolMeta.hasEnchant(CustomEnchantment.ULTRA_PICKAXE);
    }
}
