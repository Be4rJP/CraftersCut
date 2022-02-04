package be4rjp.crafterscut.api.nms.entity;

import org.bukkit.entity.Entity;

public interface IEntity {

    void setPositionRotation(double x, double y, double z, float yaw, float pitch);

    Entity getBukkitEntity();

    int getId();

}
