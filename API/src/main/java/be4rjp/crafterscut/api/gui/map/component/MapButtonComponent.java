package be4rjp.crafterscut.api.gui.map.component;

import be4rjp.crafterscut.api.gui.map.CanvasBuffer;
import be4rjp.crafterscut.api.gui.map.MapGUIRenderer;

public class MapButtonComponent extends MapTextComponent{
    
    private final String popUpText;
    
    private boolean pressed = false;
    
    public MapButtonComponent(String text, String popUpText, boolean drawGrayBack, int x, int z, MapClickRunnable clickRunnable) {
        super(text, drawGrayBack, x, z, clickRunnable);
        
        this.popUpText = popUpText;
    }
    
    public boolean isPressed() {return pressed;}
    
    public void setPressed(boolean pressed) {this.pressed = pressed;}
    
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
        
        int width = minecraftFont.getWidth(rawText);
        int height = minecraftFont.getHeight();
        
        int startXPixel = x - 2;
        int startZPixel = z - 3;
        
        int endXPixel = startXPixel + width + 3;
        int endZPixel = startZPixel + height + 4;
    
        this.width = width + 5;
        this.height = height + 7;
        
        if(clickRunnable != null) this.boundingBox = new MapComponentBoundingBox(startXPixel, startZPixel, endXPixel, endZPixel);
    }
    
    @Override
    public void setPixels(CanvasBuffer canvasBuffer) {
        if(drawGrayBack) {
            int width = minecraftFont.getWidth(rawText);
            int height = minecraftFont.getHeight();
        
            int startXPixel = x - 2;
            int startZPixel = z - 3;
        
            int endXPixel = startXPixel + width + 3;
            int endZPixel = startZPixel + height + 4;
        
            for(int pixelX = startXPixel; pixelX <= endXPixel; pixelX++){
                for(int pixelZ = startZPixel; pixelZ <= endZPixel; pixelZ++){
                    canvasBuffer.setPixel(pixelX, pixelZ, (byte) 26);
                }
            }
    
            MapGUIRenderer.drawSquare(canvasBuffer, startXPixel, startZPixel, endXPixel, startZPixel + 1, (byte) (pressed ? 24 : 33));
            MapGUIRenderer.drawSquare(canvasBuffer, startXPixel, startZPixel, startXPixel + 1, endZPixel, (byte) (pressed ? 24 : 33));
            MapGUIRenderer.drawSquare(canvasBuffer, startXPixel, endZPixel - 1, endXPixel, endZPixel, (byte) (pressed ? 33 : 24));
            MapGUIRenderer.drawSquare(canvasBuffer, endXPixel - 1, startZPixel, endXPixel, endZPixel, (byte) (pressed ? 33 : 24));
            
            canvasBuffer.setPixel(startXPixel + 1, endZPixel - 1, (byte) 26);
            canvasBuffer.setPixel(endXPixel - 1, startZPixel + 1, (byte) 26);
    
            MapGUIRenderer.drawSquare(canvasBuffer, startXPixel, startZPixel, startXPixel, endZPixel, (byte) 116);
            MapGUIRenderer.drawSquare(canvasBuffer, startXPixel, startZPixel, endXPixel, startZPixel, (byte) 116);
            MapGUIRenderer.drawSquare(canvasBuffer, endXPixel, startZPixel, endXPixel, endZPixel, (byte) 116);
            MapGUIRenderer.drawSquare(canvasBuffer, startXPixel, endZPixel, endXPixel, endZPixel, (byte) 116);
        }
    
        canvasBuffer.drawText(x, z, minecraftFont, text);
    }
    
    public String getPopUpText() {return popUpText;}
}
