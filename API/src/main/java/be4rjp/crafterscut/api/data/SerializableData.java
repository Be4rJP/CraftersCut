package be4rjp.crafterscut.api.data;

public interface SerializableData {

    CutSerializer serialize();

    void deserialize(CutSerializer cutSerializer);

}
