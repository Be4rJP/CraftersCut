package be4rjp.crafterscut.nms.v1_15_R1.entity;

import be4rjp.crafterscut.api.nms.entity.IEntityPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.PlayerInteractManager;
import net.minecraft.server.v1_15_R1.WorldServer;

public class ImplEntityPlayer extends EntityPlayer implements IEntityPlayer{

    public ImplEntityPlayer(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);
    }
    
    @Override
    public void setPositionRotationRaw(double x, double y, double z, float yaw, float pitch) {
        super.setPositionRaw(x, y, z);
        super.yaw = yaw;
        super.pitch = pitch;
    }
}
