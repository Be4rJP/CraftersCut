package be4rjp.crafterscut.api.data.cut;

import be4rjp.crafterscut.api.player.cut.CutPlayer;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import be4rjp.crafterscut.api.player.cut.PlayerCutPlayer;

public class PlayerCut extends EntityCut {
    
    protected String skinValue;
    
    protected String skinSignature;

    @Override
    public void entityDetailSerialize(CutDataSerializer cutDataSerializer) {
        cutDataSerializer.put("skin_value", skinValue);
        cutDataSerializer.put("skin_signature", skinSignature);
    }

    @Override
    public void entityDetailDeserializer(CutDataSerializer cutDataSerializer) {
        skinValue = cutDataSerializer.get("skin_value");
        skinSignature = cutDataSerializer.get("skin_signature");
    }
    
    public String getSkinValue() {return skinValue;}
    
    public String getSkinSignature() {return skinSignature;}
    
    @Override
    public CutPlayer<PlayerCut> createCutPlayerInstance(MoviePlayer moviePlayer) {
        return new PlayerCutPlayer(moviePlayer, this);
    }
    
    @Override
    public DataType getType() {
        return DataType.ENTITY_PLAYER_CUT;
    }
}
