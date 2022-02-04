package be4rjp.crafterscut.api.data.cut;

public enum DataType {
    ENTITY_PLAYER_CUT(PlayerCut.class),
    NORMAL_ENTITY_CUT(null),
    NORMAL_LIVING_ENTITY_CUT(null),
    CAMERA_CUT(CameraCut.class);
    
    private final Class<? extends Cut> clazz;
    
    DataType(Class<? extends Cut> clazz){
        this.clazz = clazz;
    }
    
    public Cut createInstance() throws Exception {return clazz.getConstructor().newInstance();}
}
