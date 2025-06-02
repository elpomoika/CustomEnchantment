package org.elpomoika.customenchantment.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.elpomoika.customenchantment.CustomEnchantment;
import org.elpomoika.customenchantment.enchantments.Magnetism;
import org.elpomoika.customenchantment.enchantments.UltraPickaxe;
import org.jetbrains.annotations.NotNull;

public class CustomEnchantCommand implements CommandExecutor {

    private final CustomEnchantment plugin;

    public CustomEnchantCommand(CustomEnchantment plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Конфиг CustomEnchant перезагружен.");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("enchant")) {
            if (args[1].equalsIgnoreCase("magnetism")) {
                Magnetism magnetism = new Magnetism("magnetism");
                if (player.getInventory().getItemInMainHand().getType().isItem()) {
                    magnetism.applyEnchant(player.getInventory().getItemInMainHand());
                } else {
                    player.sendMessage("Нельзя на эту хуйню зачарить магнетизм");
                }
            } else if (args[1].equalsIgnoreCase("pickaxe3x3")) {
                UltraPickaxe pickaxe3x3 = new UltraPickaxe("pickaxe3x3");
                if (player.getInventory().getItemInMainHand().getType().isItem()) {
                    pickaxe3x3.applyEnchant(player.getInventory().getItemInMainHand());
                } else {
                    player.sendMessage("Нельзя на эту хуйню зачарить бур");
                }
            }
        }
        return true;
    }
}
