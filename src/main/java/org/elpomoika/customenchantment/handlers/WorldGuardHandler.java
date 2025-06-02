package org.elpomoika.customenchantment.handlers;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.block.Block;
import org.elpomoika.customenchantment.CustomEnchantment;

import java.util.List;

public class WorldGuardHandler {

    private final CustomEnchantment plugin;

    public WorldGuardHandler(CustomEnchantment plugin) {
        this.plugin = plugin;
    }

    public ProtectedRegion getRegion(Block block) {
        ProtectedRegion region = getRegionAtBlock(block);

        return region;
    }

    public boolean isRegionIgnored(ProtectedRegion region) {
        List<String> regions = plugin.getConfig().getStringList("ignored-regions");

        return regions.contains(region.getId());
    }

    private ProtectedRegion getRegionAtBlock(Block block) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = container.get(BukkitAdapter.adapt(block.getWorld()));

        ApplicableRegionSet set = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(block.getLocation()));

        for (ProtectedRegion region : set) {
            if (region != null) {
                return region;
            }
        }

        return null;
    }
}
