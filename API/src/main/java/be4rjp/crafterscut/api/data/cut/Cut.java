package be4rjp.crafterscut.api.data.cut;

import be4rjp.crafterscut.api.player.cut.CutPlayer;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
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

    public Vector getTickPositionDelta(int tick){
        if(notTickRange(tick)) return null;
        return getPositionDelta(tick - startTick);
    }

    public void setTickPosition(int tick, double x, double y, double z){
        endTick = Math.max(tick, endTick);
        setPosition(tick - startTick, x, y, z);
    }

    public boolean notTickRange(int tick){return startTick > tick || tick > endTick;}

    public abstract CutPlayer<? extends Cut> createCutPlayerInstance(MoviePlayer moviePlayer);

}
