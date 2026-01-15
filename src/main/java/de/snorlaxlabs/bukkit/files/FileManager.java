package de.snorlaxlabs.bukkit.files;


import de.snorlaxlabs.bukkit.LunaBukkit;
import de.snorlaxlabs.storage.ConnectionProvider;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileManager {

    private File dataFolder;
    private File configFile;
    private FileConfiguration authConfig;

    public FileManager(File dataFolder) {
        this.dataFolder = dataFolder;
        initializeFiles();
    }

    public FileManager() {
    }

    private void initializeFiles(){
        // Plugin directory
        if (!dataFolder.exists()) dataFolder.mkdir();

        // Config file
        configFile = new File(dataFolder, "config.yml");
        try {
            if (!configFile.exists()) {
                InputStream stream = LunaBukkit.getProvider().getResource("bukkit-config.yml");
                if (stream != null) Files.copy(stream, configFile.toPath());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authConfig = YamlConfiguration.loadConfiguration(configFile);

        if (hasLingoSupport())initializeLingo();
        initializeStorageConnection();
    }

    // This is currently without any function :P
    private void initializeLingo(){

    }

    private void initializeStorageConnection(){
        String configPath = ConfigPaths.DATABASE_SECTION + ".";
        if (isServerOwnedStorage()) LunaBukkit.setStorageProvider(new ConnectionProvider(
                (String) readFromConfig(configPath + "host"),
                (String) readFromConfig(configPath + "user"),
                (String) readFromConfig(configPath + "password"),
                (int) readFromConfig(configPath + "port"),
                (String) readFromConfig(configPath + "database"),
                (int) readFromConfig(configPath + "pool-size"),
                (boolean) readFromConfig(configPath + "use-ssl")
        ));
    }

    private boolean hasLingoSupport(){
        Object lingoSupport = readFromConfig(ConfigPaths.LINGO_SUPPORT);
        return lingoSupport != null && (boolean) lingoSupport;
    }

    private boolean isServerOwnedStorage(){
        String ownStorage = (String) readFromConfig(ConfigPaths.DATABASE_SECTION);
        return ownStorage.equalsIgnoreCase("h2");
    }

    private Object readFromConfig(ConfigPaths key){
        return authConfig.get(key.getKey());
    }
    private Object readFromConfig(String key){
        return authConfig.get(key);
    }

    enum ConfigPaths{
        LINGO_SUPPORT("use-lingo-support"),
        DATABASE_SECTION("database-credentials"),
        DATABASE_HOST(".database-host");

        String key;

        ConfigPaths(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

}
