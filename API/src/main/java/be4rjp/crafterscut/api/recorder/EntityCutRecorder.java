package be4rjp.crafterscut.api.recorder;

import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.data.cut.EntityCut;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityCutRecorder extends BukkitRunnable {
    
    private final EntityCut cut;
    private final Player actor;
    
    private final int startTick;
    
    private boolean isRecording = false;
    
    public EntityCutRecorder(EntityCut cut, Player actor, int startTick){
        this.cut = cut;
        this.actor = actor;
        this.startTick = startTick;
        
        cut.setStartTick(startTick);
    }
    
    public EntityCut getCut() {return cut;}
    
    public synchronized boolean isRecording() {return isRecording;}
    
    private int index = 0;
    
    @Override
    public void run() {
        isRecording = true;
    
        Location location = actor.getLocation();
        cut.setPositionRotation(index, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        
        index++;
    }
    
    public void runAtMainThread(){
        super.runTaskTimer(CraftersCutAPI.getInstance().getPlugin(), 0, 1);
    }
    
    public void runAtAsyncThread(){
        super.runTaskTimerAsynchronously(CraftersCutAPI.getInstance().getPlugin(), 0, 1);
    }
    
    @Override
    public synchronized void cancel() throws IllegalStateException {
        isRecording = false;
        cut.setEndTick(startTick + index);
        actor.sendMessage("record end");
        super.cancel();
    }
}
