package be4rjp.crafterscut.nms;

import be4rjp.crafterscut.api.CCPlayer;
import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.gui.map.MapGUIRenderer;
import be4rjp.crafterscut.api.nms.INMSHandler;
import io.netty.channel.*;


public class PacketHandler extends ChannelDuplexHandler{
    
    private final CCPlayer ccPlayer;
    
    public PacketHandler(CCPlayer ccPlayer){this.ccPlayer = ccPlayer;}
    
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
        super.channelRead(channelHandlerContext, packet);
    }
    
    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
        
        INMSHandler nmsHandler = CraftersCutAPI.getInstance().getNMSHandler();
        
        if(nmsHandler.isMapPacket(packet)){
            MapGUIRenderer mapGUIRenderer = ccPlayer.getMapGUIRenderer();
            
            if(mapGUIRenderer != null && nmsHandler.getMapID(packet) == 0) {
                Object newPacket = mapGUIRenderer.getPacket();
                
                if(newPacket != null) {
                    super.write(channelHandlerContext, newPacket, channelPromise);
                    return;
                }
            }
        }
        
        super.write(channelHandlerContext, packet, channelPromise);
    }
    
}
