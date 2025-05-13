

package dev.tbm00.spigot.cmdfilter64.command;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;

import net.md_5.bungee.api.chat.TextComponent;

import dev.tbm00.spigot.cmdfilter64.CmdFilter64;
import dev.tbm00.spigot.cmdfilter64.data.ConfigHandler;
import dev.tbm00.spigot.cmdfilter64.data.EntryManager;

public class AdminCommand implements TabExecutor {
    private final CmdFilter64 javaPlugin;
    private final ConfigHandler configHandler;
    private final EntryManager entryManager;
    private final String[] SUBCOMMANDS = new String[]{"add", "remove", "check", "list"};

    public AdminCommand(CmdFilter64 javaPlugin, ConfigHandler configHandler, EntryManager entryManager) {
        this.javaPlugin = javaPlugin;
        this.configHandler = configHandler;
        this.entryManager = entryManager;
    }

    /**
     * Handles the "/" command.
     * 
     * @param sender the command sender
     * @param consoleCommand the command being executed
     * @param label the label used for the command
     * @param args the arguments passed to the command
     * @return true if the command was handled successfully, false otherwise
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!hasPermission(sender)) return true;

        if (args.length == 0) return true;

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "add":
                return handleAddCommand(sender, args);
            case "remove":
                return handleRemoveCommand(sender, args);
            case "check":
                return handleCheckCommand(sender, args);
            case "list":
                return handleListCommand(sender);
            default:
                sendMessage(sender, ChatColor.RED + "Unknown subcommand!");
                return false;
        }
    }

    private boolean handleAddCommand(CommandSender sender, String[] args) {
        if (args.length!=2) {
            sendMessage(sender, "Usage: /cmdfilter remove <string>");
            return true;
        }

        String cmd = args[1].toLowerCase();
        if (!entryManager.entryExists(cmd)) {
            entryManager.saveEntry(cmd);
            sendMessage(sender, "Added '" + cmd + "' to the blacklist!");
        } else {
            sendMessage(sender, "'" + cmd + "' already exists in the blacklist!");
        }

        return true;
    }

    private boolean handleRemoveCommand(CommandSender sender, String[] args) {
        if (args.length!=2) {
            sendMessage(sender, "Usage: /cmdfilter remove <string>");
            return true;
        }

        String cmd = args[1].toLowerCase();
        if (entryManager.entryExists(cmd)) {
            entryManager.deleteEntry(cmd);
            sendMessage(sender, "Removed '" + cmd + "' from the blacklist!");
        } else {
            sendMessage(sender, "'" + cmd + "' doesn't exist in the blacklist!");
        }

        return true;
    }

    private boolean handleCheckCommand(CommandSender sender, String[] args) {
        if (args.length!=2) {
            sendMessage(sender, "Usage: /cmdfilter check <string>");
            return true;
        }

        String cmd = args[1].toLowerCase();
        if (entryManager.entryExists(cmd)) {
            sendMessage(sender, "'" + cmd + "' exists in the blacklist!");
        } else {
            sendMessage(sender, "'" + cmd + "' doesn't exist in the blacklist!");
        }
        
        return true;
    }

    private boolean handleListCommand(CommandSender sender) {
        Set<String> cmds = entryManager.getEntries();
        for (String cmd : cmds) {
            sender.sendMessage(cmd);
        }
        return true;
    }

    /**
     * Checks if the sender has a specific permission.
     * 
     * @param sender the command sender
     * @return true if the sender has the permission, false otherwise
     */
    private boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(javaPlugin.ADMIN_PERM) || sender instanceof ConsoleCommandSender;
    }

    /**
     * Sends a message to a target CommandSender.
     * 
     * @param target the CommandSender to send the message to
     * @param string the message to send
     */
    private void sendMessage(CommandSender target, String string) {
        if (!string.isBlank())
            target.spigot().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', configHandler.getChatPrefix() + string)));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (!hasPermission(sender)) return list;

        if (args.length == 1) {
            list.clear();
            for (String n : SUBCOMMANDS) {
                if (n!=null && n.startsWith(args[0])) 
                    list.add(n);
            }
        }
        return list;
    }
}