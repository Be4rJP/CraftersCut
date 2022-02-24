package be4rjp.crafterscut.api.gui.map.component;

import be4rjp.crafterscut.api.gui.map.CanvasBuffer;
import be4rjp.crafterscut.api.gui.map.MapGUIRenderer;

public class MapLaneComponent extends MapButtonComponent{
    
    private final int laneWidth;
    private final byte color;
    
    private final boolean drawLeft;
    private final boolean drawRight;
    
    public MapLaneComponent(String text, String popUpText, boolean drawGrayBack, int startX, int startZ, int width, byte color, boolean drawLeft, boolean drawRight, MapClickRunnable clickRunnable) {
        super(text, popUpText, drawGrayBack, startX, startZ, clickRunnable);
        this.laneWidth = width;
        this.color = color;
        this.drawLeft = drawLeft;
        this.drawRight = drawRight;
        setText(text);
    }
    
    public boolean isDrawLeft() {return drawLeft;}
    
    public boolean isDrawRight() {return drawRight;}
    
    public int getLaneWidth() {return laneWidth;}
    
    
    @Override
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
        
        int height = minecraftFont.getHeight();
        
        int startXPixel = x - 2;
        int startZPixel = z - 3;
        
        int endXPixel = startXPixel + this.laneWidth + 4;
        int endZPixel = startZPixel + height + 4;
        
        this.width = this.laneWidth + 5;
        this.height = height + 7;
        
        if(clickRunnable != null) this.boundingBox = new MapComponentBoundingBox(startXPixel, startZPixel, endXPixel, endZPixel);
    }
    
    @Override
    public void setPixels(CanvasBuffer canvasBuffer) {
        if(drawGrayBack) {
            int height = minecraftFont.getHeight();
    
            int startXPixel = x - 2;
            int startZPixel = z - 3;
    
            int endXPixel = startXPixel + laneWidth + 3;
            int endZPixel = startZPixel + height + 4;
            
            for(int pixelX = startXPixel; pixelX <= endXPixel; pixelX++){
                for(int pixelZ = startZPixel; pixelZ <= endZPixel; pixelZ++){
                    canvasBuffer.setPixel(pixelX, pixelZ, color);
                }
            }
            
            MapGUIRenderer.drawSquare(canvasBuffer, startXPixel, startZPixel, endXPixel, startZPixel + 1, (byte) (pressed ? 24 : 33));
            if(drawLeft) MapGUIRenderer.drawSquare(canvasBuffer, startXPixel, startZPixel, startXPixel + 1, endZPixel, (byte) (pressed ? 24 : 33));
            MapGUIRenderer.drawSquare(canvasBuffer, startXPixel, endZPixel - 1, endXPixel, endZPixel, (byte) (pressed ? 33 : 24));
            if(drawRight) MapGUIRenderer.drawSquare(canvasBuffer, endXPixel - 1, startZPixel, endXPixel, endZPixel, (byte) (pressed ? 33 : 24));
            
            if(drawLeft) canvasBuffer.setPixel(startXPixel + 1, endZPixel - 1, (byte) 26);
            if(drawRight) canvasBuffer.setPixel(endXPixel - 1, startZPixel + 1, (byte) 26);
    
            if(drawLeft) MapGUIRenderer.drawSquare(canvasBuffer, startXPixel, startZPixel, startXPixel, endZPixel, (byte) 116);
            MapGUIRenderer.drawSquare(canvasBuffer, startXPixel, startZPixel, endXPixel, startZPixel, (byte) 116);
            if(drawRight) MapGUIRenderer.drawSquare(canvasBuffer, endXPixel, startZPixel, endXPixel, endZPixel, (byte) 116);
            MapGUIRenderer.drawSquare(canvasBuffer, startXPixel, endZPixel, endXPixel, endZPixel, (byte) 116);
        }
        
        canvasBuffer.drawText(x, z, minecraftFont, text);
    }
    
}
