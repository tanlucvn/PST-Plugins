package me.tanluc.phongsatthan.events;

import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ContractTargetEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Creature entity;
    private final Player player;
    private final Location eLoc;
    private final Location pLoc;

    public ContractTargetEvent(Creature entity, Player player, Location eLoc, Location pLoc) {
        this.entity = entity;
        this.player = player;
        this.eLoc = eLoc;
        this.pLoc = pLoc;
    }

    public Creature getEntity() {
        return entity;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getEntityLocation() {
        return eLoc;
    }

    public Location getPlayerLocation() {
        return pLoc;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
