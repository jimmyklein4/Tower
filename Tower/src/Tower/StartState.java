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
    
    private Picture pic;
    private Main main;
    private AppStateManager asm;
    private InputManager inputManager;
    private BitmapFont bmf;
    private BitmapText level1, level2, viewControls,
                       controlExit, controlQuit, controlPause,
                       controlJump, controlMove, controlSwitch;
    private boolean controlView = false;
    
        
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
            if(name.equals("ViewControls")){
                viewControls();
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
        inputManager.addMapping("ViewControls", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addListener(this, "Start", "Quit", "Level1", "Level2", "ViewControls");
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
        viewControls = new BitmapText(bmf);
        controlExit = new BitmapText(bmf);
        controlJump = new BitmapText(bmf);
        controlMove = new BitmapText(bmf);
        controlPause = new BitmapText(bmf);
        controlQuit = new BitmapText(bmf);
        controlSwitch = new BitmapText(bmf);
        
        level1.setColor(ColorRGBA.Black);
        level2.setColor(ColorRGBA.Black);
        viewControls.setColor(ColorRGBA.Black);
        controlExit.setColor(ColorRGBA.Black);
        controlQuit.setColor(ColorRGBA.Black);
        controlPause.setColor(ColorRGBA.Black);
        controlJump.setColor(ColorRGBA.Black);
        controlMove.setColor(ColorRGBA.Black);
        controlSwitch.setColor(ColorRGBA.Black);
        
        level1.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        level2.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        viewControls.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        controlExit.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        controlQuit.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        controlPause.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        controlJump.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        controlMove.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        controlSwitch.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        
        level1.setText("Press [1] to start Level 1");
        level2.setText("Press [2] to start Level 2");
        viewControls.setText("Press [C] to view controls");
        controlExit.setText("Press [ESC] to exit");
        controlQuit.setText("Press [Q] to quit");
        controlPause.setText("Press [P] to pause");
        controlJump.setText("Press [SPACE] to jump");
        controlMove.setText("Press [WASD] to move");
        controlSwitch.setText("Press [UP DOWN LEFT RIGHT] to move the tower");
        
        level1.setLocalTranslation(new Vector3f(0, level1.getHeight() * 2, 0));
        level2.setLocalTranslation(new Vector3f(0, level2.getHeight(), 0));
        viewControls.setLocalTranslation(new Vector3f(0, level2.getHeight() * 3, 0));
        controlExit.setLocalTranslation(new Vector3f(0,
                Main.screenHeight,0));
        controlQuit.setLocalTranslation(new Vector3f(0,
                Main.screenHeight-controlExit.getHeight(),0));
        controlPause.setLocalTranslation(new Vector3f(0,
                Main.screenHeight-controlExit.getHeight()-controlQuit.getHeight(),0));
        controlJump.setLocalTranslation(new Vector3f(0,
                Main.screenHeight-controlExit.getHeight()-controlQuit.getHeight()
                -controlPause.getHeight(),0));
        controlMove.setLocalTranslation(new Vector3f(0,
                Main.screenHeight-controlExit.getHeight()-controlQuit.getHeight()
                -controlPause.getHeight()-controlJump.getHeight(),0));
        controlSwitch.setLocalTranslation(new Vector3f(0,
                Main.screenHeight-controlExit.getHeight()-controlQuit.getHeight()
                -controlPause.getHeight()-controlJump.getHeight()-controlMove.getHeight(), 0));
        
        main.getGuiNode().attachChild(level1);
        main.getGuiNode().attachChild(level2);
        main.getGuiNode().attachChild(viewControls);
    }
    //==========================================================================
    private void viewControls(){
        if(controlView){
            controlView = false;
            main.getGuiNode().detachAllChildren();
            main.getGuiNode().attachChild(pic);
            main.getGuiNode().attachChild(level1);
            main.getGuiNode().attachChild(level2);
            main.getGuiNode().attachChild(viewControls);
        }
        else{
            controlView = true;
            main.getGuiNode().detachAllChildren();
            main.getGuiNode().attachChild(controlExit);
            main.getGuiNode().attachChild(controlQuit);
            main.getGuiNode().attachChild(controlPause);
            main.getGuiNode().attachChild(controlJump);
            main.getGuiNode().attachChild(controlMove);
            main.getGuiNode().attachChild(controlSwitch);
        }
    }
}

