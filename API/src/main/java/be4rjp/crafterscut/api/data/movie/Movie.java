package be4rjp.crafterscut.api.data.movie;

import be4rjp.crafterscut.api.data.SerializableData;
import be4rjp.crafterscut.api.data.cut.Cut;
import be4rjp.crafterscut.api.data.DataSerializer;
import be4rjp.crafterscut.api.data.cut.DataType;
import be4rjp.crafterscut.api.editor.movie.MovieEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Movie implements SerializableData {

    private String name;

    private String worldName;

    private final List<Cut> cutList = new ArrayList<>();
    
    private final MovieEditor movieEditor = new MovieEditor(this);

    public void addCut(Cut cut){cutList.add(cut);}

    public List<Cut> getCutList() {return cutList;}

    public String getName() {return name;}

    public String getWorldName() {return worldName;}
    
    public void setName(String name) {this.name = name;}
    
    public void setWorldName(String worldName) {this.worldName = worldName;}
    
    public MovieEditor getMovieEditor() {return movieEditor;}
    
    @Override
    public DataSerializer serialize() throws Exception {
        DataSerializer serializer = new DataSerializer();
        serializer.put("name", name);
        serializer.put("world_name", worldName);
        for(Cut cut : cutList){
            serializer.put("cut_" + cut.getType().getSerializeNumber() + "_" + cut.getName(), cut.serialize().toJson());
        }
        return serializer;
    }
    
    @Override
    public void deserialize(DataSerializer serializer) throws Exception {
        this.name = serializer.get("name");
        this.worldName = serializer.get("world_name");
        
        for(Map.Entry<String, String> entry : serializer.getDataMap().entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            
            if(!key.contains("cut_")) continue;
            
            String[] args = key.split("_");
            DataType dataType = DataType.getFromSerializeNumber(Integer.parseInt(args[1]));
            
            Cut cut = dataType.createInstance();
            cut.deserialize(new DataSerializer().fromJson(value));
            cutList.add(cut);
        }
    }
}
