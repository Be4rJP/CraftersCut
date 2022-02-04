package be4rjp.crafterscut.api.data.cut;

import be4rjp.crafterscut.api.util.Vec2f;
import org.bukkit.util.Vector;

public interface TickPositionData {

    Vector getPosition(int index);
    
    Vec2f getRotation(int index);

    Vector getPositionDelta(int index);

    void setPositionRotation(int index, double x, double y, double z, float yaw, float pitch);

    Vector getFirstPosition();
    
    Vec2f getFirstRotation();
}
