package be4rjp.crafterscut.api;

import be4rjp.crafterscut.api.gui.map.MapGUIRenderer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CCPlayer {
    
    protected static final Map<Player, CCPlayer> playerMap = new ConcurrentHashMap<>();
    
    public static CCPlayer getCCPlayer(Player player){return playerMap.get(player);}
    
    
    
    protected final Player player;
    
    protected MapGUIRenderer mapGUIRenderer = null;
    
    public CCPlayer(Player player){
        this.player = player;
    }
    
    public Player getPlayer() {return player;}
    
    public MapGUIRenderer getMapGUIRenderer() {return mapGUIRenderer;}
    
    public void setMapGUIRenderer(MapGUIRenderer mapGUIRenderer) {this.mapGUIRenderer = mapGUIRenderer;}
    
    public void sendPacket(Object packet){CraftersCutAPI.getInstance().getNMSHandler().sendPacket(player, packet);}
}
