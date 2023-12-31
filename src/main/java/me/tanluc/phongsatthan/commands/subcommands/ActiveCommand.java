package me.tanluc.phongsatthan.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.commands.ChildCommand;
import me.tanluc.phongsatthan.utils.CurrentContracts;
import me.tanluc.phongsatthan.utils.GenericUseMethods;

import java.util.List;

public class ActiveCommand extends ChildCommand {

    private final MobContracts plugin;
    private final CurrentContracts currentContracts;
    private final GenericUseMethods genericUseMethods;

    public ActiveCommand(String command, MobContracts plugin, CurrentContracts currentContracts, GenericUseMethods genericUseMethods) {
        super(command);
        this.plugin = plugin;
        this.currentContracts = currentContracts;
        this.genericUseMethods = genericUseMethods;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.admin";
    }

    @Override
    public String getDescription() {
        return plugin.getConfig().getString("messages.command-usage.active.desc");
    }

    @Override
    public String getSyntax() {
        return plugin.getConfig().getString("messages.command-usage.active.usage");
    }

    @Override
    public Boolean consoleUse() {
        return false;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (currentContracts.getContracts().isEmpty()) {
            genericUseMethods.sendMessageWithPrefix(player, "&cError: No active contracts!");
            return;
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.current-active-title")));
        currentContracts.getContracts().entrySet().forEach(e -> {
            Player p = plugin.getServer().getPlayer(e.getKey());
            player.sendMessage(ChatColor.GRAY + "-------------------");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.command.current-active")
                            .replace("%entity%", e.getValue().getCustomName())
                            .replace("%name%", p.getName())));
        });
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
