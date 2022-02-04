package be4rjp.crafterscut.api.player.cut;

import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.data.cut.PlayerCut;
import be4rjp.crafterscut.api.nms.INMSHandler;
import be4rjp.crafterscut.api.nms.entity.IEntityPlayer;
import be4rjp.crafterscut.api.nms.enums.WrappedPlayerInfoAction;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import be4rjp.crafterscut.api.util.Vec2f;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

public class PlayerCutPlayer extends CutPlayer<PlayerCut> {

    private IEntityPlayer entityPlayer;

    public PlayerCutPlayer(MoviePlayer moviePlayer, PlayerCut cut) {
        super(moviePlayer, cut);
    }

    @Override
    public void initializeAtMainThread() {
        World world = moviePlayer.getWorld();

        INMSHandler nmsHandler = CraftersCutAPI.getInstance().getNMSHandler();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), cut.getEntityName());
        gameProfile.getProperties().put("textures", new Property("textures", cut.getSkinValue(), cut.getSkinSignature()));
        entityPlayer = (IEntityPlayer) nmsHandler.createNMSEntity(world, 0, 0, 0, EntityType.PLAYER, gameProfile);
        Vector position = cut.getFirstPosition();
        Vec2f rotation = cut.getFirstRotation();
        
        if(position != null && rotation != null){
            entityPlayer.setPositionRotationRaw(position.getX(), position.getY(), position.getZ(), rotation.x, rotation.y);
        }
    }

    @Override
    public void onMovieStart() {
        //None
    }

    @Override
    public void onMovieEnd() {
        //None
    }

    @Override
    public void onStart() {
        INMSHandler nmsHandler = CraftersCutAPI.getInstance().getNMSHandler();
        Player audience = moviePlayer.getAudience();
        Object infoPacket = nmsHandler.createPlayerInfoPacket(entityPlayer, WrappedPlayerInfoAction.ADD_PLAYER);
        Object spawnPacket = nmsHandler.createSpawnNamedEntityPacket(entityPlayer);
        
        nmsHandler.sendPacket(audience, infoPacket);
        nmsHandler.sendPacket(audience, spawnPacket);
    }

    @Override
    public void onEnd() {
        INMSHandler nmsHandler = CraftersCutAPI.getInstance().getNMSHandler();
        Player audience = moviePlayer.getAudience();
        Object removePacket = nmsHandler.createEntityDestroyPacket(entityPlayer);
        Object infoPacket = nmsHandler.createPlayerInfoPacket(entityPlayer, WrappedPlayerInfoAction.REMOVE_PLAYER);
        
        nmsHandler.sendPacket(audience, removePacket);
        nmsHandler.sendPacket(audience, infoPacket);
    }

    @Override
    public void playTick(int tick) {
        INMSHandler nmsHandler = CraftersCutAPI.getInstance().getNMSHandler();
        Player audience = moviePlayer.getAudience();
        
        if(tick % 60 == 0) {
            Vector position = cut.getTickPosition(tick);
            Vec2f rotation = cut.getTickRotation(tick);
            if(position != null && rotation != null){
                entityPlayer.setPositionRotationRaw(position.getX(), position.getY(), position.getZ(), rotation.x, rotation.y);
                Object teleportPacket = nmsHandler.createTeleportPacket(entityPlayer);
                Object moveLookPacket = nmsHandler.createRelEntityMoveLookPacket(entityPlayer, 0.0, 0.0, 0.0, rotation.x, rotation.y);
                Object headPacket = nmsHandler.createHeadRotationPacket(entityPlayer, rotation.x);
                nmsHandler.sendPacket(audience, teleportPacket);
                nmsHandler.sendPacket(audience, moveLookPacket);
                nmsHandler.sendPacket(audience, headPacket);
            }
        } else {
            Vector delta = cut.getTickPositionDelta(tick);
            Vec2f rotation = cut.getTickRotation(tick);
            if(delta != null && rotation != null){
                Object moveLookPacket = nmsHandler.createRelEntityMoveLookPacket(entityPlayer, delta.getX(), delta.getY(), delta.getZ(), rotation.x, rotation.y);
                Object headPacket = nmsHandler.createHeadRotationPacket(entityPlayer, rotation.x);
                nmsHandler.sendPacket(audience, moveLookPacket);
                nmsHandler.sendPacket(audience, headPacket);
            }
        }
    }
}
