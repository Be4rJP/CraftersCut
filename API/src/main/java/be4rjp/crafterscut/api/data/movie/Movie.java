package be4rjp.crafterscut.api.data.movie;

import be4rjp.crafterscut.api.data.SerializableData;
import be4rjp.crafterscut.api.data.cut.Cut;
import be4rjp.crafterscut.api.data.cut.CutDataSerializer;
import be4rjp.crafterscut.api.data.cut.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Movie implements SerializableData {

    private String name;

    private String worldName;

    private final List<Cut> cutList = new ArrayList<>();

    public void addCut(Cut cut){cutList.add(cut);}

    public List<Cut> getCutList() {return cutList;}

    public String getName() {return name;}

    public String getWorldName() {return worldName;}
    
    @Override
    public CutDataSerializer serialize() throws Exception {
        CutDataSerializer serializer = new CutDataSerializer();
        serializer.put("name", name);
        serializer.put("world_name", worldName);
        for(Cut cut : cutList){
            serializer.put("cut_" + cut.getType().toString() + "_" + cut.getName(), cut.serialize().toJson());
        }
        return serializer;
    }
    
    @Override
    public void deserialize(CutDataSerializer serializer) throws Exception {
        this.name = serializer.get("name");
        this.worldName = serializer.get("world_name");
        
        for(Map.Entry<String, String> entry : serializer.getDataMap().entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            
            if(!key.contains("cut_")) continue;
            
            String[] args = key.split("_");
            DataType dataType = DataType.valueOf(args[1]);
            
            Cut cut = dataType.createInstance();
            cut.deserialize(new CutDataSerializer().fromJson(value));
        }
    }
}
