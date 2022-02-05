package be4rjp.crafterscut.api.data.cut;

public enum DataType {
    ENTITY_PLAYER_CUT(PlayerCut.class, 0),
    NORMAL_ENTITY_CUT(null, 1),
    NORMAL_LIVING_ENTITY_CUT(null, 2),
    CAMERA_CUT(CameraCut.class, 3);
    
    private final Class<? extends Cut> clazz;
    
    private final int serializeNumber;
    
    DataType(Class<? extends Cut> clazz, int serializeNumber){
        this.clazz = clazz;
        this.serializeNumber = serializeNumber;
    }
    
    public int getSerializeNumber() {return serializeNumber;}
    
    public Cut createInstance() throws Exception {return clazz.getConstructor().newInstance();}
    
    public static DataType getFromSerializeNumber(int number){
        for(DataType dataType : DataType.values()){
            if(dataType.serializeNumber == number) return dataType;
        }
        throw new IllegalArgumentException("NOT FOUND DATA TYPE : " + number);
    }
}
