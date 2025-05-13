package dev.tbm00.spigot.cmdfilter64;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import dev.tbm00.spigot.cmdfilter64.command.AdminCommand;
import dev.tbm00.spigot.cmdfilter64.data.ConfigHandler;
import dev.tbm00.spigot.cmdfilter64.data.EntryManager;
import dev.tbm00.spigot.cmdfilter64.data.JSONHandler;
import dev.tbm00.spigot.cmdfilter64.listener.CommandInput;

public class CmdFilter64 extends JavaPlugin {
    private ConfigHandler configHandler;
    private JSONHandler jsonHandler;
    private EntryManager entryManager;

    public final String ADMIN_PERM = "cmdfilter64.admin";
    public final String BYPASS_PERM = "cmdfilter64.bypass";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        final PluginDescriptionFile pdf = this.getDescription();
        
        log(ChatColor.LIGHT_PURPLE,
            ChatColor.DARK_PURPLE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-",
            pdf.getName() + " v" + pdf.getVersion() + " created by tbm00",
            ChatColor.DARK_PURPLE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");

        try {
            jsonHandler = new JSONHandler(this);
        } catch (Exception e) {
            getLogger().severe("Failed to connect to JSON. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        entryManager = new EntryManager(this, jsonHandler);

        if (getConfig().contains("enabled") && getConfig().getBoolean("enabled")) {
            configHandler = new ConfigHandler(this);
            getServer().getPluginManager().registerEvents(new CommandInput(this, configHandler, entryManager), this);
            getCommand("cmdfilter").setExecutor(new AdminCommand(this, configHandler, entryManager));
        } else {
            getLogger().severe("Disabled in config...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        entryManager.close();
        log(ChatColor.RED, "CmdFilter64 disabled..!");
    }

    /**
     * Logs one or more messages to the server console with the prefix & specified chat color.
     *
     * @param chatColor the chat color to use for the log messages
     * @param strings one or more message strings to log
     */
    public void log(ChatColor chatColor, String... strings) {
		for (String s : strings)
            getServer().getConsoleSender().sendMessage("[CmdFilter64] " + chatColor + s);
	}
}