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
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        main = (Main) app;
        asm = stateManager;
        inputManager = app.getInputManager();
        main.initTower();
        main.initCharacter();
        main.initCamera();
        initKeys();
    }
    
    private void initKeys(){
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Quit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        
        inputManager.addListener(this, "Pause", "Quit");
    }   

    public void onAction(String name, boolean isPressed, float tpf) {
        if(isPressed){
            if(name.equals("Quit")){
                EndState end = new EndState(); //TODO Add the height and time
                asm.detach(this);
                asm.attach(end);
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
        }
    }
}
