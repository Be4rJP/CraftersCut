package be4rjp.crafterscut.api.gui.map;

import be4rjp.crafterscut.api.CCPlayer;
import be4rjp.crafterscut.api.gui.map.component.*;
import org.bukkit.Location;
import org.bukkit.map.MapCursor;

import java.util.ArrayList;
import java.util.List;

public class PlayerCursor {
    
    private final CCPlayer ccPlayer;
    
    private final MapGUIRenderer mapGUIRenderer;
    
    public PlayerCursor(CCPlayer ccPlayer, MapGUIRenderer mapGUIRenderer){
        this.ccPlayer = ccPlayer;
        this.mapGUIRenderer = mapGUIRenderer;
    }
    
    
    private float lastYaw;
    private float currentX = 0.0F;
    
    private int cursorPixelX = 0;
    private int cursorPixelY = 0;
    
    private boolean isShowAllComponent = false;
    private List<MapComponent> componentList = new ArrayList<>();
    
    public MapCursor render(List<MapComponent> componentList, List<MapComponent> addComponent, CanvasBuffer canvasBuffer, boolean isShowAllComponent) {
        Location location = ccPlayer.getPlayer().getLocation();
        this.isShowAllComponent = isShowAllComponent;
        this.componentList = componentList;
        
        //カーソル
        float y = location.getPitch();
        y -= 0.1F;
        y = Math.min(y, 90F);
        y = Math.max(y, 45F);
        y = ((y / 45.0F) - 1.5F) * 256.0F;
    
        float yaw = location.getYaw();
        float x = yaw - lastYaw;
        if (Math.abs(x) < 180) {
            currentX += x * 3.0F;
            currentX = Math.min(currentX, 127.9F);
            currentX = Math.max(currentX, -128.0F);
        }
        this.lastYaw = yaw;
    
    
        this.cursorPixelX = ((int) currentX + 128) >> 1;
        this.cursorPixelY = ((int) y + 128) >> 1;
        
        if (isShowAllComponent) {
            
            for (MapComponent mapComponent : componentList) {
                MapComponentBoundingBox boundingBox = mapComponent.getBoundingBox();
                if (boundingBox == null) break;
                if (boundingBox.isInBox(cursorPixelX, cursorPixelY)) {
                    if(mapComponent instanceof MapLaneComponent){
                        MapLaneComponent mapLaneComponent = (MapLaneComponent) mapComponent;
                        MapGUIRenderer.drawBoundingBox(mapComponent.getBoundingBox(), canvasBuffer, mapLaneComponent.isDrawLeft(), mapLaneComponent.isDrawRight());
                    }else {
                        MapGUIRenderer.drawBoundingBox(mapComponent.getBoundingBox(), canvasBuffer);
                    }
                    
                    if(mapComponent instanceof MapButtonComponent){
                        String popUpText = ((MapButtonComponent) mapComponent).getPopUpText();
                        if(popUpText != null){
                            MapTextComponent textComponent = new MapTextComponent(popUpText, true, cursorPixelX, cursorPixelY, null);
                            float rateX = ((float) cursorPixelX) / 128.0F;
                            float newX = ((float) textComponent.getWidth()) * rateX;
                            
                            textComponent.setX(textComponent.getX() - (int) newX);
                            
                            addComponent.add(textComponent);
                        }
                    }
                }
            }
        }
        
        return new MapCursor((byte) currentX, (byte) y, (byte) 6, MapCursor.Type.WHITE_POINTER, true);
    }
    
    public void onClick(){
        if(isShowAllComponent) {
            for (MapComponent mapComponent : componentList) {
                MapComponentBoundingBox boundingBox = mapComponent.getBoundingBox();
                if (boundingBox == null) break;
                if (boundingBox.isInBox(cursorPixelX, cursorPixelY)) {
                    mapComponent.onClick(ccPlayer);
                }
                
                if(mapComponent instanceof MapLaneComponent){
                    MapLaneComponent mapLaneComponent = (MapLaneComponent) mapComponent;
                    isShowAllComponent = false;
                    mapGUIRenderer.setSelectedLaneComponent(mapLaneComponent);
                }else{
                    isShowAllComponent = true;
                    mapGUIRenderer.setSelectedLaneComponent(null);
                }
            }
        }
    }
}
