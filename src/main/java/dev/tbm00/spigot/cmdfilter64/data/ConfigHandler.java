package dev.tbm00.spigot.cmdfilter64.data;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import dev.tbm00.spigot.cmdfilter64.CmdFilter64;

public class ConfigHandler {
    private final CmdFilter64 javaPlugin;
    private String chatPrefix;
    private boolean prefixedBlocked = false;
    private boolean spacingBlocked = false;

    /**
     * Constructs a ConfigHandler instance.
     *
     * @param javaPlugin the main plugin instance
     */
    public ConfigHandler(CmdFilter64 javaPlugin) {
        this.javaPlugin = javaPlugin;
        try {
            loadLanguageSection();
            loadFeatureSection();
        } catch (Exception e) {
            javaPlugin.log(ChatColor.RED, "Caught exception loading config: ");
            javaPlugin.getLogger().warning(e.getMessage());
        }
    }

    /**
     * Loads the "lang" section of the configuration.
     */
    private void loadLanguageSection() {
        ConfigurationSection section = javaPlugin.getConfig().getConfigurationSection("lang");
        if (section!=null)
            chatPrefix = section.contains("prefix") ? section.getString("prefix") : null;
    }

    /**
     * Loads the "features" section of the configuration.
     */
    private void loadFeatureSection() {
        ConfigurationSection section = javaPlugin.getConfig().getConfigurationSection("features");
        if (section!=null) {
            prefixedBlocked = section.contains("blacklistPluginPrefixedCommands") ? section.getBoolean("blacklistPluginPrefixedCommands") : false;
            spacingBlocked = section.contains("blacklistSpacedCommands") ? section.getBoolean("blacklistSpacedCommands") : false;
        }
    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public boolean isPrefixedBlocked() {
        return prefixedBlocked;
    }

    public boolean isSpacingBlocked() {
        return spacingBlocked;
    }
}