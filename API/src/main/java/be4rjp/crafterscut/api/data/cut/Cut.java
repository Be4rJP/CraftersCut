package be4rjp.crafterscut.api.data.cut;

import be4rjp.crafterscut.api.data.SerializableData;
import be4rjp.crafterscut.api.player.cut.CutPlayer;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import be4rjp.crafterscut.api.util.Vec2f;
import org.bukkit.util.Vector;

public abstract class Cut implements TickPositionData, SerializableData {

    protected String name;

    protected int startTick;

    protected int endTick;

    public String getName() {return name;}

    public int getEndTick() {return endTick;}

    public int getStartTick() {return startTick;}
    
    public void setName(String name) {this.name = name;}
    
    public void setEndTick(int endTick) {this.endTick = endTick;}

    public void setStartTick(int startTick) {this.startTick = startTick;}


    @Override
    public CutDataSerializer serialize() {
        CutDataSerializer cutDataSerializer = new CutDataSerializer();
        cutDataSerializer.put("name", name);
        cutDataSerializer.put("start_tick", String.valueOf(startTick));
        cutDataSerializer.put("end_tick", String.valueOf(endTick));

        detailSerialize(cutDataSerializer);

        return cutDataSerializer;
    }

    @Override
    public void deserialize(CutDataSerializer cutDataSerializer) {
        this.name = cutDataSerializer.get("name");
        this.startTick = Integer.parseInt(cutDataSerializer.get("start_tick"));
        this.endTick = Integer.parseInt(cutDataSerializer.get("end_tick"));
    }

    public abstract void detailSerialize(CutDataSerializer cutDataSerializer);

    public abstract void detailDeserializer(CutDataSerializer cutDataSerializer);


    public Vector getTickPosition(int tick){
        if(notTickRange(tick)) return null;
        return getPosition(tick - startTick);
    }

    public Vec2f getTickRotation(int tick){
        if(notTickRange(tick)) return null;
        return getRotation(tick - startTick);
    }
    
    public Vector getTickPositionDelta(int tick){
        if(notTickRange(tick)) return null;
        return getPositionDelta(tick - startTick);
    }

    public void setTickPositionRotation(int tick, double x, double y, double z, float yaw, float pitch){
        if(tick < startTick) return;
        endTick = Math.max(tick, endTick);
        setPositionRotation(tick - startTick, x, y, z, yaw, pitch);
    }

    public boolean notTickRange(int tick){return startTick > tick || tick > endTick;}

    public abstract CutPlayer<? extends Cut> createCutPlayerInstance(MoviePlayer moviePlayer);

    public abstract DataType getType();
    
}
