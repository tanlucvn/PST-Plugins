package me.tanluc.phongsatthan.listeners;

import me.tanluc.phongsatthan.gui.handler.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class GuiClickListener implements Listener {

    @EventHandler
    public void onGuiClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(Gui.guiName)) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getClickedInventory() == null) return;
            InventoryHolder holder = e.getClickedInventory().getHolder();

            if (holder instanceof Gui) {
                if (e.getCurrentItem() == null) return;

                Gui gui = (Gui) holder;
                gui.handleMenu(e);
            }
        }
    }
}
