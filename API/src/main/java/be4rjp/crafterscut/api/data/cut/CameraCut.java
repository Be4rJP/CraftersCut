package be4rjp.crafterscut.api.data.cut;

import be4rjp.crafterscut.api.player.cut.CutPlayer;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import be4rjp.crafterscut.api.util.Vec2f;
import org.bukkit.util.Vector;

public class CameraCut extends Cut {

    @Override
    public Vector getPosition(int index) {
        return null;
    }
    
    @Override
    public Vec2f getRotation(int index) {
        return null;
    }
    
    @Override
    public Vector getPositionDelta(int index) {
        return null;
    }

    @Override
    public void setPositionRotation(int index, double x, double y, double z, float yaw, float pitch) {

    }

    @Override
    public Vector getFirstPosition() {
        return null;
    }
    
    @Override
    public Vec2f getFirstRotation() {
        return null;
    }
    
    @Override
    public void detailSerialize(CutDataSerializer cutDataSerializer) {

    }

    @Override
    public void detailDeserializer(CutDataSerializer cutDataSerializer) {

    }

    @Override
    public CutPlayer<CameraCut> createCutPlayerInstance(MoviePlayer moviePlayer) {
        return null;
    }
    
    @Override
    public DataType getType() {
        return DataType.CAMERA_CUT;
    }
}
