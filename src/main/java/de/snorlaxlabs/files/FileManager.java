package de.snorlaxlabs.files;

import de.snorlaxlabs.LunaAuthProvider;
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
        configFile = new File(dataFolder, "auth-config.yml");
        try {
            if (!configFile.exists()) {
                InputStream stream = LunaAuthProvider.getProvider().getResource("auth-config.yml");
                if (stream != null) Files.copy(stream, configFile.toPath());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authConfig = YamlConfiguration.loadConfiguration(configFile);

        if (hasLingoSupport())initializeLingo();
    }

    private void initializeLingo(){

    }

    private boolean hasLingoSupport(){
        Object lingoSupport = readFromConfig(ConfigPaths.LINGO_SUPPORT);
        return lingoSupport != null && (boolean) lingoSupport;
    }

    private Object readFromConfig(ConfigPaths key){
        return authConfig.get(key.getKey());
    }

    enum ConfigPaths{
        LINGO_SUPPORT("use-lingo-support");

        String key;

        ConfigPaths(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

}
