package be4rjp.crafterscut.api.gui.map.component;

import be4rjp.crafterscut.api.gui.map.CanvasBuffer;
import be4rjp.crafterscut.api.gui.map.MapGUIRenderer;
import org.bukkit.map.MinecraftFont;

public class MapTextComponent extends MapComponent {
    
    protected static final MinecraftFont minecraftFont = MapGUIRenderer.FONT;
    
    
    protected String text;
    
    protected String rawText;
    
    protected boolean drawGrayBack;
    
    protected MapComponentBoundingBox boundingBox;
    
    protected int width = 0;
    
    protected int height = 0;
    
    public MapTextComponent(String text, boolean drawGrayBack, int x, int z, MapClickRunnable clickRunnable){
        super(x, z, clickRunnable);
        this.setText(text);
        this.drawGrayBack = drawGrayBack;
    }
    
    public boolean isDrawGrayBack() {return drawGrayBack;}
    
    public void setDrawGrayBack(boolean drawGrayBack) {this.drawGrayBack = drawGrayBack;}
    
    public String getRawText() {return rawText;}
    
    public String getText() {return text;}
    
    public int getHeight() {return height;}
    
    public int getWidth() {return width;}
    
    
    public void setText(String text){
        this.text = text;
        
        char[] chars = text.toCharArray();
        boolean foundSection = false;
        for(int index = 0; index < chars.length; index++){
            
            if(chars[index] == '§') foundSection = true;
            if(chars[index] == ';') foundSection = false;
            
            if(foundSection){
                chars[index] = ';';
            }
        }
        
        this.rawText = new String(chars).replace(";", "");
        
        int width = minecraftFont.getWidth(rawText);
        int height = minecraftFont.getHeight();
        
        int startXPixel = x - 1;
        int startZPixel = z - 2;
        
        int endXPixel = startXPixel + width + 1;
        int endZPixel = startZPixel + height + 2;
        
        this.width = width + 2;
        this.height = height + 4;
        
        if(clickRunnable != null) this.boundingBox = new MapComponentBoundingBox(startXPixel, startZPixel, endXPixel, endZPixel);
    }
    
    
    @Override
    public void setPixels(CanvasBuffer canvasBuffer) {
        if(drawGrayBack) {
            
            int width = minecraftFont.getWidth(rawText);
            int height = minecraftFont.getHeight();
            
            int startXPixel = x - 1;
            int startZPixel = z - 2;
            
            int endXPixel = startXPixel + width + 1;
            int endZPixel = startZPixel + height + 2;
            
            for(int pixelX = startXPixel; pixelX <= endXPixel; pixelX++){
                for(int pixelZ = startZPixel; pixelZ <= endZPixel; pixelZ++){
                    int color = canvasBuffer.getPixel(pixelX, pixelZ);
                    color = color >> 2 << 2 | 3;
                    canvasBuffer.setPixel(pixelX, pixelZ, (byte) color);
                }
            }
        }
        
        canvasBuffer.drawText(x, z, minecraftFont, text);
    }
    
    
    @Override
    public MapComponentBoundingBox getBoundingBox() {return this.boundingBox;}
}
