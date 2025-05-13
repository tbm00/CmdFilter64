package dev.tbm00.spigot.suggestionblocker64.data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.bukkit.plugin.java.JavaPlugin;

public class JSONHandler {
    private final JavaPlugin javaPlugin;
    private final Object fileLock = new Object();
    private File jsonFile;
    private Gson gson;

    public JSONHandler(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.gson = new Gson();
        initializeDatabase();
    }

    private void initializeDatabase() {
        synchronized (fileLock) {
            jsonFile = new File(javaPlugin.getDataFolder(), "blocked_cmds.json");
            if (!jsonFile.exists()) {
                try {
                    jsonFile.getParentFile().mkdirs();
                    jsonFile.createNewFile();
                    saveEntries(new HashSet<>());
                } catch (Exception e) {
                    javaPlugin.getLogger().severe("Exception when creating new JSON file!");
                    e.printStackTrace();
                }
            }
        }
    }

    public Set<String> loadEntries() {
        synchronized (fileLock) {
            try (FileReader reader = new FileReader(jsonFile)) {
                Type type = new TypeToken<Set<String>>(){}.getType();
                Set<String> entries = gson.fromJson(reader, type);
                return (entries != null) ? entries : new HashSet<>();
            } catch (Exception e) {
                javaPlugin.getLogger().severe("Exception when loading JSON file!");
                e.printStackTrace();
                return new HashSet<>();
            }
        }
    }
    
    public void saveEntries(Set<String> entries) {
        synchronized (fileLock) {
            try (FileWriter writer = new FileWriter(jsonFile)) {
                gson.toJson(entries, writer);
            } catch (Exception e) {
                javaPlugin.getLogger().severe("Exception when saving JSON file!");
                e.printStackTrace();
            }
        }
    }
}

