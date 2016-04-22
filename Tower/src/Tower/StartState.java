package Tower;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.ui.Picture;

/**
 *
 * @author Chris
 */
public class StartState extends AbstractAppState implements ActionListener{
    
    Picture pic;
    Main main;
    AppStateManager asm;
    InputManager inputManager;
        
    //==========================================================================
    public void onAction(String name, boolean isPressed, float tpf) {
        if(isPressed){
            if(name.equals("Start")){
                GameState game = new GameState();
                asm.detach(this);
                asm.attach(game);
            }
            if(name.equals("Quit")){
                main.stop();
            }
        }
    }
    //==========================================================================
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        //cleanup();
        main = (Main)app;
        Main.killAll(main);
        asm = stateManager;
        main.initSky();
        initKeys();
        initText();
        main.getFlyByCamera().setEnabled(false);
        main.setDisplayStatView(false);
    }
    //==========================================================================
    private void initKeys(){
        inputManager = main.getInputManager();
        inputManager.addMapping("Start", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Quit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addListener(this, "Start", "Quit");
    }
    //==========================================================================
    private void initText(){
        
        pic = new Picture("Start Pic");
        pic.setImage(main.getAssetManager(), "Materials/Text/title.png", true);
        pic.setWidth(Main.screenWidth);
        pic.setHeight(Main.screenHeight);
        pic.setPosition(0, 0);
        main.getGuiNode().attachChild(pic);
    }
    //==========================================================================
}

