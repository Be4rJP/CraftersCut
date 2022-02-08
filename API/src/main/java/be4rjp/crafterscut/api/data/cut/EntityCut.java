package be4rjp.crafterscut.api.data.cut;

import be4rjp.crafterscut.api.data.DataSerializer;
import be4rjp.crafterscut.api.util.math.Vec2f;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import org.bukkit.util.Vector;

import java.util.Map;

public abstract class EntityCut extends Cut {
    
    protected String entityName;
    
    protected final Int2ObjectArrayMap<Vector> positionMap = new Int2ObjectArrayMap<>();
    protected final Int2ObjectArrayMap<Vec2f> rotationMap = new Int2ObjectArrayMap<>();

    @Override
    public void detailSerialize(DataSerializer dataSerializer) {
        dataSerializer.put("entity_name", entityName);
        
        StringBuilder sb1 = new StringBuilder();
        boolean first = true;
        for(Map.Entry<Integer, Vector> entry : positionMap.int2ObjectEntrySet()){
            int index = entry.getKey();
            Vector position = entry.getValue();
            if(!first){
                sb1.append("/");
            }
            sb1.append(index);
            sb1.append(",");
            sb1.append(position.getX());
            sb1.append(",");
            sb1.append(position.getY());
            sb1.append(",");
            sb1.append(position.getZ());
            first = false;
        }
        dataSerializer.put("position", sb1.toString());
    
        StringBuilder sb2 = new StringBuilder();
        first = true;
        for(Map.Entry<Integer, Vec2f> entry : rotationMap.int2ObjectEntrySet()){
            int index = entry.getKey();
            Vec2f rotation = entry.getValue();
            if(!first){
                sb2.append("/");
            }
            sb2.append(index);
            sb2.append(",");
            sb2.append(rotation.x);
            sb2.append(",");
            sb2.append(rotation.y);
            first = false;
        }
        dataSerializer.put("rotation", sb2.toString());

        entityDetailSerialize(dataSerializer);
    }

    @Override
    public void detailDeserializer(DataSerializer dataSerializer) {
        entityName = dataSerializer.get("entity_name");
        
        String[] positionMapArgs = dataSerializer.get("position").split("/");
        for(String positionMapEntry : positionMapArgs){
            String[] args = positionMapEntry.split(",");
            
            int index = Integer.parseInt(args[0]);
            
            double x = Double.parseDouble(args[1]);
            double y = Double.parseDouble(args[2]);
            double z = Double.parseDouble(args[3]);
            
            positionMap.put(index, new Vector(x, y, z));
        }
    
        String[] rotationMapArgs = dataSerializer.get("rotation").split("/");
        for(String rotationMapEntry : rotationMapArgs){
            String[] args = rotationMapEntry.split(",");
        
            int index = Integer.parseInt(args[0]);
        
            float yaw = Float.parseFloat(args[1]);
            float pitch = Float.parseFloat(args[2]);
        
            rotationMap.put(index, new Vec2f(yaw, pitch));
        }

        entityDetailDeserializer(dataSerializer);
    }

    public abstract void entityDetailSerialize(DataSerializer dataSerializer);

    public abstract void entityDetailDeserializer(DataSerializer dataSerializer);
    
    
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
