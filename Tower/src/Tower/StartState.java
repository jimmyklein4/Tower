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
    BitmapFont bmf;
    BitmapText level1, level2;
        
    //==========================================================================
    public void onAction(String name, boolean isPressed, float tpf) {
        if(isPressed){
            if(name.equals("Start")){
                GameState game = new GameState(1);
                asm.detach(this);
                asm.attach(game);
            }
            if(name.equals("Level1")){
                GameState game = new GameState(1);
                asm.detach(this);
                asm.attach(game);
            }
            if(name.equals("Level2")){
                GameState game = new GameState(2);
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
        inputManager.addMapping("Level1", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("Level2", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addListener(this, "Start", "Quit", "Level1", "Level2");
    }
    //==========================================================================
    private void initText(){
        pic = new Picture("Start Pic");
        pic.setImage(main.getAssetManager(), "Materials/Text/title.png", true);
        pic.setWidth(Main.screenWidth);
        pic.setHeight(Main.screenHeight);
        pic.setPosition(0, 0);
        main.getGuiNode().attachChild(pic);
        
        bmf = main.getAssetManager().loadFont("Interface/Fonts/Jokerman.fnt");
        level1 = new BitmapText(bmf);
        level2 = new BitmapText(bmf);
        
        level1.setColor(ColorRGBA.Black);
        level2.setColor(ColorRGBA.Black);
        
        level1.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        level2.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        
        level1.setText("Press [1] to start Level 1");
        level2.setText("Press [2] to start Level 2");
        
        level1.setLocalTranslation(new Vector3f(0, level1.getHeight() * 2, 0));
        level2.setLocalTranslation(new Vector3f(0, level2.getHeight(), 0));
        
        main.getGuiNode().attachChild(level1);
        main.getGuiNode().attachChild(level2);
    }
    //==========================================================================
}

