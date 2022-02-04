package be4rjp.crafterscut.api.player.cut;

import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.data.cut.PlayerCut;
import be4rjp.crafterscut.api.nms.INMSHandler;
import be4rjp.crafterscut.api.nms.entity.IEntityPlayer;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

public class PlayerCutPlayer extends CutPlayer<PlayerCut> {

    private IEntityPlayer entityPlayer;

    public PlayerCutPlayer(MoviePlayer moviePlayer, PlayerCut cut) {
        super(moviePlayer, cut);
    }

    @Override
    public void initializeAtMainThread() {
        World world = null;

        INMSHandler nmsHandler = CraftersCutAPI.getInstance().getNMSHandler();
        entityPlayer = (IEntityPlayer) nmsHandler.createNMSEntity(world, 0, 0, 0, EntityType.PLAYER, null);

    }

    @Override
    public void onMovieStart() {

    }

    @Override
    public void onMovieEnd() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public void playTick(int tick) {

    }
}
