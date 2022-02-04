package be4rjp.crafterscut.api.data.cut;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.bukkit.util.Vector;

public abstract class EntityCut extends Cut {

    protected final Int2ObjectArrayMap<Vector> deltaMap = new Int2ObjectArrayMap<>();
    protected final Int2ObjectOpenHashMap<Vector> positionMap = new Int2ObjectOpenHashMap<>();

    @Override
    public void detailSerialize(CutDataSerializer cutDataSerializer) {


        entityDetailSerialize(cutDataSerializer);
    }

    @Override
    public void detailDeserializer(CutDataSerializer cutDataSerializer) {


        entityDetailDeserializer(cutDataSerializer);
    }

    public abstract void entityDetailSerialize(CutDataSerializer cutDataSerializer);

    public abstract void entityDetailDeserializer(CutDataSerializer cutDataSerializer);



    @Override
    public Vector getPosition(int index) {
        Vector position = positionMap.get(index);
        if(position != null){
            return position;
        }

        Vector nearPosition = null;
        int positionIndex = 0;
        int plusIndex = 0;
        for(int i = 1; i < positionMap.size(); i++){
            positionIndex = index - i;
            plusIndex = i;
            nearPosition = positionMap.get(positionIndex);
            if(nearPosition != null) break;
        }

        if(nearPosition == null) return null;

        nearPosition = nearPosition.clone();
        for(int i = 0; i < plusIndex; i++){
            nearPosition.add(deltaMap.get(positionIndex + plusIndex));
        }

        return nearPosition;
    }

    @Override
    public Vector getPositionDelta(int index) {
        return deltaMap.get(index);
    }

    @Override
    public void setPosition(int index, double x, double y, double z) {
        if(index == 0){
            deltaMap.put(index, new Vector(0.0, 0.0, 0.0));
            positionMap.put(index, new Vector(x, y, z));
            return;
        }

        Vector previous = getPosition(index - 1);
        if(previous == null) return;

        if(index % 60 == 0){
            positionMap.put(index, new Vector(x, y, z));
        }

        deltaMap.put(index, new Vector(x - previous.getX(), y - previous.getY(), z - previous.getZ()));
    }

    @Override
    public Vector getFirstPosition() {
        return null;
    }
}
