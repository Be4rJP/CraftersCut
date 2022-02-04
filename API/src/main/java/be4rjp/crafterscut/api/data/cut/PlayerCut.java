package be4rjp.crafterscut.api.data.cut;

import be4rjp.crafterscut.api.player.cut.CutPlayer;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import be4rjp.crafterscut.api.player.cut.PlayerCutPlayer;

public class PlayerCut extends EntityCut {

    @Override
    public void entityDetailSerialize(CutDataSerializer cutDataSerializer) {

    }

    @Override
    public void entityDetailDeserializer(CutDataSerializer cutDataSerializer) {

    }

    @Override
    public CutPlayer<PlayerCut> createCutPlayerInstance(MoviePlayer moviePlayer) {
        return new PlayerCutPlayer(moviePlayer, this);
    }
}
