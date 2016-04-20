/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tower;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author Chris
 */
public class EndState extends AbstractAppState implements ActionListener{
    private BitmapFont bmf;
    private BitmapText height, time, endMessage;
    private AppStateManager asm;
    private Main main;
    private InputManager input;
    
    public EndState(){
        
    }
    
    /**
     * 
     * @param height: Height climbed
     * @param time: Time spent in game
     */
    public EndState(String height, String time){
        this.height.setText(height);
        this.time.setText(time);
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        main = (Main) app;
        asm = stateManager;
        bmf = main.getAssetManager().loadFont("Interface/Fonts/Jokerman.fnt");
        height = new BitmapText(bmf);
        //height.setColor(ColorRGBA.Blue);
        //height.setSize(size);
        //height.setLocalTranslation(x, y, z);
        time = new BitmapText(bmf);
        //time.setColor(ColorRGBA.Blue);
        //time.setSize(size);
        //time.setLocalTranslation(x, y, z);
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
