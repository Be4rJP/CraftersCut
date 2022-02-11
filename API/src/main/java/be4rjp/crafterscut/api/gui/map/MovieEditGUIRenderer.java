package be4rjp.crafterscut.api.gui.map;

import be4rjp.crafterscut.api.gui.map.component.MapButtonComponent;
import be4rjp.crafterscut.api.gui.map.component.MapComponent;

import java.util.List;

public class MovieEditGUIRenderer extends MapGUIRenderer{
    
    private final MapButtonComponent buttonComponent = new MapButtonComponent("§116;Test", "This is test!", true, 64, 64, (ccPlayer, clicked) -> {
        ccPlayer.getPlayer().sendMessage("CLICKED!");
    
        MapButtonComponent button = (MapButtonComponent) clicked;
        button.setPressed(!button.isPressed());
    });
    
    @Override
    public void render(CanvasBuffer canvasBuffer, List<MapComponent> mapComponentList) {
        MapGUIRenderer.drawSquare(canvasBuffer, 0, 14, 128, 15, (byte) 24);
        MapGUIRenderer.drawSquare(canvasBuffer, 0, 16, 128, 16, (byte) 116);
        MapGUIRenderer.drawSquare(canvasBuffer, 0, 30, 128, 31, (byte) 24);
        MapGUIRenderer.drawSquare(canvasBuffer, 0, 32, 128, 32, (byte) 116);
        
        mapComponentList.add(buttonComponent);
    }
}
