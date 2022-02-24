package be4rjp.crafterscut.api.gui.map;

import be4rjp.crafterscut.api.CCPlayer;
import be4rjp.crafterscut.api.editor.movie.MovieEditor;
import be4rjp.crafterscut.api.gui.map.component.MapButtonComponent;
import be4rjp.crafterscut.api.gui.map.component.MapComponent;
import be4rjp.crafterscut.api.gui.map.component.TimelineComponent;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class MovieEditGUIRenderer extends MapGUIRenderer{
    
    private final MovieEditor movieEditor;
    
    private final TimelineComponent timelineComponent;
    
    private final MoviePlayer moviePlayer;
    
    public MovieEditGUIRenderer(MovieEditor movieEditor){
        super(movieEditor.getPlayer());
        this.movieEditor = movieEditor;
        this.timelineComponent = new TimelineComponent(20, 59, 124, 118, movieEditor);
        
        this.moviePlayer = new MoviePlayer(movieEditor.getMovie(), movieEditor.getPlayer().getPlayer(), e -> {/**/});
    
        NEXT = new MapButtonComponent("§116;NEXT", "NEXT", true, 19, 36, (ccPlayer, mapComponent) -> {
            timelineComponent.setCurrentTick(timelineComponent.getCurrentTick() + 1);
        });
        BACK = new MapButtonComponent("§116;BACK", "BACK", true, 50, 36, (ccPlayer, mapComponent) -> {
            timelineComponent.setCurrentTick(timelineComponent.getCurrentTick() - 1);
        });
    }
    
    public MovieEditor getMovieEditor() {return movieEditor;}
    
    private final MapButtonComponent buttonComponent = new MapButtonComponent("§116;Test", "This is test!", true, 64, 64, (ccPlayer, clicked) -> {
        ccPlayer.getPlayer().sendMessage("CLICKED!");
    
        MapButtonComponent button = (MapButtonComponent) clicked;
        button.setPressed(!button.isPressed());
    });
    
    
    private final MapButtonComponent START_BUTTON = new MapButtonComponent("", "Movie start", true, 19, 36, (ccPlayer, mapComponent) -> {
    
    });
    
    private final MapButtonComponent NEXT;
    
    private final MapButtonComponent BACK;
    
    @Override
    public void render(CanvasBuffer canvasBuffer, List<MapComponent> mapComponentList) {
        //MapGUIRenderer.drawSquare(canvasBuffer, 0, 14, 128, 15, (byte) 24);
        //MapGUIRenderer.drawSquare(canvasBuffer, 0, 16, 128, 16, (byte) 116);
        MapGUIRenderer.drawSquare(canvasBuffer, 0, 30, 128, 31, (byte) 24);
        MapGUIRenderer.drawSquare(canvasBuffer, 0, 32, 128, 32, (byte) 116);
        
        MapGUIRenderer.drawHole(canvasBuffer, 18, 35, 126, 50);
        MapGUIRenderer.drawHole(canvasBuffer, 18, 51, 126, 126);
        MapGUIRenderer.drawHole(canvasBuffer, 2, 35, 16, 126);
        
        MapGUIRenderer.drawSquare(canvasBuffer, 19, 58, 125, 58, (byte) 87);
        MapGUIRenderer.drawSquare(canvasBuffer, 19, 74, 125, 74, (byte) 87);
        MapGUIRenderer.drawSquare(canvasBuffer, 19, 90, 125, 90, (byte) 87);
        MapGUIRenderer.drawSquare(canvasBuffer, 19, 106, 125, 106, (byte) 87);
        MapGUIRenderer.drawSquare(canvasBuffer, 19, 122, 125, 122, (byte) 87);
    
        MapGUIRenderer.drawSquare(canvasBuffer, 34, 52, 34, 125, (byte) 68);
        
        timelineComponent.render(canvasBuffer,  mapComponentList);
        
        mapComponentList.add(NEXT);
        mapComponentList.add(BACK);
        
        //mapComponentList.add(buttonComponent);
    }
}
