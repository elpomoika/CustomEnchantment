package org.elpomoika.customenchantment;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.elpomoika.customenchantment.commands.CustomEnchantCommand;
import org.elpomoika.customenchantment.enchantments.Magnetism;
import org.elpomoika.customenchantment.enchantments.UltraPickaxe;
import org.elpomoika.customenchantment.handlers.WorldGuardHandler;
import org.elpomoika.customenchantment.listeners.PickaxeEnchantListener;
import org.elpomoika.customenchantment.util.ToolUtil;

import java.lang.reflect.Field;

public final class CustomEnchantment extends JavaPlugin {

    public static final UltraPickaxe ULTRA_PICKAXE = new UltraPickaxe("pickaxe3x3");
    public static final Magnetism MAGNETISM = new Magnetism("magnetism");
    private WorldGuardHandler worldGuardHandler;

    @Override
    public void onEnable() {
        worldGuardHandler = new WorldGuardHandler(this);
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new PickaxeEnchantListener(worldGuardHandler, this, new ToolUtil()), this);
        getCommand("custom-enchant").setExecutor(new CustomEnchantCommand(this));
    }

    @Override
    public void onLoad() {
        registerCustomEnchantment(MAGNETISM);
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


}
