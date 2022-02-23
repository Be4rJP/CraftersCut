package be4rjp.crafterscut.api.gui.map;

import be4rjp.crafterscut.api.editor.movie.MovieEditor;
import be4rjp.crafterscut.api.gui.map.component.MapButtonComponent;
import be4rjp.crafterscut.api.gui.map.component.MapComponent;
import be4rjp.crafterscut.api.gui.map.component.MapLaneComponent;
import be4rjp.crafterscut.api.gui.map.component.TimelineComponent;

import java.util.List;

public class MovieEditGUIRenderer extends MapGUIRenderer{
    
    private final MovieEditor movieEditor;
    
    private final TimelineComponent timelineComponent;
    
    public MovieEditGUIRenderer(MovieEditor movieEditor){
        this.movieEditor = movieEditor;
        this.timelineComponent = new TimelineComponent(20, 59, 124, 118, movieEditor);
    }
    
    public MovieEditor getMovieEditor() {return movieEditor;}
    
    private final MapButtonComponent buttonComponent = new MapButtonComponent("§116;Test", "This is test!", true, 64, 64, (ccPlayer, clicked) -> {
        ccPlayer.getPlayer().sendMessage("CLICKED!");
    
        MapButtonComponent button = (MapButtonComponent) clicked;
        button.setPressed(!button.isPressed());
    });
    
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
        MapGUIRenderer.drawSquare(canvasBuffer, 19, 73, 125, 73, (byte) 87);
        MapGUIRenderer.drawSquare(canvasBuffer, 19, 88, 125, 88, (byte) 87);
        MapGUIRenderer.drawSquare(canvasBuffer, 19, 103, 125, 103, (byte) 87);
        MapGUIRenderer.drawSquare(canvasBuffer, 19, 118, 125, 118, (byte) 87);
    
        MapGUIRenderer.drawSquare(canvasBuffer, 34, 52, 34, 125, (byte) 68);
        
        timelineComponent.render(canvasBuffer,  mapComponentList);
        
        //mapComponentList.add(buttonComponent);
    }
}
