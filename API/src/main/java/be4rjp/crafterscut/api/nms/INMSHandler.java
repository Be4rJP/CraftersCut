package be4rjp.crafterscut.api.nms;

import be4rjp.crafterscut.api.nms.entity.IEntity;
import be4rjp.crafterscut.api.nms.entity.IEntityLiving;
import be4rjp.crafterscut.api.nms.entity.IEntityPlayer;
import be4rjp.crafterscut.api.nms.enums.WrappedPlayerInfoAction;
import io.netty.channel.Channel;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public interface INMSHandler {

    Channel getChannel(Player player);

    void sendPacket(Player player, Object packet);

    <T> IEntity createNMSEntity(World world, double x, double y, double z, EntityType type, @Nullable T data);

    Object createSpawnEntityPacket(IEntity iEntity);

    Object createSpawnEntityLivingPacket(IEntityLiving iEntityLiving);

    Object createMetadataPacket(IEntity iEntity);

    Object createPlayerInfoPacket(IEntityPlayer iEntityPlayer, WrappedPlayerInfoAction info);

    Object createSpawnNamedEntity(IEntityPlayer iEntityPlayer);

}
