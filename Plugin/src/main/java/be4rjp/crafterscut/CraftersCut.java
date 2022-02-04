package be4rjp.crafterscut;

import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.nms.INMSHandler;
import be4rjp.crafterscut.impl.ImplCraftersCutAPI;
import be4rjp.crafterscut.nms.NMSManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class CraftersCut extends JavaPlugin {

    private static CraftersCut plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        setup();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private static void setup(){
        INMSHandler nmsHandler = NMSManager.createNMSHandlerInstance();
        try{
            Field instance = CraftersCutAPI.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, new ImplCraftersCutAPI(plugin, nmsHandler));
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Failed to create api instance.");
        }
    }

    public static CraftersCut getPlugin() {return plugin;}
}
