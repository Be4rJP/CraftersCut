package be4rjp.crafterscut.nms.v1_15_R1;

import be4rjp.crafterscut.api.gui.map.CanvasBuffer;
import be4rjp.crafterscut.api.nms.INMSHandler;
import be4rjp.crafterscut.api.nms.entity.IEntity;
import be4rjp.crafterscut.api.nms.entity.IEntityLiving;
import be4rjp.crafterscut.api.nms.entity.IEntityPlayer;
import be4rjp.crafterscut.api.nms.enums.WrappedPlayerInfoAction;
import be4rjp.crafterscut.nms.v1_15_R1.entity.ImplEntityArmorStand;
import be4rjp.crafterscut.nms.v1_15_R1.entity.ImplEntityPlayer;
import com.mojang.authlib.GameProfile;
import io.netty.channel.Channel;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_15_R1.util.CraftChatMessage;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NMSHandler implements INMSHandler {
    
    private static Field mapPacketA;
    
    static {
        try{
            mapPacketA = PacketPlayOutMap.class.getDeclaredField("a");
            mapPacketA.setAccessible(true);
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public Channel getChannel(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
    }

    @Override
    public void sendPacket(Player player, Object packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket((Packet<?>) packet);
    }

    @Override
    public <T> IEntity createNMSEntity(World world, double x, double y, double z, EntityType type, @Nullable T data) {

        WorldServer worldServer = ((CraftWorld) world).getHandle();

        switch (type){
            case PLAYER:{
                return new ImplEntityPlayer(MinecraftServer.getServer(), worldServer, (GameProfile) data, new PlayerInteractManager(worldServer));
            }

            case ARMOR_STAND:{
                return new ImplEntityArmorStand(EntityTypes.ARMOR_STAND, worldServer);
            }
        }

        return null;
    }


    @Override
    public Object createSpawnEntityPacket(IEntity iEntity) {
        return new PacketPlayOutSpawnEntity((Entity) iEntity);
    }

    @Override
    public Object createSpawnEntityLivingPacket(IEntityLiving iEntityLiving) {
        return new PacketPlayOutSpawnEntityLiving((EntityLiving) iEntityLiving);
    }

    @Override
    public Object createMetadataPacket(IEntity iEntity) {
        Entity entity = (Entity) iEntity;
        return new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), true);
    }

    @Override
    public Object createPlayerInfoPacket(IEntityPlayer iEntityPlayer, WrappedPlayerInfoAction action) {
        PacketPlayOutPlayerInfo.EnumPlayerInfoAction nmsAction = PacketPlayOutPlayerInfo.EnumPlayerInfoAction.valueOf(action.toString());
        return new PacketPlayOutPlayerInfo(nmsAction, (EntityPlayer) iEntityPlayer);
    }

    @Override
    public Object createSpawnNamedEntityPacket(IEntityPlayer iEntityPlayer) {
        return new PacketPlayOutNamedEntitySpawn((EntityHuman) iEntityPlayer);
    }
    
    @Override
    public Object createTeleportPacket(IEntity iEntity) {
        return new PacketPlayOutEntityTeleport((Entity) iEntity);
    }
    
    @Override
    public Object createRelEntityMoveLookPacket(IEntity iEntity, double deltaX, double deltaY, double deltaZ, float yaw, float pitch) {
        return new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(iEntity.getId(), (short) (deltaX * 4096), (short) (deltaY * 4096), (short) (deltaZ * 4096),
                (byte) ((yaw * 256.0F) / 360.0F), (byte) ((pitch * 256.0F) / 360.0F), true);
    }
    
    @Override
    public Object createHeadRotationPacket(IEntity iEntity, float yaw) {
        return new PacketPlayOutEntityHeadRotation((Entity) iEntity, (byte) ((yaw * 256.0F) / 360.0F));
    }
    
    @Override
    public Object createEntityDestroyPacket(IEntity iEntity) {
        return new PacketPlayOutEntityDestroy(iEntity.getId());
    }
    
    @Override
    public Object createMapPacket(CanvasBuffer canvasBuffer, List<MapCursor> mapCursor) {
        List<MapIcon> mapIcons = new ArrayList<>();
        mapCursor.forEach(cursor -> mapIcons.add(new MapIcon(MapIcon.Type.values()[cursor.getType().ordinal()], cursor.getX(), cursor.getY(), cursor.getDirection(), CraftChatMessage.fromStringOrNull(null))));
        return new PacketPlayOutMap(0, (byte) 0, false, false, mapIcons, canvasBuffer.getBuffer(), 0, 0, 128, 128);
    }
    
    @Override
    public boolean isMapPacket(Object packet) {
        return packet instanceof PacketPlayOutMap;
    }
    
    @Override
    public int getMapID(Object packet) {
        try{
            return mapPacketA.getInt(packet);
        }catch (Exception e){e.printStackTrace();}
        
        return 0;
    }
    
}
