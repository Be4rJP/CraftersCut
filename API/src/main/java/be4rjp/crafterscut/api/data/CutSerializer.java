package be4rjp.crafterscut.api.data;

import java.util.HashMap;
import java.util.Map;

public class CutSerializer {

    private final Map<String, String> dataMap = new HashMap<>();

    public void put(String key, String data){dataMap.put(key, data);}

    public String get(String key){return dataMap.get(key);}

    public String serialize(){
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : dataMap.entrySet()){
            if(!first) sb.append("/");
            first = false;
            sb.append(entry.getKey()).append(",").append(entry.getValue());
        }
        return sb.toString();
    }

    public void deserialize(String data){
        String[] args = data.split("/");
        for(String arg : args){
            String[] entry = arg.split(",");
            dataMap.put(entry[0], entry[1]);
        }
    }

}
