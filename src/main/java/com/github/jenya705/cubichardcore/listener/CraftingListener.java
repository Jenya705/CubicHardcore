package com.github.jenya705.cubichardcore.listener;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.*;

public class CraftingListener implements Listener {

    private static final Set<Material> DISABLED = new HashSet<>(List.of(Material.ENDER_EYE));

    @EventHandler
    public void craft(CraftItemEvent event) {
        if (DISABLED.contains(event.getRecipe().getResult().getType())) {
            event.setResult(Event.Result.DENY);
        }
    }

}
