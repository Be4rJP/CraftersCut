package be4rjp.crafterscut.api.data;

import be4rjp.crafterscut.api.player.MoviePlayer;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private final List<Cut> cutList = new ArrayList<>();

    public void addCut(Cut cut){cutList.add(cut);}

    public void playTick(MoviePlayer moviePlayer, int tick){
        for(Cut cut : cutList){
            if(cut.notTickRange(tick)) continue;


        }
    }

}
