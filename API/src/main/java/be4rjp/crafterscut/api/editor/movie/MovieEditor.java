package be4rjp.crafterscut.api.editor.movie;

import be4rjp.artgui.button.ItemBuilder;
import be4rjp.crafterscut.api.CCPlayer;
import be4rjp.crafterscut.api.data.cut.CameraCut;
import be4rjp.crafterscut.api.data.cut.Cut;
import be4rjp.crafterscut.api.data.movie.Movie;
import be4rjp.crafterscut.api.editor.ItemClickBase;
import be4rjp.crafterscut.api.editor.cut.CutEditor;
import be4rjp.crafterscut.api.gui.map.MovieEditGUIRenderer;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import be4rjp.crafterscut.api.util.ClickableItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.MapMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieEditor implements ItemClickBase {
    
    private final Movie movie;
    
    private final CCPlayer ccPlayer;
    
    private final Int2ObjectArrayMap<List<Cut>> cutLaneMap = new Int2ObjectArrayMap<>();
    
    private int maxLane = 0;

    private MoviePlayer moviePlayer;
    
    public MovieEditor(Movie movie, CCPlayer ccPlayer){
        this.movie = movie;
        this.ccPlayer = ccPlayer;
        
        for(Cut cut : movie.getCutList()){
            if(cut instanceof CameraCut){
                getLaneList(0).add(cut);
                continue;
            }
            
            if(cut.getLane() == -1){
                int lane = 1;
                loop : while (true) {
                    for (Cut laneSetCut : getLaneList(lane)) {
                        if (laneSetCut.isDuplicate(cut)) {
                            lane++;
                            continue loop;
                        }
                    }
                    break;
                }
                
                cut.setLane(lane);
                getLaneList(lane).add(cut);
                
                maxLane = Math.max(lane, maxLane);
            }else{
                getLaneList(cut.getLane()).add(cut);
                maxLane = Math.max(cut.getLane(), maxLane);
            }
        }
        
        this.moviePlayer = new MoviePlayer(movie, ccPlayer, e -> {});
    }
    
    public Movie getMovie() {return movie;}
    
    public int getMaxLane() {return maxLane;}
    
    public void updateMaxLane(int lane){maxLane = Math.max(lane, maxLane);}
    
    public List<Cut> getLaneList(int lane){
        updateMaxLane(lane);
        return cutLaneMap.computeIfAbsent(lane, l -> new ArrayList<>());
    }
    
    public CCPlayer getPlayer() {return ccPlayer;}
    
    public MoviePlayer getMoviePlayer() {return moviePlayer;}
    
    @Deprecated
    public void recreateMoviePlayer(int tick){
        try{
            if(!moviePlayer.isCancelled()) {
                moviePlayer.cancel();
                moviePlayer.playOnEnd();
                moviePlayer = new MoviePlayer(movie, ccPlayer, e -> {});
                moviePlayer.setTick(tick);
            }
        }catch (Exception e){/**/}
    }
    
    private CutEditor cutEditor;
    
    public void setEditingCut(CutEditor cutEditor){
        if(this.cutEditor != null){
            this.cutEditor.setEditing(false);
        }
        this.cutEditor = cutEditor;
        cutEditor.setEditing(true);
    }
    
    public CutEditor getEditingCUt() {return cutEditor;}
    
    @Override
    public void setItems(CCPlayer ccPlayer) {
        ItemStack item = new ItemStack(Material.FILLED_MAP);
        MapMeta mapMeta = (MapMeta) item.getItemMeta();
        mapMeta.setMapId(0);
        item.setItemMeta(mapMeta);
        Player player = ccPlayer.getPlayer();
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        inventory.setItem(0, item);
    
        inventory.setItem(1, ClickableItem.create(new ItemBuilder(Material.EMERALD).name("&aStart / Stop").build(), event -> {
            if(moviePlayer.isPaused()){
                moviePlayer.restart();
            }else{
                moviePlayer.pause();
            }
        }));
    }
}
