package de.snorlaxlabs;

import de.snorlaxlabs.files.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LunaAuthProvider extends JavaPlugin {

    private static LunaAuthProvider instance;
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

    }

    public static LunaAuthProvider getProvider() {
        return instance;
    }
}
