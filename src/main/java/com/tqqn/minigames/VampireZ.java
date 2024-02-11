package com.tqqn.minigames;

import com.tqqn.minigames.modules.ModuleManager;
import com.tqqn.minigames.modules.nms.v1_20_R3.v1_20_R3;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class VampireZ extends JavaPlugin {

    private ModuleManager moduleManager;
    private final String prefix = "[VampireZ] ";
    private static v1_20_R3 nms;

    @Override
    public void onLoad() {
        moduleManager = new ModuleManager(this);
    }
    @Override
    public void onEnable() {
        moduleManager.init();
        nms = new v1_20_R3(this);
    }

    @Override
    public void onDisable() {
        moduleManager.disable();
    }

    public static v1_20_R3 getNMS() {
        return nms;
    }
}
