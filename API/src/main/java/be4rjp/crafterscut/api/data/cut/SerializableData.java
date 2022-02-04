package be4rjp.crafterscut.api.data.cut;

public interface SerializableData {

    CutDataSerializer serialize();

    void deserialize(CutDataSerializer cutDataSerializer);

}
