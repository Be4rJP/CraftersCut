package be4rjp.crafterscut.nms;

import be4rjp.crafterscut.api.nms.INMSHandler;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;

public class NMSManager {
    
    public static INMSHandler createNMSHandlerInstance(){
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);
        
        try{
            Class<?> nmsHandlerClass = Class.forName("be4rjp.crafterscut.nms." + version + ".NMSHandler");
            return (INMSHandler) nmsHandlerClass.getConstructor().newInstance();
            
        }catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e){
            e.printStackTrace();
            
            throw new IllegalStateException("This version is not supported!"
                    + System.lineSeparator() + "Server version : " + version);
        }
    }
    
}
