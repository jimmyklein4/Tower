/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tower;

import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author Chris
 */
public class TowerEnd {
    private BitmapFont bmf;
    private BitmapText height, time, endMessage;
    private AppStateManager asm;
    private Main main;
    private InputManager input;
    
    /**
     * 
     * @param height: Height climbed
     * @param time: Time spent in game
     */
    public TowerEnd(String height, String time){
        init();
        this.height.setText(height);
        this.time.setText(time);
    }
    private void init(){
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
}
