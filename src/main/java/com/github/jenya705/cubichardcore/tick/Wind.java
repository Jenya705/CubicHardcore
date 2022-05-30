package com.github.jenya705.cubichardcore.tick;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Wind implements Runnable {

    private static final int WIND_Y = 175;
    private static final float WIND_FORCE = 0.5f;

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getWorld().getEnvironment() != World.Environment.NORMAL ||
                    player.getLocation().getY() < WIND_Y) return;
            Vector velocity = player.getVelocity();
            velocity.setX(velocity.getX() + WIND_FORCE);
            player.setVelocity(velocity);
        });
    }
}
