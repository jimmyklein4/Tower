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
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 *
 * @author Chris
 */
public class EndState extends AbstractAppState implements ActionListener{
    private BitmapFont bmf;
    private BitmapText time, endMessage, heightText;
    private float height;
    private AppStateManager asm;
    private Main main;
    private InputManager inputManager;
    private Boolean won;
    //==========================================================================
    public EndState(){
        
    }
    //==========================================================================
    /**
     * 
     * @param height: Height climbed
     * @param time: Time spent in game
     */
    public EndState(BitmapText time, float height, Boolean won){
        this.height = height;
        this.time = time;
        this.won = won;
    }
    //==========================================================================
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        main = (Main) app;
        asm = stateManager;
        
        main.killAll(main);
        inputManager = main.getInputManager();
        main.initSky();
        
        main.getFlyByCamera().setEnabled(false);
        initKeys();
        initText();
    }
    //==========================================================================
    public void onAction(String name, boolean isPressed, float tpf) {
        if(isPressed){
            if(name.equals("Exit") || name.equals("Quit")){
                main.stop();
            }
            if(name.equals("New")){
                StartState start = new StartState();
                asm.detach(this);
                asm.attach(start);
                main.getGuiNode().detachAllChildren();
            }
        }
    }
    //==========================================================================
    @Override
    public void update(float tpf){
    }
    //==========================================================================
    private void initKeys(){
        inputManager.addMapping("Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addMapping("Quit", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("New", new KeyTrigger(KeyInput.KEY_SPACE));
        
        inputManager.addListener(this, "Exit", "New", "Quit");
    }
    //==========================================================================
    private void initText(){
        bmf = main.getAssetManager().loadFont("Interface/Fonts/Jokerman.fnt");
        endMessage = new BitmapText(bmf);
        heightText = new BitmapText(bmf);
        
        endMessage.setColor(ColorRGBA.Black);
        heightText.setColor(ColorRGBA.Black);
        time.setColor(ColorRGBA.Black);
        
        String t = String.format("Height: %3.1f \n\nPress Space to start over", height);
        heightText.setText(t);
        
        if(won){
            endMessage.setText("You Win!");
        }
        else{
            endMessage.setText("Game Over");
        }
        
        endMessage.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 450);
        heightText.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        
        endMessage.setLocalTranslation(new Vector3f(
                Main.screenWidth / 2 - (endMessage.getLineWidth()/2),
                Main.screenHeight / 2 + endMessage.getHeight(),
                0));
        time.setLocalTranslation(new Vector3f(
                Main.screenWidth / 2 - (endMessage.getLineWidth()/2),
                Main.screenHeight / 2 + endMessage.getLineHeight() - time.getHeight() * 2,
                0));
        heightText.setLocalTranslation(new Vector3f(
                Main.screenWidth / 2 - (endMessage.getLineWidth()/2),
                Main.screenHeight / 2 + endMessage.getLineHeight() - time.getHeight() * 3,
                0));
        
        
        main.getGuiNode().attachChild(time);
        main.getGuiNode().attachChild(endMessage);
        main.getGuiNode().attachChild(heightText);
    }
}
