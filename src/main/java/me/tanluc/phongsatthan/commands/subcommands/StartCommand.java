package me.tanluc.phongsatthan.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.commands.ChildCommand;
import me.tanluc.phongsatthan.contracts.CommonContract;
import me.tanluc.phongsatthan.contracts.EpicContract;
import me.tanluc.phongsatthan.contracts.LegendaryContract;
import me.tanluc.phongsatthan.database.DatabaseManager;
import me.tanluc.phongsatthan.utils.CurrentContracts;
import me.tanluc.phongsatthan.utils.GenericUseMethods;
import me.tanluc.phongsatthan.utils.PlayerObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StartCommand extends ChildCommand {

    private final GenericUseMethods genericUseMethods;
    private final CommonContract commonContract;
    private final EpicContract epicContract;
    private final LegendaryContract legendaryContract;
    private final MobContracts plugin;
    private final CurrentContracts currentContracts;
    private final DatabaseManager databaseManager;

    public StartCommand(String command,
                        GenericUseMethods genericUseMethods,
                        CommonContract commonContract,
                        EpicContract epicContract,
                        LegendaryContract legendaryContract,
                        MobContracts plugin,
                        CurrentContracts currentContracts,
                        DatabaseManager databaseManager) {
        super(command);
        this.genericUseMethods = genericUseMethods;
        this.commonContract = commonContract;
        this.epicContract = epicContract;
        this.legendaryContract = legendaryContract;
        this.plugin = plugin;
        this.currentContracts = currentContracts;
        this.databaseManager = databaseManager;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.core";
    }

    @Override
    public String getDescription() {
        return plugin.getConfig().getString("messages.command-usage.start.desc");
    }

    @Override
    public String getSyntax() {
        return plugin.getConfig().getString("messages.command-usage.start.usage");
    }

    @Override
    public Boolean consoleUse() {
        return false;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;
        Map<UUID, PlayerObject> map = databaseManager.getPlayerMap();

        if (!(args.length > 1)) {
            genericUseMethods.sendMessageWithPrefix(player, "&e" + this.getSyntax());
            return;
        }

        if (currentContracts.inContract(player)) {
            genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.already-in-contract"));
            return;
        }

        String type = args[1];
        if ((type.equalsIgnoreCase("legendary")) || (type.equalsIgnoreCase("leg")) || (type.equalsIgnoreCase("l"))) {

            if(map.get(player.getUniqueId()).getLegendaryOwned() > 0){
                legendaryContract.summonLegendaryContract(player);
                map.get(player.getUniqueId()).setLegendaryOwned(map.get(player.getUniqueId()).getLegendaryOwned() - 1);
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.start-legendary"));
            }else{
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.no-contracts-left")
                        .replace("%type%", "Legendary"));
            }

        } else if ((type.equalsIgnoreCase("epic")) || (type.equalsIgnoreCase("ep")) || (type.equalsIgnoreCase("e"))) {

            if(map.get(player.getUniqueId()).getEpicOwned() > 0){
                epicContract.summonEpicContract(player);
                map.get(player.getUniqueId()).setEpicOwned(map.get(player.getUniqueId()).getEpicOwned() - 1);
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.start-epic"));
            } else {
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.no-contracts-left")
                        .replace("%type%", "Epic"));
            }

        } else if ((type.equalsIgnoreCase("common")) || (type.equalsIgnoreCase("com")) || (type.equalsIgnoreCase("c"))) {

            if(map.get(player.getUniqueId()).getCommonOwned() > 0){
                commonContract.summonCommonContract(player);
                map.get(player.getUniqueId()).setCommonOwned(map.get(player.getUniqueId()).getCommonOwned() - 1);
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.start-common"));
            }else {
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.no-contracts-left")
                        .replace("%type%", "Common"));
            }
        }
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 2) {
            return Arrays.asList("Common", "Epic", "Legendary");
        }
        return null;
    }
}
