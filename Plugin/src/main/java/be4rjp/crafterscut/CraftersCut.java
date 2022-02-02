package be4rjp.crafterscut;

import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.impl.ImplCraftersCut;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class CraftersCut extends JavaPlugin {

    private static CraftersCut plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        createAPIInstance();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private static void createAPIInstance(){
        try{
            Field instance = CraftersCutAPI.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, new ImplCraftersCut());
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Failed to create api instance.");
        }
    }

    public static CraftersCut getPlugin() {return plugin;}
}
