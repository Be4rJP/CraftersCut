package be4rjp.crafterscut.api.player;

import be4rjp.crafterscut.api.data.Cut;

public abstract class CutPlayer {

    protected final MoviePlayer moviePlayer;
    protected final Cut cut;

    protected CutPlayer(MoviePlayer moviePlayer, Cut cut){
        this.moviePlayer = moviePlayer;
        this.cut = cut;
    }

    public Cut getCut() {return cut;}

    public MoviePlayer getMoviePlayer() {return moviePlayer;}

    public abstract void initializeAtSync();

    public abstract void onMovieStart();

    public abstract void onMovieEnd();

    public abstract void onStart();

    public abstract void onEnd();

}
