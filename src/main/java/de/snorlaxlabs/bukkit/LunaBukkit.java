package de.snorlaxlabs.bukkit;

import de.snorlaxlabs.bukkit.commands.AuthCommand;
import de.snorlaxlabs.bukkit.files.FileManager;
import de.snorlaxlabs.storage.ConnectionProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class LunaBukkit extends JavaPlugin {

    private static LunaBukkit instance;
    private static ConnectionProvider connectionProvider;
    private FileManager fileManager;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Load pluginfiles
        fileManager = new FileManager(getDataFolder());
    }

    @Override
    public void onDisable() {
        if (connectionProvider != null) connectionProvider.close();
    }

    private void commandRegistration(){
        getCommand("auth").setExecutor(new AuthCommand());
        getCommand("auth").setTabCompleter(new AuthCommand());
    }

    public static ConnectionProvider getStorageProvider() {
        return connectionProvider;
    }

    public static void setStorageProvider(ConnectionProvider connectionProvider) {
        LunaBukkit.connectionProvider = connectionProvider;
    }

    public static LunaBukkit getProvider() {
        return instance;
    }
}
