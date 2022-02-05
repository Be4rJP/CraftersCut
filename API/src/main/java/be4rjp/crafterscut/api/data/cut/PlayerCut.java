package be4rjp.crafterscut.api.data.cut;

import be4rjp.crafterscut.api.data.DataSerializer;
import be4rjp.crafterscut.api.player.cut.CutPlayer;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import be4rjp.crafterscut.api.player.cut.PlayerCutPlayer;

public class PlayerCut extends EntityCut {
    
    protected String skinValue;
    
    protected String skinSignature;

    @Override
    public void entityDetailSerialize(DataSerializer dataSerializer) {
        dataSerializer.put("skin_value", skinValue);
        dataSerializer.put("skin_signature", skinSignature);
    }

    @Override
    public void entityDetailDeserializer(DataSerializer dataSerializer) {
        skinValue = dataSerializer.get("skin_value");
        skinSignature = dataSerializer.get("skin_signature");
    }
    
    public String getSkinValue() {return skinValue;}
    
    public String getSkinSignature() {return skinSignature;}
    
    public void setSkinValue(String skinValue) {this.skinValue = skinValue;}
    
    public void setSkinSignature(String skinSignature) {this.skinSignature = skinSignature;}
    
    @Override
    public CutPlayer<PlayerCut> createCutPlayerInstance(MoviePlayer moviePlayer) {
        return new PlayerCutPlayer(moviePlayer, this);
    }
    
    @Override
    public DataType getType() {return DataType.ENTITY_PLAYER_CUT;}
}
