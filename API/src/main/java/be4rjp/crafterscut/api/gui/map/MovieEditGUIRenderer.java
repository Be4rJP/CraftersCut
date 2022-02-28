package be4rjp.crafterscut.api.gui.map;

import be4rjp.crafterscut.api.editor.movie.MovieEditor;
import be4rjp.crafterscut.api.gui.map.component.MapButtonComponent;
import be4rjp.crafterscut.api.gui.map.component.MapComponent;
import be4rjp.crafterscut.api.gui.map.component.TimelineComponent;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;

import java.util.List;

public class MovieEditGUIRenderer extends MapGUIRenderer{
    
    private final MovieEditor movieEditor;
    
    private final TimelineComponent timelineComponent;
    
    private MoviePlayer moviePlayer;
    
    public MovieEditGUIRenderer(MovieEditor movieEditor){
        super(movieEditor.getPlayer());
        this.movieEditor = movieEditor;
        this.timelineComponent = new TimelineComponent(20, 59, 124, 118, movieEditor);
        
        this.moviePlayer = new MoviePlayer(movieEditor.getMovie(), movieEditor.getPlayer(), e -> {/**/});
        moviePlayer.setAutoCancel(false);
    
        NEXT = new MapButtonComponent("§116;N", "NEXT", true, 19, 39, (ccPlayer, mapComponent) -> {
            int tick = timelineComponent.getCurrentTick() + 1;
            timelineComponent.setCurrentTick(tick);
            moviePlayer.setTick(tick);
        });
        BACK = new MapButtonComponent("§116;B", "BACK", true, 35, 39, (ccPlayer, mapComponent) -> {
            int tick = timelineComponent.getCurrentTick() - 1;
            timelineComponent.setCurrentTick(tick);
            moviePlayer.setTick(tick);
        });
    
        START = new MapButtonComponent("§116;S", "START", true, 50, 39, (ccPlayer, mapComponent) -> {
            /*
            try{
                if(!moviePlayer.isCancelled()) {
                    moviePlayer.cancel();
                    moviePlayer.playOnEnd();
                    moviePlayer = new MoviePlayer(movieEditor.getMovie(), ccPlayer, e -> {});
                }
            }catch (Exception e){e.printStackTrace();}*/
            
            moviePlayer.initializeAtMainThread();
            moviePlayer.runAtAsyncThread();
            moviePlayer.restart();
        });
    
        PAUSE = new MapButtonComponent("§116;P", "PAUSE", true, 65, 39, (ccPlayer, mapComponent) -> {
            moviePlayer.pause();
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
    
    private final MapButtonComponent START;
    
    private final MapButtonComponent PAUSE;
    
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
        
        timelineComponent.setCurrentTick(moviePlayer.getTick());
        
        timelineComponent.render(canvasBuffer,  mapComponentList);
        
        mapComponentList.add(NEXT);
        mapComponentList.add(BACK);
        mapComponentList.add(START);
        mapComponentList.add(PAUSE);
        
        
        
        //mapComponentList.add(buttonComponent);
    }
}
