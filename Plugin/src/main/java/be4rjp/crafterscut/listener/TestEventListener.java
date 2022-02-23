package be4rjp.crafterscut.listener;

import be4rjp.crafterscut.api.CCPlayer;
import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.data.DataSerializer;
import be4rjp.crafterscut.api.data.cut.PlayerCut;
import be4rjp.crafterscut.api.data.movie.Movie;
import be4rjp.crafterscut.api.editor.movie.MovieEditor;
import be4rjp.crafterscut.api.gui.map.MovieEditGUIRenderer;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import be4rjp.crafterscut.api.recorder.EntityCutRecorder;
import be4rjp.crafterscut.api.util.SkinManager;
import be4rjp.crafterscut.api.util.math.BezierCurve3D;
import be4rjp.crafterscut.api.util.math.Vec2d;
import com.google.common.io.Files;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TestEventListener implements Listener {
    
    private static final Movie movie;
    private static PlayerCut playerCut;
    private static EntityCutRecorder recorder;
    
    private static final List<Vec2d> nodes = new ArrayList<>();
    
    private static BezierCurve3D endCurve = null;
    
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
    
        if(itemStack.getType() == Material.LAPIS_LAZULI){
            Location loc = player.getLocation();
            
            if(endCurve == null){
                endCurve = new BezierCurve3D(loc.toVector(), loc.toVector().add(new Vector(0.1, 0.1, 0.1)));
                
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        
                        if(player.getInventory().getItemInMainHand().getType() != Material.LAPIS_LAZULI) return;
                        
                        Location location = player.getLocation();
                        endCurve.moveEndAnchorToExperiment(location.getX(), location.getY(), location.getZ());
                        
                        BezierCurve3D current = endCurve;
                        while (true){
    
                            for(double t = 0.0; t < 1.0; t += 0.025){
                                Vector pos = current.getPosition(t);
        
                                Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 1);
                                player.spawnParticle(Particle.REDSTONE, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0, 0, dustOptions);
                            }
    
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.BLUE, 1);
                            Vector start = current.getStartAnchor();
                            Vector end = current.getEndAnchor();
                            Vector startC = current.getStartControl();
                            Vector endC = current.getEndControl();
                            player.spawnParticle(Particle.REDSTONE, start.getX(), start.getY(), start.getZ(), 0, 0, 0, 0, dustOptions);
                            player.spawnParticle(Particle.REDSTONE, end.getX(), end.getY(), end.getZ(), 0, 0, 0, 0, dustOptions);
                            player.spawnParticle(Particle.REDSTONE, startC.getX(), startC.getY(), startC.getZ(), 0, 0, 0, 0, dustOptions);
                            player.spawnParticle(Particle.REDSTONE, endC.getX(), endC.getY(), endC.getZ(), 0, 0, 0, 0, dustOptions);
                            
                            BezierCurve3D previous = current.getPrevious();
                            if(previous == null){
                                break;
                            }
                            
                            current = previous;
                            
                        }
                        
                        
                    }
                }.runTaskTimerAsynchronously(CraftersCutAPI.getInstance().getPlugin(), 0, 1);
            }else{
                endCurve = endCurve.createNextBezierCurve(player.getLocation().toVector());
            }
        }
    
        if(itemStack.getType() == Material.PAPER){
            ItemStack item = new ItemStack(Material.FILLED_MAP);
            MapMeta mapMeta = (MapMeta) item.getItemMeta();
            mapMeta.setMapId(0);
            item.setItemMeta(mapMeta);
            
            player.getInventory().setItemInMainHand(item);
    
            MovieEditGUIRenderer renderer = new MovieEditGUIRenderer(new MovieEditor(movie));
            CCPlayer ccPlayer = CCPlayer.getCCPlayer(player);
            renderer.addPlayer(ccPlayer);
            
            renderer.start();
        }
    }
    
}
