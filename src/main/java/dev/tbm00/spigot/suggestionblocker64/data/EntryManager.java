package dev.tbm00.spigot.suggestionblocker64.data;

import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EntryManager {
    private final JavaPlugin javaPlugin;
    private final JSONHandler db;
    private final Set<String> entries;

    public EntryManager(JavaPlugin javaPlugin, JSONHandler db) {
        this.javaPlugin = javaPlugin;
        this.db = db;

        Set<String> loaded = db.loadEntries();
        Set<String> concurrentSet = ConcurrentHashMap.newKeySet();
        concurrentSet.addAll(loaded);
        this.entries = concurrentSet;
    }

    // returns if the string entry for username exists
    public boolean entryExists(String str) {
        return entries.contains(str);
    }

    // creates string entry in json & map if DNE
    // updates string entry in json & map if it does exist
    public void saveEntry(String str) {
        if (entries.add(str)) {
            saveEntriesAsync();
        }
    }

    // removes string entry from json & map
    public void deleteEntry(String str) {
        if (entries.remove(str)) {
            saveEntriesAsync();
        }
    }

    private void saveEntriesAsync() {
        Set<String> snapshot;
        synchronized (entries) {
            snapshot = new HashSet<>(entries);
        }
        Bukkit.getScheduler().runTaskAsynchronously(javaPlugin, () -> db.saveEntries(snapshot));
    }

    // on plugin disable
    public void close() {
        Set<String> snapshot;
        synchronized (entries) {
            snapshot = new HashSet<>(entries);
        }
        db.saveEntries(snapshot);
    }
}