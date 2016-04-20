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

/**
 *
 * @author Chris
 */
public class StartState extends AbstractAppState implements ActionListener{
    
    BitmapText welcomeText;
    BitmapFont bmf;
    Main main;
    AppStateManager asm;
    InputManager inputManager;
    private AppStateManager stateManager;
    
        
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
    public void initialize(AppStateManager statManager, Application app){
        main = (Main)app;
        asm = stateManager;
        
        initKeys();
        main.initTower();
        main.getFlyByCamera().setEnabled(false);
        main.getFlyByCamera().setDragToRotate(true);
    }
    //==========================================================================
    private void initKeys(){
        inputManager = main.getInputManager();
        inputManager.addMapping("Start", new KeyTrigger(KeyInput.KEY_NUMPADENTER));
        inputManager.addMapping("Quit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addListener(this, "Start", "Quit");
    }
}

