package be4rjp.nms.v1_15_R1;

import be4rjp.crafterscut.api.nms.INMSHandler;
import be4rjp.crafterscut.api.nms.entity.IEntity;
import be4rjp.crafterscut.api.nms.entity.IEntityLiving;
import be4rjp.crafterscut.api.nms.entity.IEntityPlayer;
import be4rjp.crafterscut.api.nms.enums.WrappedPlayerInfoAction;
import be4rjp.nms.v1_15_R1.entity.ImplEntityArmorStand;
import be4rjp.nms.v1_15_R1.entity.ImplEntityPlayer;
import com.mojang.authlib.GameProfile;
import io.netty.channel.Channel;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class NMSHandler implements INMSHandler {

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
    public Object createSpawnNamedEntity(IEntityPlayer iEntityPlayer) {
        return new PacketPlayOutNamedEntitySpawn((EntityHuman) iEntityPlayer);
    }

}
