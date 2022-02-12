package be4rjp.crafterscut.api.gui.map;

import be4rjp.crafterscut.api.CCPlayer;
import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.gui.map.component.MapComponent;
import be4rjp.crafterscut.api.gui.map.component.MapComponentBoundingBox;
import be4rjp.crafterscut.api.util.math.Vec2f;
import org.bukkit.map.MapCursor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MapGUIRenderer extends BukkitRunnable {
    
    protected Object packet;
    
    public Object getPacket() {return packet;}
    
    protected final Set<CCPlayer> ccPlayers = new HashSet<>();
    
    protected boolean isShowAllComponent = true;
    
    public void addPlayer(CCPlayer ccPlayer){
        ccPlayers.add(ccPlayer);
        ccPlayer.setMapGUIRenderer(this);
    }
    
    
    protected Map<CCPlayer, PlayerCursor> cursorMap = new ConcurrentHashMap<>();
    
    public PlayerCursor getPlayerCursor(CCPlayer ccPlayer){return cursorMap.computeIfAbsent(ccPlayer, PlayerCursor::new);}
    
    public void removePlayer(CCPlayer ccPlayer){
        ccPlayers.remove(ccPlayer);
        cursorMap.remove(ccPlayer);
        ccPlayer.setMapGUIRenderer(null);
    }
    
    public boolean isShowAllComponent() {return isShowAllComponent;}
    
    public void setShowAllComponent(boolean showAllComponent) {isShowAllComponent = showAllComponent;}
    
    @Override
    public void run() {
        List<MapComponent> mapComponents = new ArrayList<>();
        List<MapComponent> addComponent = new ArrayList<>();
        
        byte[] buffer = new byte[128 * 128];
        for(int i = 0; i < 128 * 128; i++){
            buffer[i] = 32;
        }
        CanvasBuffer canvasBuffer = new CanvasBuffer(buffer);
        
        render(canvasBuffer, mapComponents);
    
        for(MapComponent mapComponent : mapComponents){
            mapComponent.setPixels(canvasBuffer);
        }
        
        List<MapCursor> mapCursors = new ArrayList<>();
        for(CCPlayer ccPlayer : ccPlayers){
            PlayerCursor cursor = getPlayerCursor(ccPlayer);
            mapCursors.add(cursor.render(mapComponents, addComponent, canvasBuffer, isShowAllComponent));
        }
        
        for(MapComponent mapComponent : addComponent){
            mapComponent.setPixels(canvasBuffer);
        }
        
        packet = CraftersCutAPI.getInstance().getNMSHandler().createMapPacket(canvasBuffer, mapCursors);
        ccPlayers.forEach(ccPlayer -> ccPlayer.sendPacket(packet));
    }
    
    public abstract void render(CanvasBuffer canvasBuffer, List<MapComponent> mapComponentList);
    
    public void start(){this.runTaskTimerAsynchronously(CraftersCutAPI.getInstance().getPlugin(), 0, 1);}
    
    
    
    public static void drawBoundingBox(MapComponentBoundingBox boundingBox, CanvasBuffer canvasBuffer){
        drawLine(canvasBuffer, new Vec2f(boundingBox.minX, boundingBox.minY), new Vec2f(boundingBox.maxX, boundingBox.minY), (byte) 57);
        drawLine(canvasBuffer, new Vec2f(boundingBox.minX, boundingBox.minY), new Vec2f(boundingBox.minX, boundingBox.maxY), (byte) 57);
        drawLine(canvasBuffer, new Vec2f(boundingBox.maxX, boundingBox.minY), new Vec2f(boundingBox.maxX, boundingBox.maxY), (byte) 57);
        drawLine(canvasBuffer, new Vec2f(boundingBox.minX, boundingBox.maxY), new Vec2f(boundingBox.maxX, boundingBox.maxY), (byte) 57);
        drawLine(canvasBuffer, new Vec2f(boundingBox.minX - 1, boundingBox.minY - 1), new Vec2f(boundingBox.maxX + 1, boundingBox.minY - 1), (byte) 116);
        drawLine(canvasBuffer, new Vec2f(boundingBox.minX - 1, boundingBox.minY - 1), new Vec2f(boundingBox.minX - 1, boundingBox.maxY + 1), (byte) 116);
        drawLine(canvasBuffer, new Vec2f(boundingBox.maxX + 1, boundingBox.minY - 1), new Vec2f(boundingBox.maxX + 1, boundingBox.maxY + 1), (byte) 116);
        drawLine(canvasBuffer, new Vec2f(boundingBox.minX - 1, boundingBox.maxY + 1), new Vec2f(boundingBox.maxX + 1, boundingBox.maxY + 1), (byte) 116);
    }
    
    public static void drawLine(CanvasBuffer canvasBuffer, Vec2f start, Vec2f end, byte color){
        Vec2f direction = new Vec2f(end.x - start.x, end.y - start.y);
        
        float accuracy = 0.1F;
        int max = (int) (direction.length() / accuracy);
        
        Vec2f add = direction.clone().setLength(accuracy);
        Vec2f currentPosition = start.clone();
        for(int i = 0; i < max; i++){
            currentPosition.add(add);
            canvasBuffer.setPixel((int) currentPosition.x, (int) currentPosition.y, color);
        }
    }
    
    public static void drawSquare(CanvasBuffer canvasBuffer, int startX, int startZ, int endX, int endZ, byte color){
        for(int x = startX; x <= endX; x++){
            for(int z = startZ; z <= endZ; z++){
                canvasBuffer.setPixel(x, z, color);
            }
        }
    }
    
    public static void drawHole(CanvasBuffer canvasBuffer, int startX, int startZ, int endX, int endZ){
        
        drawSquare(canvasBuffer, startX, startZ, endX, endZ, (byte) 26);
        
        MapGUIRenderer.drawSquare(canvasBuffer, startX, startZ, endX, startZ, (byte) 24);
        MapGUIRenderer.drawSquare(canvasBuffer, startX, startZ, startX, endZ, (byte) 24);
        MapGUIRenderer.drawSquare(canvasBuffer, startX, endZ, endX, endZ, (byte) 33);
        MapGUIRenderer.drawSquare(canvasBuffer, endX, startZ, endX, endZ, (byte) 33);
    }
}
