package be4rjp.crafterscut.api.util;

import be4rjp.crafterscut.api.CraftersCutAPI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class SkinManager {
    
    public static CompletableFuture<String[]> getSkinAtAsync(String uuid){
        CompletableFuture<String[]> completableFuture = new CompletableFuture<>();
        
        Bukkit.getScheduler().runTaskAsynchronously(CraftersCutAPI.getInstance().getPlugin(), () -> {
            try {
                URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
                InputStreamReader reader = new InputStreamReader(url.openStream());
                JsonObject property = new JsonParser().parse(reader).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
                String texture = property.get("value").getAsString();
                String signature = property.get("signature").getAsString();
                String[] skin = {texture, signature};
                completableFuture.complete(skin);
            } catch (Exception e) {
                CraftersCutAPI.getInstance().getPlugin().getLogger().warning("An error occurred when querying Mojang's session server for skin information.");
                e.printStackTrace();
            }
        });
        
        return completableFuture;
    }
    
}
