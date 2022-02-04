package be4rjp.crafterscut.api.data.cut;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class CutDataSerializer {

    private Map<String, String> dataMap;

    public void put(String key, String data){dataMap.put(key, data);}

    public String get(String key){return dataMap.get(key);}
    
    public Map<String, String> getDataMap() {return dataMap;}
    
    public String toJson() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dataMap);
    }

    public CutDataSerializer fromJson(String json) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        dataMap = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
        return this;
    }

}
