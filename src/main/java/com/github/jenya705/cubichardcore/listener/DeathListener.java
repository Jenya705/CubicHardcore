package com.github.jenya705.cubichardcore.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeathListener implements Listener {

    private static final Set<EntityType> ONLY_ENCHANTMENT_DROP = new HashSet<>(List.of(
            EntityType.COW, EntityType.MUSHROOM_COW, EntityType.PIG,
            EntityType.CHICKEN, EntityType.RABBIT, EntityType.SHEEP
    ));

    @EventHandler
    public void death(EntityDeathEvent event) {
        if (ONLY_ENCHANTMENT_DROP.contains(event.getEntityType())) {
            Player killer = event.getEntity().getKiller();
            if (killer == null) {
                event.getDrops().clear();
                return;
            }
            PlayerInventory inventory = killer.getInventory();
            if (inventory.getItemInMainHand().getEnchantments().containsKey(Enchantment.LOOT_BONUS_MOBS) ||
                    inventory.getItemInOffHand().getEnchantments().containsKey(Enchantment.LOOT_BONUS_MOBS)) {
                return;
            }
            event.getDrops().clear();
            killer.sendMessage(Component
                    .text("Ходят слухи, что нужно зачарование добыча, чтобы получить мясо")
                    .color(NamedTextColor.RED)
            );
        }
    }

}
