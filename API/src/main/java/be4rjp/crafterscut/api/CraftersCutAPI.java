package be4rjp.crafterscut.api;

import be4rjp.crafterscut.api.data.movie.Movie;
import be4rjp.crafterscut.api.nms.INMSHandler;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class CraftersCutAPI {

    private static CraftersCutAPI instance;

    public static CraftersCutAPI getInstance() {return instance;}


    protected final Plugin plugin;

    protected final INMSHandler nmsHandler;
    
    public CraftersCutAPI(Plugin plugin, INMSHandler nmsHandler) {
        this.plugin = plugin;
        this.nmsHandler = nmsHandler;
    }
    
    
    public Plugin getPlugin() {return plugin;}

    public INMSHandler getNMSHandler() {return nmsHandler;}

    public @Nullable CCPlayer getCCPlayer(Player player){return CCPlayer.getCCPlayer(player);}

    public MoviePlayer createMoviePlayer(Movie movie, CCPlayer audience){
        return createMoviePlayer(movie, audience, null);
    }

    public MoviePlayer createMoviePlayer(Movie movie, CCPlayer audience, Consumer<Exception> exceptionConsumer){
        if(!Bukkit.isPrimaryThread()) throw new IllegalStateException("DO NOT CALL FROM ASYNC THREAD!");

        MoviePlayer moviePlayer = new MoviePlayer(movie, audience, exceptionConsumer);
        moviePlayer.initializeAtMainThread();
        return moviePlayer;
    }

}
