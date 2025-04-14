package org.elpomoika.customenchantment.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.elpomoika.customenchantment.CustomEnchantment;
import org.elpomoika.customenchantment.enchantments.UltraPickaxe;
import org.jetbrains.annotations.NotNull;

public class GiveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        UltraPickaxe pickaxe = new UltraPickaxe("pickaxe3x3");

        ItemStack pickaxe3x3 = new ItemStack(Material.NETHERITE_PICKAXE);

        pickaxe.applyEnchant(pickaxe3x3);

        player.getInventory().addItem(pickaxe3x3);
        player.sendMessage("You get pickaxe3x3");
        return true;
    }
}
