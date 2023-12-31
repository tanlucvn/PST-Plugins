package me.tanluc.phongsatthan.gui.slain;

import me.tanluc.phongsatthan.gui.handler.GuiUtil;
import me.tanluc.phongsatthan.gui.handler.PaginatedGui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.database.DatabaseManager;
import me.tanluc.phongsatthan.gui.MainMenu;
import me.tanluc.phongsatthan.utils.CreateCustomGuiItem;
import me.tanluc.phongsatthan.utils.PlayerObject;

import java.util.*;

public class LegendaryContractsGui extends PaginatedGui {

    private final MobContracts plugin;
    private final DatabaseManager databaseManager;
    private final CreateCustomGuiItem createCustomGuiItem;
    private int index;


    public LegendaryContractsGui(GuiUtil guiUtil,
                                 MobContracts plugin,
                                 DatabaseManager databaseManager,
                                 CreateCustomGuiItem createCustomGuiItem) {
        super(plugin, guiUtil);
        this.plugin = plugin;
        this.databaseManager = databaseManager;
        this.createCustomGuiItem = createCustomGuiItem;
    }


    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.title.legendary"));
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        ArrayList<Map.Entry<UUID, PlayerObject>> sorted = new ArrayList<>(databaseManager.getPlayerMap().entrySet());
        String previousButton = ChatColor.stripColor(plugin.getConfig().getString("gui.button.previous"));
        String nextButton = ChatColor.stripColor(plugin.getConfig().getString("gui.button.next"));
        String closeButton = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.button.close"));

        if (e.getCurrentItem().getType().equals(Material.PAPER)) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(previousButton)) {
                if (page != 0) {
                    page -= 1;
                    super.open();
                } else {
                    new MainMenu(plugin.getMenuUtil((Player) e.getWhoClicked()), createCustomGuiItem, plugin, databaseManager).open();
                }
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(nextButton)) {
                if (!((index + 1) >= sorted.size())) {
                    page += 1;
                    super.open();
                }
            }
        } else if (e.getCurrentItem().getType().equals((Material.BARRIER))) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(closeButton)) {
                e.getWhoClicked().closeInventory();
            }
        }
    }

    @Override
    public void setItems() {

        addBottomRow();

        ArrayList<Map.Entry<UUID, PlayerObject>> sorted = new ArrayList<>(databaseManager.getPlayerMap().entrySet());
        sorted.sort(Collections.reverseOrder(Comparator.comparing(l -> l.getValue().getLegendarySlain())));

        for (int i = 0; i < super.maxItemsPerPage; i++) {
            index = super.maxItemsPerPage * page + i;
            if (index >= sorted.size()) break;
            if (sorted.get(index) != null) {

                UUID uuid = sorted.get(index).getKey();

                inventory.addItem(createCustomGuiItem.getPlayerHead(uuid,
                        plugin.getConfig().getString("gui.legendary-slain.name-color"),
                        createCustomGuiItem.parsePlayerLore(sorted, index, plugin.getConfig().getStringList("gui.legendary-slain.lore"))));
            }
        }
    }

}
