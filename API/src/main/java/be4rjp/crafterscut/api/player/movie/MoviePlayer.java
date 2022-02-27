package be4rjp.crafterscut.api.player.movie;

import be4rjp.crafterscut.api.CCPlayer;
import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.data.cut.Cut;
import be4rjp.crafterscut.api.data.movie.Movie;
import be4rjp.crafterscut.api.player.cut.CutPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MoviePlayer extends BukkitRunnable {

    private final Movie movie;
    private final CCPlayer audience;
    private final Consumer<Exception> exceptionConsumer;

    private World world;

    private final List<CutPlayer<? extends Cut>> cutPlayerList;

    private int endTick = 0;

    public MoviePlayer(Movie movie, CCPlayer audience, Consumer<Exception> exceptionConsumer) {
        this.movie = movie;
        this.audience = audience;
        this.exceptionConsumer = exceptionConsumer;

        this.cutPlayerList = new ArrayList<>();
        for(Cut cut : movie.getCutList()){
            cutPlayerList.add(cut.createCutPlayerInstance(this));
            endTick = Math.max(endTick, cut.getEndTick());
        }
    }

    public Movie getMovie() {return movie;}

    public CCPlayer getAudience() {return audience;}

    public void playTick(int tick, boolean initialize){
        for(CutPlayer<? extends Cut> cutPlayer : cutPlayerList){
            Cut cut = cutPlayer.getCut();

            if(cut.notTickRange(tick)) continue;

            if(cut.getStartTick() == tick) cutPlayer.onStart();
            if(cut.getEndTick() == tick) cutPlayer.onEnd();

            if(initialize) {
                cutPlayer.playInitializeTick(tick);
            }else{
                cutPlayer.playTick(tick);
            }
        }
    }
    
    private boolean initialized = false;

    public synchronized void initializeAtMainThread() {
        if(!initialized) {
            if (!Bukkit.isPrimaryThread()) throw new IllegalStateException("DO NOT CALL FROM ASYNC THREAD!");
    
            this.world = Bukkit.getWorld(movie.getWorldName());
            if (world == null) throw new IllegalStateException("WORLD '" + movie.getWorldName() + "' IS NOT LOADED!");
    
            cutPlayerList.forEach(CutPlayer::initializeAtMainThread);
            
            initialized = true;
        }
    }

    public @NotNull World getWorld() {
        if(world == null) throw new IllegalStateException("Do not call getWorld() before the initialization is complete.");
        return world;
    }

    public void setWorld(@NotNull World world) {this.world = world;}


    private boolean paused = false;
    
    private boolean reset = false;
    
    private boolean autoCancel = true;
    
    public boolean isAutoCancel() {return autoCancel;}
    
    public void setAutoCancel(boolean autoCancel) {this.autoCancel = autoCancel;}
    
    public void pause(){
        this.paused = true;
        this.reset = true;
    }
    
    public void restart(){this.paused = false;}

    private int tick = 0;
    
    public void setTick(int tick) {this.tick = tick;}
    
    public int getTick() {return tick;}
    
    @Override
    public void run() {
        if(paused){
            return;
        }
        
        try{
            playTick(tick, reset);
            reset = false;
        }catch (Exception e){
            if(exceptionConsumer == null){
                e.printStackTrace();
            }else{
                exceptionConsumer.accept(e);
            }

            cancel();
        }

        if(tick == endTick){
            audience.getPlayer().sendMessage("play end");
            if(autoCancel){
                cancel();
            }else{
                pause();
            }
            return;
        }

        tick++;
    }
    
    private boolean started = false;

    public synchronized void runAtMainThread(){
        if(!started) {
            super.runTaskTimer(CraftersCutAPI.getInstance().getPlugin(), 0, 1);
            started = true;
        }
    }

    public synchronized void runAtAsyncThread(){
        if(!started) {
            super.runTaskTimerAsynchronously(CraftersCutAPI.getInstance().getPlugin(), 0, 1);
            started = true;
        }
    }
}
