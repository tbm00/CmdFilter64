package dev.tbm00.spigot.cmdfilter64.listener;

import java.util.Collection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
//import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import dev.tbm00.spigot.cmdfilter64.CmdFilter64;
import dev.tbm00.spigot.cmdfilter64.data.ConfigHandler;
import dev.tbm00.spigot.cmdfilter64.data.EntryManager;

public class CommandInput implements Listener {
    private final CmdFilter64 javaPlugin;
    private final ConfigHandler configHandler;
    private final EntryManager entryManager;

    public CommandInput(CmdFilter64 javaPlugin, ConfigHandler configHandler, EntryManager entryManager) {
        this.javaPlugin = javaPlugin;
        this.configHandler = configHandler;
        this.entryManager = entryManager;
    }

    /**
     * Handles the event called whenever a player runs a command (by placing a slash at the start of their message).
     *
     * @param event the PlayerCommandPreprocessEvent
     */
    /*@EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
        if (e.getPlayer().hasPermission(javaPlugin.BYPASS_PERM)) return;

        String[] message = e.getMessage().toLowerCase().split(" ");
        String command = message[0].substring(1);

        if (isBlacklisted(command)) e.setCancelled(true);
    }*/

    /**
     * Handles the event called when the list of available server commands is sent to the player.
     *
     * @param event the PlayerCommandSendEvent
     */
    @EventHandler
    public void onPlayerCommandSendEvent(PlayerCommandSendEvent e) {
        if (e.getPlayer().hasPermission(javaPlugin.BYPASS_PERM)) return;

        Collection<String> cmds = e.getCommands();
        cmds.removeIf(this::isBlacklisted);
    }

    private boolean isBlacklisted(String command) {
        if (entryManager.entryExists(command)) return true;
        if (configHandler.isPrefixedBlocked() && command.contains(":")) return true;
        if (configHandler.isSpacingBlocked() && command.contains(" ")) return true;
        return false;
    }
}