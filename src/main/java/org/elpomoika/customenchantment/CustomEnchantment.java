package org.elpomoika.customenchantment;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.elpomoika.customenchantment.commands.GiveCommand;
import org.elpomoika.customenchantment.enchantments.UltraPickaxe;
import org.elpomoika.customenchantment.listeners.PickaxeEnchantListener;

import java.lang.reflect.Field;

public final class CustomEnchantment extends JavaPlugin {


    public static final UltraPickaxe ULTRA_PICKAXE = new UltraPickaxe("pickaxe3x3");

    @Override
    public void onLoad() {
        registerCustomEnchantment(ULTRA_PICKAXE);
    }

    public static void registerCustomEnchantment(Enchantment enchantment) {
        try {
            if (Enchantment.getByKey(enchantment.getKey()) != null) {
                return;
            }

            Field acceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNew.setAccessible(true);
            acceptingNew.set(null, true);

            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PickaxeEnchantListener(), this);
        getCommand("give-pickaxe").setExecutor(new GiveCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
