package dev.tbm00.spigot.blankplugin;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigHandler {
    private final BlankPlugin javaPlugin;
    private String chatPrefix;
    private boolean featureEnabled = false;

    /**
     * Constructs a ConfigHandler instance.
     *
     * @param javaPlugin the main plugin instance
     */
    public ConfigHandler(BlankPlugin javaPlugin) {
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
     * Loads the "feature" section of the configuration.
     */
    private void loadFeatureSection() {
        ConfigurationSection section = javaPlugin.getConfig().getConfigurationSection("feature");
        if (section!=null)
            featureEnabled = section.contains("enabled") ? section.getBoolean("enabled") : false;
    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public boolean isFeatureEnabled() {
        return featureEnabled;
    }
}