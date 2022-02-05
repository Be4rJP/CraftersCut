package be4rjp.crafterscut.api.data;

public interface SerializableData {

    DataSerializer serialize() throws Exception;

    void deserialize(DataSerializer dataSerializer) throws Exception;

}
