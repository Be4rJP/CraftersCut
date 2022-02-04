package be4rjp.crafterscut.api.data;

import be4rjp.crafterscut.api.data.cut.CutDataSerializer;

public interface SerializableData {

    CutDataSerializer serialize() throws Exception;

    void deserialize(CutDataSerializer cutDataSerializer) throws Exception;

}
