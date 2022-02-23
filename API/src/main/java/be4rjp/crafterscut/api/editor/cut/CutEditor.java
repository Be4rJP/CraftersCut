package be4rjp.crafterscut.api.editor.cut;

import be4rjp.crafterscut.api.data.cut.Cut;

public abstract class CutEditor {
    
    protected final Cut cut;
    
    public CutEditor(Cut cut){
        this.cut = cut;
    }
    
    public Cut getCut() {return cut;}
    
}
