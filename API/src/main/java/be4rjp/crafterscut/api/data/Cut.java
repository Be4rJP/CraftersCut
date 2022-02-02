package be4rjp.crafterscut.api.data;

import org.bukkit.util.Vector;

public abstract class Cut implements TickPositionData, SerializableData {

    protected String name;

    protected int startTick;

    protected int endTick;

    public String getName() {return name;}

    public int getEndTick() {return endTick;}

    public int getStartTick() {return startTick;}

    public void setEndTick(int endTick) {this.endTick = endTick;}

    public void setStartTick(int startTick) {this.startTick = startTick;}

    public Vector getTickPosition(int tick){
        if(notTickRange(tick)) return null;
        return getPosition(tick - startTick);
    }

    public Vector getTickPositionDelta(int tick){
        if(notTickRange(tick)) return null;
        return getPositionDelta(tick - startTick);
    }

    public void setTickPosition(int tick, double x, double y, double z){
        endTick = Math.max(tick, endTick);
        setPosition(tick - startTick, x, y, z);
    }

    public boolean notTickRange(int tick){return startTick > tick || tick > endTick;}
}
