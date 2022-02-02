package be4rjp.crafterscut.api.player;

import be4rjp.crafterscut.api.data.Movie;
import org.bukkit.entity.Player;

public class MoviePlayer {

    private final Movie movie;
    private final Player audience;

    public MoviePlayer(Movie movie, Player audience) {
        this.movie = movie;
        this.audience = audience;
    }

    public Movie getMovie() {return movie;}

    public Player getAudience() {return audience;}

}
