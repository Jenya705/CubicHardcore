package com.github.jenya705.cubichardcore.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    private static final double MODIFIER = 3;

    @EventHandler
    public void hit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        double finalDamage = event.getFinalDamage();
        if (player.getHealth() < finalDamage) return;
        event.setDamage(Math.min(
                player.getHealth() - 0.5,
                finalDamage * MODIFIER
        ));
    }

}
