package com.github.jenya705.cubichardcore;

import com.github.jenya705.cubichardcore.listener.CraftingListener;
import com.github.jenya705.cubichardcore.listener.DamageListener;
import com.github.jenya705.cubichardcore.listener.DeathListener;
import com.github.jenya705.cubichardcore.listener.EyeBedrockListener;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class CubicHardcore extends JavaPlugin {

    public static final int SPAWN_RADIUS = 500;
    public static final int MIN_Y = 200;

    private final List<Location> bedrockLocations = new ArrayList<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new CraftingListener(), this);
        getServer().getPluginManager().registerEvents(new EyeBedrockListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        World world = Bukkit.getWorld(NamespacedKey.minecraft("overworld"));
        for (int i = 0; i < 10; i++) {
            spawnBedrock(world);
        }
    }

    @Override
    public void onDisable() {
        despawnBedrock();
    }

    public void respawnBedrock(Location which) {
        bedrockLocations.remove(which);
        spawnBedrock(which.getWorld());
    }

    public void spawnBedrock(World world) {
        int x = ThreadLocalRandom.current().nextInt(SPAWN_RADIUS * 2) - SPAWN_RADIUS;
        int y = ThreadLocalRandom.current().nextInt(MIN_Y, 255);
        int z = ThreadLocalRandom.current().nextInt(SPAWN_RADIUS * 2) - SPAWN_RADIUS;
        Location newLocation = new Location(world, x, y, z);
        if (bedrockLocations.contains(newLocation)) return;
        newLocation.getBlock().setType(Material.BEDROCK);
        bedrockLocations.add(newLocation);
        getLogger().info("Spawning bedrock at (%s, %s, %s)".formatted(
                Integer.toString(x), Integer.toString(y), Integer.toString(z)
        ));
    }

    public void despawnBedrock() {
        bedrockLocations.forEach(it -> it.getBlock().setType(Material.AIR));
    }

}
