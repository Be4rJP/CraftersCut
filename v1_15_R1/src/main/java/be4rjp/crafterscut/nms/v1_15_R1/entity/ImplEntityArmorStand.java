package be4rjp.crafterscut.nms.v1_15_R1.entity;

import be4rjp.crafterscut.api.nms.entity.IEntityLiving;
import net.minecraft.server.v1_15_R1.EntityArmorStand;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.World;

public class ImplEntityArmorStand extends EntityArmorStand implements IEntityLiving {

    public ImplEntityArmorStand(EntityTypes<? extends EntityArmorStand> entitytypes, World world) {
        super(entitytypes, world);
    }
    
    @Override
    public void setPositionRotationRaw(double x, double y, double z, float yaw, float pitch) {
        super.setPositionRaw(x, y, z);
        super.yaw = yaw;
        super.pitch = pitch;
    }
}
