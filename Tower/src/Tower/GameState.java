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
import com.jme3.system.AppSettings;

/**
 *
 * @author Chris
 */
public class GameState extends AbstractAppState implements ActionListener{
    private Main main;
    private AppStateManager asm;
    private InputManager inputManager;
    private BitmapFont bmf;
    private BitmapText pauseText, controlJump, controlMove, controlPause, 
                       controlQuit, controlExit, controlSwitch, timeText, livesText;
    private float gameTime;
    private int level;
    //==========================================================================
    protected GameState(){
    }
    //==========================================================================
    protected GameState(int level){
        this.level = level;
    }
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        cleanup();
        main = (Main) app;
        Main.killAll(main);
        asm = stateManager;
        inputManager = app.getInputManager();
        inputManager.clearMappings();
        main.initSky();
        main.initPhysics();
        main.initTower(level);
        main.initCharacter();
        main.initCamera();
        initKeys();
        initText();
        main.getGuiNode().attachChild(timeText);
    }
    //==========================================================================
    private void initKeys(){
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("EndState", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("Quit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        
        inputManager.addListener(this, "Pause", "Quit", "EndState");
    }   
    //==========================================================================
    public void onAction(String name, boolean isPressed, float tpf) {
        if(isPressed){
            if(name.equals("EndState")){
                EndState end = new EndState(timeText, main.getCharacter().getWorldTranslation().y, false); //TODO Add the height and time
                //StartState end = new StartState();
                asm.detach(this);
                asm.attach(end);
                System.out.println("Reached here");
            }
            if(name.equals("Pause")){
                if(main.paused){
                    main.paused = false;
                    System.out.println("Paused = " + main.paused);
                    main.getGuiNode().detachAllChildren();
                    main.getGuiNode().attachChild(timeText);
                    //remove pause information
                }
                else{
                    main.paused = true;
                    System.out.println("Paused = " + main.paused);
                    attachAllText();
                    //add pause information
                }
            }
            if(name.equals("Quit")){
               System.exit(0);
            }
        }
    }
    //==========================================================================
    private void initText(){
        bmf = main.getAssetManager().loadFont("Interface/Fonts/Jokerman.fnt");
        pauseText = new BitmapText(bmf);
        controlExit = new BitmapText(bmf);
        controlJump = new BitmapText(bmf);
        controlMove = new BitmapText(bmf);
        controlPause = new BitmapText(bmf);
        controlQuit = new BitmapText(bmf);
        controlSwitch = new BitmapText(bmf);
        timeText = new BitmapText(bmf);
        livesText = new BitmapText(bmf);
        
        pauseText.setColor(ColorRGBA.White);
        controlExit.setColor(ColorRGBA.White);
        controlQuit.setColor(ColorRGBA.White);
        controlPause.setColor(ColorRGBA.White);
        controlJump.setColor(ColorRGBA.White);
        controlMove.setColor(ColorRGBA.White);
        controlSwitch.setColor(ColorRGBA.White);
        timeText.setColor(ColorRGBA.Black);
        
        //set livesText, sorry for the block of unrelated code
        //quickly adding my previous code to this commit
        livesText.setSize(bmf.getCharSet().getRenderedSize()*2);
        livesText.setColor(ColorRGBA.Black);
        livesText.setText("Lives: "+ main.character.getLives());
        AppSettings s = main.getSettings();
        float lineY = s.getHeight(); 
        livesText.setLocalTranslation(0, lineY, 0f);
        main.getGuiNode().attachChild(livesText);
        
        pauseText.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 450);
        controlExit.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        controlQuit.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        controlPause.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        controlJump.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        controlMove.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        controlSwitch.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        timeText.setSize(bmf.getCharSet().getRenderedSize() * Main.screenWidth / 800);
        
        pauseText.setText("Paused");
        controlExit.setText("Press [ESC] to exit");
        controlQuit.setText("Press [Q] to quit");
        controlPause.setText("Press [P] to pause");
        controlJump.setText("Press [SPACE] to jump");
        controlMove.setText("Press [WASD] to move");
        controlSwitch.setText("Press [UP DOWN LEFT RIGHT] to move the tower");
        
        
        pauseText.setLocalTranslation(new Vector3f(
                Main.screenWidth / 2 - (pauseText.getLineWidth()/2),
                Main.screenHeight - pauseText.getLineHeight()/2,
                0));
        controlExit.setLocalTranslation(new Vector3f(
                0,
                Main.screenHeight,
                0));
        controlQuit.setLocalTranslation(new Vector3f(
                0,
                Main.screenHeight-controlExit.getHeight(),
                0));
        controlPause.setLocalTranslation(new Vector3f(
                0,
                Main.screenHeight-controlExit.getHeight()-controlQuit.getHeight(),
                0));
        controlJump.setLocalTranslation(new Vector3f(
                0,
                Main.screenHeight-controlExit.getHeight()-controlQuit.getHeight()-controlPause.getHeight(),
                0));
        controlMove.setLocalTranslation(new Vector3f(
                0,
                Main.screenHeight-controlExit.getHeight()-controlQuit.getHeight()-controlPause.getHeight()-controlJump.getHeight(),
                0));
        controlSwitch.setLocalTranslation(new Vector3f(
                0,
                Main.screenHeight-controlExit.getHeight()-controlQuit.getHeight()-controlPause.getHeight()-controlJump.getHeight()-controlMove.getHeight(),
                0));
        timeText.setLocalTranslation(new Vector3f(0, timeText.getHeight(), 0));
    }
    //==========================================================================
    private void attachAllText(){
        main.getGuiNode().attachChild(pauseText);
        main.getGuiNode().attachChild(controlExit);
        main.getGuiNode().attachChild(controlQuit);
        main.getGuiNode().attachChild(controlPause);
        main.getGuiNode().attachChild(controlJump);
        main.getGuiNode().attachChild(controlMove);
        main.getGuiNode().attachChild(controlSwitch);
    }
    //==========================================================================
    @Override
    public void update(float tpf){
        if(main.paused){
            
        }
        else{
            gameTime += tpf;
        }
        String t = String.format("Time: %3.1f", gameTime);
        timeText.setText(t);
        
        if(main.character.getCharacterNode().getLocalTranslation().y<-1){
            if(main.character.die()==0){
                EndState end = new EndState(timeText, main.getCharacter().getWorldTranslation().y, false); //TODO Add the height and time
                //StartState end = new StartState();
                asm.detach(this);
                asm.attach(end);
            }
            else{
                main.character.getCharacterBodyControl().warp(main.character.getCheckPoint());
                String live = "Lives: "+ main.character.getLives();
                livesText.setText(live);
            }
        }
        if(level == 1){
            if(main.character.getCharacterNode().getWorldTranslation().distance(main.tower.tower1.endGate.getWorldTranslation())<0.5f){
                EndState end = new EndState(timeText, main.getCharacter().getWorldTranslation().y, true); //TODO Add the height and time
                //StartState end = new StartState();
                asm.detach(this);
                asm.attach(end);
            }
        }
    }
    //==========================================================================
}
