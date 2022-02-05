package be4rjp.crafterscut.listener;

import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.data.DataSerializer;
import be4rjp.crafterscut.api.data.cut.PlayerCut;
import be4rjp.crafterscut.api.data.movie.Movie;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import be4rjp.crafterscut.api.recorder.EntityCutRecorder;
import be4rjp.crafterscut.api.util.SkinManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.List;

public class TestEventListener implements Listener {
    
    private static final Movie movie;
    private static PlayerCut playerCut;
    private static EntityCutRecorder recorder;
    
    static {
        movie = new Movie();
        movie.setName("test-movie");
        movie.setWorldName("world");
    }
    
    private int i = 0;
    
    @EventHandler
    public void onClick(PlayerInteractEvent event){
        i++;
        Player player = event.getPlayer();
    
        ItemStack itemStack = event.getItem();
        if(itemStack == null) return;
    
        CraftersCutAPI api = CraftersCutAPI.getInstance();
        
        if(itemStack.getType() == Material.EMERALD){
            playerCut = new PlayerCut();
            playerCut.setName("test" + i);
            playerCut.setEntityName("NPC");
    
            SkinManager.getSkinAtAsync(player.getUniqueId().toString()).thenAccept(skin -> {
                playerCut.setSkinValue(skin[0]);
                playerCut.setSkinSignature(skin[1]);
    
                recorder = new EntityCutRecorder(playerCut, player, 0);
                recorder.runAtAsyncThread();
            });
        }
    
        if(itemStack.getType() == Material.REDSTONE){
            recorder.cancel();
            movie.addCut(playerCut);
        }
        
        if(itemStack.getType() == Material.DIAMOND){
            MoviePlayer moviePlayer = api.createMoviePlayer(movie, player);
            moviePlayer.runAtAsyncThread();
        }
    
        if(itemStack.getType() == Material.GREEN_STAINED_GLASS){
            File file = new File("test.json");
            
            try {
                List<String> lines = Files.readLines(file, Charset.defaultCharset());
                StringBuilder sb = new StringBuilder();
                lines.forEach(sb::append);
                DataSerializer serializer = new DataSerializer();
                serializer.fromJson(sb.toString());
                
                movie.deserialize(serializer);
                
            }catch (Exception e){e.printStackTrace();}
        }
    
        if(itemStack.getType() == Material.BLACK_STAINED_GLASS){
            File file = new File("test.json");
            
            try{
                FileWriter filewriter = new FileWriter(file);
                filewriter.write(movie.serialize().toJson());
                filewriter.close();
            }catch (Exception e){e.printStackTrace();}
            
        }
    }
    
}
