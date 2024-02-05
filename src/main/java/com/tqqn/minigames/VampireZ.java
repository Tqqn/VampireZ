package com.tqqn.minigames;

import com.tqqn.minigames.modules.ModuleManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class VampireZ extends JavaPlugin {

    private ModuleManager moduleManager;
    private final String prefix = "[VampireZ] ";

    @Override
    public void onLoad() {
        moduleManager = new ModuleManager(this);
    }
    @Override
    public void onEnable() {
        moduleManager.init();
    }

    @Override
    public void onDisable() {
        moduleManager.disable();
    }
}
