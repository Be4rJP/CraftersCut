package be4rjp.crafterscut.api;

import be4rjp.crafterscut.api.data.movie.Movie;
import be4rjp.crafterscut.api.nms.INMSHandler;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class CraftersCutAPI {

    private static CraftersCutAPI instance;

    public CraftersCutAPI(Plugin plugin, INMSHandler nmsHandler) {
        this.plugin = plugin;
        this.nmsHandler = nmsHandler;
    }

    public static CraftersCutAPI getInstance() {return instance;}


    protected final Plugin plugin;

    protected final INMSHandler nmsHandler;

    public Plugin getPlugin() {return plugin;}

    public INMSHandler getNMSHandler() {return nmsHandler;}


    public CompletableFuture<MoviePlayer> createMoviePlayerForAsyncThread(Movie movie, Player audience){
        return createMoviePlayerForAsyncThread(movie, audience, null);
    }

    public CompletableFuture<MoviePlayer> createMoviePlayerForAsyncThread(Movie movie, Player audience, Consumer<Exception> exceptionConsumer){
        CompletableFuture<MoviePlayer> completableFuture = new CompletableFuture<>();
        MoviePlayer moviePlayer = new MoviePlayer(movie, audience, exceptionConsumer);

        Bukkit.getScheduler().runTask(plugin, () -> {
            moviePlayer.initializeAtMainThread();
            completableFuture.complete(moviePlayer);
        });

        return completableFuture;
    }

    public MoviePlayer createMoviePlayer(Movie movie, Player audience){
        return createMoviePlayer(movie, audience, null);
    }

    public MoviePlayer createMoviePlayer(Movie movie, Player audience, Consumer<Exception> exceptionConsumer){
        if(!Bukkit.isPrimaryThread()) throw new IllegalStateException("DO NOT CALL FROM ASYNC THREAD!");

        MoviePlayer moviePlayer = new MoviePlayer(movie, audience, exceptionConsumer);
        moviePlayer.initializeAtMainThread();
        return moviePlayer;
    }

}
