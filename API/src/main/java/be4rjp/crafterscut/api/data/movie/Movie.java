package be4rjp.crafterscut.api.data.movie;

import be4rjp.crafterscut.api.data.cut.Cut;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private String name;

    private String worldName;

    private final List<Cut> cutList = new ArrayList<>();

    public void addCut(Cut cut){cutList.add(cut);}

    public List<Cut> getCutList() {return cutList;}

    public String getName() {return name;}

    public String getWorldName() {return worldName;}
}
