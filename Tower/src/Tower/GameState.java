/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tower;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

/**
 *
 * @author Chris
 */
public class GameState extends AbstractAppState implements ActionListener{
    private Main main;
    private AppStateManager asm;
    private InputManager inputManager;
    private boolean paused = false;
    
    protected GameState(){
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        cleanup();
        main = (Main) app;
        asm = stateManager;
        inputManager = app.getInputManager();
        inputManager.clearMappings();
        main.initPhysics();
        main.initTower();
        main.initCharacter();
        main.initCamera();
        initKeys();
    }
    
    private void initKeys(){
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("EndState", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("Quit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        
        inputManager.addListener(this, "Pause", "Quit", "EndState");
    }   

    public void onAction(String name, boolean isPressed, float tpf) {
        if(isPressed){
            if(name.equals("EndState")){
                EndState end = new EndState(); //TODO Add the height and time
                //StartState end = new StartState();
                asm.detach(this);
                asm.attach(end);
                System.out.println("Reached here");
            }
            if(name.equals("Pause")){
                if(paused){
                    paused = false;
                    //remove pause information
                }
                else{
                    paused = true;
                    //add pause information
                }
            }
            if(name.equals("Quit")){
               System.exit(0);
            }
        }
    }
}
