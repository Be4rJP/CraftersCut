package be4rjp.crafterscut.api.data.cut;

import org.bukkit.util.Vector;

public interface TickPositionData {

    Vector getPosition(int index);

    Vector getPositionDelta(int index);

    void setPosition(int index, double x, double y, double z);

    Vector getFirstPosition();
}
