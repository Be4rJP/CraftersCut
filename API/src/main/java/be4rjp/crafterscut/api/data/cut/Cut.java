package be4rjp.crafterscut.api.data.cut;

import be4rjp.crafterscut.api.data.DataSerializer;
import be4rjp.crafterscut.api.data.SerializableData;
import be4rjp.crafterscut.api.editor.cut.CutEditor;
import be4rjp.crafterscut.api.gui.map.component.TimelineComponent;
import be4rjp.crafterscut.api.player.cut.CutPlayer;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import be4rjp.crafterscut.api.util.math.Vec2f;
import org.bukkit.util.Vector;

public abstract class Cut implements TickPositionData, SerializableData {

    protected String name;

    protected int startTick;

    protected int endTick;
    
    protected int lane = -1;
    
    protected final CutEditor cutEditor;

    public String getName() {return name;}

    public int getEndTick() {return endTick;}

    public int getStartTick() {return startTick;}
    
    public void setName(String name) {this.name = name;}
    
    public void setEndTick(int endTick) {this.endTick = endTick;}

    public void setStartTick(int startTick) {this.startTick = startTick;}
    
    public int getLane() {return lane;}
    
    public void setLane(int lane) {this.lane = lane;}
    
    public CutEditor getCutEditor() {return cutEditor;}
    
    public boolean isDuplicate(Cut cut){
        return this.isInRange(cut.getStartTick()) || this.isInRange(cut.getEndTick()) || cut.isInRange(startTick) || cut.isInRange(endTick);
    }
    
    public boolean isDuplicate(TimelineComponent timeline){
        return this.isInRange(timeline.getStartTick()) || this.isInRange(timeline.getEndTick()) || timeline.isInRange(startTick) || timeline.isInRange(endTick);
    }
    
    public boolean isInRange(int tick){return startTick <= tick && tick <= endTick;}
    
    public int getWidth(){return (endTick - startTick) + 1;}
    
    public Cut(){
        this.cutEditor = createEditorInstance();
    }
    
    protected abstract CutEditor createEditorInstance();
    
    @Override
    public DataSerializer serialize() {
        DataSerializer dataSerializer = new DataSerializer();
        dataSerializer.put("name", name);
        dataSerializer.put("start_tick", String.valueOf(startTick));
        dataSerializer.put("end_tick", String.valueOf(endTick));

        detailSerialize(dataSerializer);

        return dataSerializer;
    }

    @Override
    public void deserialize(DataSerializer dataSerializer) {
        this.name = dataSerializer.get("name");
        this.startTick = Integer.parseInt(dataSerializer.get("start_tick"));
        this.endTick = Integer.parseInt(dataSerializer.get("end_tick"));
        
        detailDeserializer(dataSerializer);
    }

    public abstract void detailSerialize(DataSerializer dataSerializer);

    public abstract void detailDeserializer(DataSerializer dataSerializer);


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
