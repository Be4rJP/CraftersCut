package be4rjp.crafterscut.api.gui.map.component;

import be4rjp.crafterscut.api.CCPlayer;
import be4rjp.crafterscut.api.gui.map.CanvasBuffer;

public abstract class MapComponent {
    
    protected int x;
    
    protected int z;
    
    protected MapClickRunnable clickRunnable;
    
    public MapComponent(int x, int z, MapClickRunnable clickRunnable){
        this.x = x;
        this.z = z;
        this.clickRunnable = clickRunnable;
    }
    
    public abstract void setPixels(CanvasBuffer canvasBuffer);
    
    public void onClick(CCPlayer ccPlayer){
        if(clickRunnable != null) clickRunnable.run(ccPlayer, this);
    }
    
    public abstract MapComponentBoundingBox getBoundingBox();
    
    public int getX() {return x;}
    
    public int getZ() {return z;}
    
    public void setX(int x) {this.x = x;}
    
    public void setZ(int z) {this.z = z;}
    
    public interface MapClickRunnable{
        void run(CCPlayer ccPlayer, MapComponent clicked);
    }
}
