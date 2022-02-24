package be4rjp.crafterscut.api.gui.map.component;

import be4rjp.crafterscut.api.data.cut.Cut;
import be4rjp.crafterscut.api.editor.movie.MovieEditor;
import be4rjp.crafterscut.api.gui.map.CanvasBuffer;

import java.util.ArrayList;
import java.util.List;

public class TimelineComponent extends MapComponent{
    
    private final int endX;
    private final int endZ;
    
    private final int width;
    
    private final MovieEditor movieEditor;
    
    private final List<MapComponent> currentLineComponents = new ArrayList<>();
    
    private int currentLine = 0;
    private int currentTick = 0;
    
    private int startTick = -14;
    private int endTick = 105;
    
    public TimelineComponent(int startX, int startZ, int endX, int endZ, MovieEditor movieEditor) {
        super(startX, startZ, null);
        this.endX = endX;
        this.endZ = endZ;
        this.movieEditor = movieEditor;
        this.width = endX - startX;
    }
    
    public void setCurrentTick(int tick){
        currentTick = tick;
        startTick = currentTick - 14;
        endTick = currentTick + 105;
    }
    
    public int getCurrentTick() {return currentTick;}
    
    public int getEndX() {return endX;}
    
    public int getEndZ() {return endZ;}
    
    public MovieEditor getMovieEditor() {return movieEditor;}
    
    public int getStartTick() {return startTick;}
    
    public int getEndTick() {return endTick;}
    
    public boolean isInRange(int tick){return startTick <= tick && tick <= endTick;}
    
    
    public void render(CanvasBuffer canvasBuffer, List<MapComponent> mapComponentList) {
        
        for(int lane = currentLine; lane < currentLine + 4; lane++){
            List<Cut> laneList = movieEditor.getLaneList(lane);
            
            for(Cut cut : laneList){
                if(cut.isDuplicate(this)){
                    int csX = Math.max(this.x, (cut.getStartTick() - currentTick) + this.x + 15);
                    int ceX = Math.min(endX, (cut.getEndTick() - (currentTick + 90)) + endZ);
                    
                    MapLaneComponent laneComponent = new MapLaneComponent("", cut.getName(), true, csX, lane * 16 + 47, ceX - csX, (byte) 20, csX != this.x, ceX != endX, (ccPlayer, mapComponent) -> {
                    
                    });
                    
                    mapComponentList.add(laneComponent);
                }
            }
        }
        
        
        
    }
    
    @Override
    public void setPixels(CanvasBuffer canvasBuffer) {/**/}
    
    @Override
    public MapComponentBoundingBox getBoundingBox() {
        return null;
    }
}
