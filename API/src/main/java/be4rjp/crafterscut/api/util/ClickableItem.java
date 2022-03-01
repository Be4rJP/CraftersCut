package be4rjp.crafterscut.api.util;

import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.nms.INMSHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ClickableItem {
    
    public static final String NBT_TAG = "CraftersCutClickableItem";
    
    private static Map<UUID, ClickableItem> clickableItemMap = new ConcurrentHashMap<>();
    
    public static ItemStack create(ItemStack itemStack, Consumer<PlayerInteractEvent> eventConsumer){
        CraftersCutAPI api = CraftersCutAPI.getInstance();
        INMSHandler nmsHandler = api.getNMSHandler();
        UUID uuid = UUID.randomUUID();
        itemStack = nmsHandler.setItemNBTString(itemStack, NBT_TAG, uuid.toString());
        
        ClickableItem clickableItem = new ClickableItem(uuid, itemStack, eventConsumer);
        clickableItemMap.put(uuid, clickableItem);
        
        return itemStack;
    }
    
    public static ClickableItem getClickableItem(UUID uuid){return clickableItemMap.get(uuid);}
    
    
    private final UUID uuid;
    
    private final ItemStack itemStack;
    
    private final Consumer<PlayerInteractEvent> eventConsumer;
    
    private ClickableItem(UUID uuid, ItemStack itemStack, Consumer<PlayerInteractEvent> eventConsumer){
        this.uuid = uuid;
        this.itemStack = itemStack;
        this.eventConsumer = eventConsumer;
    }
    
    public void accept(PlayerInteractEvent event){
        if(eventConsumer != null){
            eventConsumer.accept(event);
        }
    }
}
