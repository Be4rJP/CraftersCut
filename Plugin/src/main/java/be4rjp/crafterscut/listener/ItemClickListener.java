package be4rjp.crafterscut.listener;

import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.nms.INMSHandler;
import be4rjp.crafterscut.api.util.ClickableItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ItemClickListener implements Listener {
    
    @EventHandler
    public void onItemClick(PlayerInteractEvent event){
        ItemStack clickedItem = event.getItem();
        if(clickedItem == null) return;
    
        INMSHandler nmsHandler = CraftersCutAPI.getInstance().getNMSHandler();
        String id = nmsHandler.getItemNBTString(clickedItem, ClickableItem.NBT_TAG);
        if(id == null) return;
    
        UUID uuid = UUID.fromString(id);
        ClickableItem clickableItem = ClickableItem.getClickableItem(uuid);
        if(clickableItem == null) return;
        
        clickableItem.accept(event);
    }
    
}
