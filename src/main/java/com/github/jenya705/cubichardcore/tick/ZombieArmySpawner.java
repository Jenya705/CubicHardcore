package com.github.jenya705.cubichardcore.tick;

import com.github.jenya705.cubichardcore.CubicHardcore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class ZombieArmySpawner implements Runnable {

    private static final int RANDOM_CHANCE = 15;
    private static final int ARMY_COUNT = 3;
    private static final double POS_RANDOM = 10;

    private final CubicHardcore plugin;

    public ZombieArmySpawner(CubicHardcore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (ThreadLocalRandom.current().nextInt(RANDOM_CHANCE) != 0) return;
            Location armyLocation = new Location(
                    player.getWorld(),
                    player.getLocation().getX() + ThreadLocalRandom.current().nextDouble(-POS_RANDOM, POS_RANDOM),
                    player.getLocation().getY(),
                    player.getLocation().getZ() + ThreadLocalRandom.current().nextDouble(-POS_RANDOM, POS_RANDOM)
            );
            for (int i = 0; i < ARMY_COUNT; ++i) {
                Zombie zombie = armyLocation.getWorld().spawn(armyLocation, Zombie.class);
                zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4);
                zombie.setTarget(player);
            }
            plugin.getLogger().info("Spawned army for the player %s on the (%s, %s, %s)".formatted(
                    player.getName(),
                    Integer.toString(armyLocation.getBlockX()),
                    Integer.toString(armyLocation.getBlockY()),
                    Integer.toString(armyLocation.getBlockZ())
            ));
        });
    }
}
