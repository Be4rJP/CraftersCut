package be4rjp.crafterscut.api.editor.cut;

import be4rjp.crafterscut.api.data.cut.Cut;
import be4rjp.crafterscut.api.editor.ItemClickBase;
import be4rjp.crafterscut.api.editor.MapClickBase;

public abstract class CutEditor implements ItemClickBase, MapClickBase {
    
    protected final Cut cut;
    
    protected boolean isEditing = false;
    
    public CutEditor(Cut cut){
        this.cut = cut;
    }
    
    public Cut getCut() {return cut;}
    
    
    public boolean isEditing() {return isEditing;}
    
    public void setEditing(boolean editing) {
        if(isEditing){
            if(!editing){
                endEditing();
            }
        }else{
            if(editing){
                startEditing();
            }
        }
        
        isEditing = editing;
    }
    
    public abstract void startEditing();
    
    public abstract void endEditing();
}
