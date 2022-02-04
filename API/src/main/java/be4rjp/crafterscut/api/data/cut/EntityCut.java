package be4rjp.crafterscut.api.data.cut;

import be4rjp.crafterscut.api.util.Vec2f;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import org.bukkit.util.Vector;

public abstract class EntityCut extends Cut {
    
    protected String entityName;
    
    protected final Int2ObjectArrayMap<Vector> positionMap = new Int2ObjectArrayMap<>();
    protected final Int2ObjectArrayMap<Vec2f> rotationMap = new Int2ObjectArrayMap<>();

    @Override
    public void detailSerialize(CutDataSerializer cutDataSerializer) {
        cutDataSerializer.put("entity_name", entityName);

        entityDetailSerialize(cutDataSerializer);
    }

    @Override
    public void detailDeserializer(CutDataSerializer cutDataSerializer) {
        entityName = cutDataSerializer.get("entity_name");

        entityDetailDeserializer(cutDataSerializer);
    }

    public abstract void entityDetailSerialize(CutDataSerializer cutDataSerializer);

    public abstract void entityDetailDeserializer(CutDataSerializer cutDataSerializer);
    
    
    public String getEntityName() {return entityName;}
    
    public void setEntityName(String entityName) {this.entityName = entityName;}
    
    @Override
    public Vector getPosition(int index) {
        return positionMap.get(index);
    }
    
    @Override
    public Vec2f getRotation(int index) {
        return rotationMap.get(index);
    }
    
    @Override
    public Vector getPositionDelta(int index) {
        if(index == 0) return new Vector(0, 0, 0);
        
        Vector previous = getPosition(index - 1);
        if(previous == null) return null;
        
        Vector current = getPosition(index);
        if(current == null) return null;
        
        return new Vector(current.getX() - previous.getX(), current.getY() - previous.getY(), current.getZ() - previous.getZ());
    }

    @Override
    public void setPositionRotation(int index, double x, double y, double z, float yaw, float pitch) {
        positionMap.put(index, new Vector(x, y, z));
        rotationMap.put(index, new Vec2f(yaw, pitch));
    }

    @Override
    public Vector getFirstPosition() {
        if(positionMap.size() == 0){
            return null;
        }
        return positionMap.get(0);
    }
    
    @Override
    public Vec2f getFirstRotation() {
        if(rotationMap.size() == 0){
            return null;
        }
        return rotationMap.get(0);
    }
}
