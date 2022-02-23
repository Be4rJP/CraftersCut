package be4rjp.crafterscut.api.editor.movie;

import be4rjp.crafterscut.api.CCPlayer;
import be4rjp.crafterscut.api.data.cut.CameraCut;
import be4rjp.crafterscut.api.data.cut.Cut;
import be4rjp.crafterscut.api.data.movie.Movie;
import be4rjp.crafterscut.api.gui.map.MovieEditGUIRenderer;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieEditor {
    
    private final Movie movie;
    
    private final Set<CCPlayer> players = new HashSet<>();
    
    private final Int2ObjectArrayMap<List<Cut>> cutLaneMap = new Int2ObjectArrayMap<>();
    
    private int maxLane = 0;

    private MovieEditGUIRenderer renderer;
    
    public MovieEditor(Movie movie){
        this.movie = movie;
        
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
    }
    
    public Movie getMovie() {return movie;}
    
    public int getMaxLane() {return maxLane;}
    
    public void updateMaxLane(int lane){maxLane = Math.max(lane, maxLane);}
    
    public void addEditPlayer(CCPlayer ccPlayer){
        players.add(ccPlayer);
        if(renderer == null){
            renderer = new MovieEditGUIRenderer(this);
            renderer.start();
        }
        renderer.addPlayer(ccPlayer);
    }
    
    public List<Cut> getLaneList(int lane){
        updateMaxLane(lane);
        return cutLaneMap.computeIfAbsent(lane, l -> new ArrayList<>());
    }
    
    public void removeEditPlayer(CCPlayer ccPlayer){
        players.remove(ccPlayer);
        if(renderer != null) renderer.removePlayer(ccPlayer);
    }
    
}