package com.tqqn.minigames;

import com.tqqn.minigames.modules.ModuleManager;
import com.tqqn.minigames.modules.nms.ReflectionLayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main plugin class for VampireZ.
 */
@Getter
public final class VampireZ extends JavaPlugin {

    private ModuleManager moduleManager;
    private final String prefix = "[VampireZ] ";
    @Getter private static ReflectionLayer reflectionLayer;

    /**
     * Called when the plugin is loaded.
     */
    @Override
    public void onLoad() {
        moduleManager = new ModuleManager(this);
    }

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        moduleManager.init();
        findReflectionLayer();
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        moduleManager.disable();
    }

    /**
     * Finds and initializes the reflection layer based on the server's Bukkit version.
     */
    public void findReflectionLayer() {
        String bukkitVersion = Bukkit.getServer().getClass().getPackage().getName();
        String version = bukkitVersion.substring(bukkitVersion.lastIndexOf('.') + 1);
        try {
            Class<?> nmsClass = Class.forName("com.tqqn.modules.nms." + version + "." + version);
            Bukkit.getLogger().info("Using reflection layer for version " + version);
            reflectionLayer = (ReflectionLayer) nmsClass.getConstructors()[0].newInstance();
        } catch (Exception ignored) {
            Bukkit.getLogger().info("This version is not supported - " + version);
            Bukkit.getServer().shutdown();
        }
    }
}
