package be4rjp.crafterscut.listener;

import be4rjp.crafterscut.CraftersCut;
import be4rjp.crafterscut.api.CCPlayer;
import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.impl.ImplCCPlayer;
import be4rjp.crafterscut.nms.PacketHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        //Inject packet handler
        Player player = event.getPlayer();
        CCPlayer ccPlayer = ImplCCPlayer.onJoin(player);
        
        PacketHandler packetHandler = new PacketHandler(ccPlayer);
        
        try {
            ChannelPipeline pipeline = CraftersCutAPI.getInstance().getNMSHandler().getChannel(player).pipeline();
            pipeline.addBefore("packet_handler", CraftersCut.getPlugin().getName() + "PacketInjector:" + player.getName(), packetHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        ImplCCPlayer.onQuit(player);
        
        try {
            Channel channel = CraftersCutAPI.getInstance().getNMSHandler().getChannel(player);
            
            channel.eventLoop().submit(() -> {
                channel.pipeline().remove(CraftersCut.getPlugin().getName() + "PacketInjector:" + player.getName());
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
