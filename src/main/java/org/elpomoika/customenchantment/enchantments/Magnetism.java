package org.elpomoika.customenchantment.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Magnetism extends Enchantment {

    public Magnetism(@NotNull String name) {
        super(NamespacedKey.minecraft(name));
    }

    @Override
    public @NotNull String getName() {
        return "Магнетизм";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return item.getType() == Material.DIAMOND_PICKAXE ||
                item.getType() == Material.IRON_PICKAXE ||
                item.getType() == Material.GOLDEN_PICKAXE ||
                item.getType() == Material.NETHERITE_PICKAXE ||
                item.getType() == Material.STONE_PICKAXE ||
                item.getType() == Material.WOODEN_PICKAXE;
    }

    @Override
    public @NotNull Component displayName(int i) {
        return Component.text("§6Магнетизм", NamedTextColor.GOLD);
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.RARE;
    }

    @Override
    public float getDamageIncrease(int i, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return null;
    }

    @Override
    public @NotNull String translationKey() {
        return null;
    }

    public void applyEnchant(ItemStack item) {
        ItemMeta meta = item.getItemMeta();


        if (meta != null) {
            List<String> lore = new ArrayList<>();
            if(meta.hasLore()){
                lore = meta.getLore();
            }
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7Магнетизм"));
            meta.setLore(lore);
            item.setItemMeta(meta);

            meta.addEnchant(this, 1, true);
            item.setItemMeta(meta);
        }
    }

    public boolean isToolHaveCustomEnchant(ItemStack tool) {
        if (!(tool.getType() == Material.NETHERITE_PICKAXE) && !tool.hasItemMeta()) return false;

        ItemMeta toolMeta = tool.getItemMeta();

        return toolMeta.hasEnchant(this);
    }
}
