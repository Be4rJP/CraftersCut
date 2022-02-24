package be4rjp.crafterscut.api.player.cut;

import be4rjp.crafterscut.api.data.cut.Cut;
import be4rjp.crafterscut.api.player.movie.MoviePlayer;

public abstract class CutPlayer<T extends Cut> {

    protected final MoviePlayer moviePlayer;
    protected final T cut;

    public CutPlayer(MoviePlayer moviePlayer, T cut){
        this.moviePlayer = moviePlayer;
        this.cut = cut;
    }

    public T getCut() {return cut;}

    public MoviePlayer getMoviePlayer() {return moviePlayer;}

    public abstract void initializeAtMainThread();

    public abstract void onMovieStart();

    public abstract void onMovieEnd();

    public abstract void onStart();

    public abstract void onEnd();

    public abstract void playTick(int tick);
    
    public abstract void playInitializeTick(int tick);

}
