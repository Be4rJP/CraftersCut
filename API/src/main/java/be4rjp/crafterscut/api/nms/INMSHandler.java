package be4rjp.crafterscut.api.nms;

import be4rjp.crafterscut.api.gui.map.CanvasBuffer;
import be4rjp.crafterscut.api.nms.entity.IEntity;
import be4rjp.crafterscut.api.nms.entity.IEntityLiving;
import be4rjp.crafterscut.api.nms.entity.IEntityPlayer;
import be4rjp.crafterscut.api.nms.enums.WrappedPlayerInfoAction;
import io.netty.channel.Channel;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface INMSHandler {

    Channel getChannel(Player player);

    void sendPacket(Player player, Object packet);

    <T> IEntity createNMSEntity(World world, double x, double y, double z, EntityType type, @Nullable T data);

    Object createSpawnEntityPacket(IEntity iEntity);

    Object createSpawnEntityLivingPacket(IEntityLiving iEntityLiving);

    Object createMetadataPacket(IEntity iEntity);

    Object createPlayerInfoPacket(IEntityPlayer iEntityPlayer, WrappedPlayerInfoAction info);

    Object createSpawnNamedEntityPacket(IEntityPlayer iEntityPlayer);
    
    Object createTeleportPacket(IEntity iEntity);
    
    Object createRelEntityMoveLookPacket(IEntity iEntity, double deltaX, double deltaY, double deltaZ, float yaw, float pitch);

    Object createHeadRotationPacket(IEntity iEntity, float yaw);
    
    Object createEntityDestroyPacket(IEntity iEntity);
    
    Object createMapPacket(CanvasBuffer canvasBuffer, List<MapCursor> mapCursor);
    
    boolean isMapPacket(Object packet);
    
    int getMapID(Object packet);
    
}
