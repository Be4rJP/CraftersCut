package be4rjp.crafterscut.listener;

import be4rjp.crafterscut.api.CCPlayer;
import be4rjp.crafterscut.api.gui.map.MapGUIRenderer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

public class PlayerClickListener implements Listener {
    
    @EventHandler
    public void onMapClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
    
        ItemStack itemStack = event.getItem();
        if(itemStack == null) return;
        if(itemStack.getType() != Material.FILLED_MAP) return;
    
        MapMeta mapMeta = (MapMeta) itemStack.getItemMeta();
        if(mapMeta == null) return;
        
        if(mapMeta.getMapId() != 0) return;
        
        CCPlayer ccPlayer = CCPlayer.getCCPlayer(player);
        if(ccPlayer == null) return;
    
        MapGUIRenderer mapGUIRenderer = ccPlayer.getMapGUIRenderer();
        if(mapGUIRenderer == null) return;
        
        mapGUIRenderer.getPlayerCursor(ccPlayer).onClick();
    }
    
}
