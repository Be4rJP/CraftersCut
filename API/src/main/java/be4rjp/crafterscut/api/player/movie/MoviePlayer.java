package be4rjp.crafterscut.api.player.movie;

import be4rjp.crafterscut.api.CraftersCutAPI;
import be4rjp.crafterscut.api.data.cut.Cut;
import be4rjp.crafterscut.api.data.movie.Movie;
import be4rjp.crafterscut.api.player.cut.CutPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MoviePlayer extends BukkitRunnable {

    private final Movie movie;
    private final Player audience;
    private final Consumer<Exception> exceptionConsumer;

    private World world;

    private final List<CutPlayer<? extends Cut>> cutPlayerList;

    private int endTick = 0;

    public MoviePlayer(Movie movie, Player audience, Consumer<Exception> exceptionConsumer) {
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

    public Player getAudience() {return audience;}

    public void playTick(int tick){
        for(CutPlayer<? extends Cut> cutPlayer : cutPlayerList){
            Cut cut = cutPlayer.getCut();

            if(cut.notTickRange(tick)) continue;

            if(cut.getStartTick() == tick) cutPlayer.onStart();
            if(cut.getEndTick() == tick) cutPlayer.onEnd();

            cutPlayer.playTick(tick);
        }
    }

    public void initializeAtMainThread() {
        if(!Bukkit.isPrimaryThread()) throw new IllegalStateException("DO NOT CALL FROM ASYNC THREAD!");

        this.world = Bukkit.getWorld(movie.getWorldName());
        if(world == null) throw new IllegalStateException("WORLD '" + movie.getWorldName() + "' IS NOT LOADED!");

        cutPlayerList.forEach(CutPlayer::initializeAtMainThread);
    }

    public @NotNull World getWorld() {
        if(world == null) throw new IllegalStateException("Do not call getWorld() before the initialization is complete.");
        return world;
    }

    public void setWorld(@NotNull World world) {this.world = world;}



    private int tick = 0;

    @Override
    public void run() {
        try{
            playTick(tick);
        }catch (Exception e){
            if(exceptionConsumer == null){
                e.printStackTrace();
            }else{
                exceptionConsumer.accept(e);
            }

            cancel();
        }

        if(tick == endTick){
            audience.sendMessage("play end");
            cancel();
            return;
        }

        tick++;
    }

    public void runAtMainThread(){
        super.runTaskTimer(CraftersCutAPI.getInstance().getPlugin(), 0, 1);
    }

    public void runAtAsyncThread(){
        super.runTaskTimerAsynchronously(CraftersCutAPI.getInstance().getPlugin(), 0, 1);
    }
}
