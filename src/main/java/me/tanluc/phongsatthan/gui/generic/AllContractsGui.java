package me.tanluc.phongsatthan.gui.generic;

//import org.apache.commons.text.WordUtils;
import me.tanluc.phongsatthan.gui.handler.GuiUtil;
import me.tanluc.phongsatthan.gui.handler.PaginatedGui;
import me.tanluc.phongsatthan.utils.PlayerObject;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.database.DatabaseManager;
import me.tanluc.phongsatthan.gui.MainMenu;
import me.tanluc.phongsatthan.utils.ContractObject;
import me.tanluc.phongsatthan.utils.CreateCustomGuiItem;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class AllContractsGui extends PaginatedGui {

    private final DatabaseManager databaseManager;
    private final CreateCustomGuiItem createCustomGuiItem;
    private final MobContracts plugin;
    private int index;

    public AllContractsGui(GuiUtil guiUtil,
                           DatabaseManager databaseManager,
                           CreateCustomGuiItem createCustomGuiItem,
                           MobContracts plugin) {
        super(plugin, guiUtil);
        this.databaseManager = databaseManager;
        this.createCustomGuiItem = createCustomGuiItem;
        this.plugin = plugin;
    }

    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.title.all"));
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

        ArrayList<Map.Entry<UUID, ContractObject>> sorted = new ArrayList<>(databaseManager.getContractMap().entrySet());
        //sorted.sort(Collections.reverseOrder((Comparator) Map.Entry.comparingByValue()));
        sorted.sort(Collections.reverseOrder(Comparator.comparing(d -> d.getValue().getDate())));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < super.maxItemsPerPage; i++) {
            index = super.maxItemsPerPage * page + i;
            if (index >= sorted.size()) break;
            if (sorted.get(index) != null) {

                UUID mobUuid = sorted.get(index).getKey();
                String tier = sorted.get(index).getValue().getTier();
                String type = sorted.get(index).getValue().getMobType().name();
                String name = sorted.get(index).getValue().getDisplayName();
                String summonerName = sorted.get(index).getValue().getSummonerName();
                int health = sorted.get(index).getValue().getHealth();
                int damage = sorted.get(index).getValue().getDamage();
                Date date = new Date(sorted.get(index).getValue().getDate()*1000L);
                String acDate = formatter.format(date);

                String color = tier.equalsIgnoreCase("Legendary") ? "&6" : tier.equalsIgnoreCase("Epic") ? "&5" : "&7";
                String base = CreateCustomGuiItem.MobType.valueOf(type).getBase();
                String formattedType = WordUtils.capitalizeFully(type.replace("_", " "));

                inventory.addItem(createCustomGuiItem.getCustomSkull(name
                                .replace("§", "&")
                                .replace("[", "")
                                .replace("]", ""),
                        base, plugin.getConfig().getStringList("gui.all-contracts.lore")
                                .stream().map(s -> s.replace("%name%", summonerName)
                                        .replace("%health%", String.valueOf(health))
                                        .replace("%damage%", String.valueOf(damage))
                                        .replace("%color%", color)
                                        .replace("%tier%", tier)
                                        .replace("%entity_type%", formattedType)
                                        .replace("%date%", acDate))
                                .collect(Collectors.toList())));
            }
        }
    }
}
