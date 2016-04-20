package Tower;

/**
 * Audio Credit: Adam Goh
 * Audio Source: http://www.flashkit.com/soundfx/Ambience/TEMPLE-Adam_Goh-7394/index.php
 * 
 */

import com.jme3.app.DebugKeysAppState;
import com.jme3.scene.Node;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.util.SkyFactory;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * @author Jimmy
 * @author Chris
 * @author Theo
 */
public class Main extends SimpleApplication {
    
    private Node startNode = new Node();
    private AudioNode ambientSound;
    private Vector3f st = new Vector3f(0,0,4);
    BulletAppState bullet; 
    private TowerObject tower;
    public CustomCamera customCamera;
    CharacterObject character;
    Lighting light;
    Spatial sky;
    //==========================================================================
    public static void main(String[] args) {
        Main app = new Main();
        initAppScreen(app);
        app.start();
    }
    //==========================================================================
    @Override
    public void simpleInitApp() {
        light = new Lighting(this);
        viewPort.setBackgroundColor(ColorRGBA.White);
        //initTower();
        //initCharacter();
        //initCamera();
        //initAudio();
        //setDisplayStatView(false);
        //System.out.println(customCamera.getRotate());
        
        StartState start = new StartState();
        stateManager.attach(start);
    }
    //==========================================================================
    @Override
    public void simpleUpdate(float tpf) {
        
    }
    //==========================================================================
    @Override
    public void simpleRender(RenderManager rm) {
    }
    //==========================================================================
    public CustomCamera getCustomCamera(){
        return customCamera;
    }
    //==========================================================================
    public Lighting getLighting(){
        return light;
    }
    //==========================================================================
    public Node getStartNode(){
        return startNode;
    }
    //==========================================================================
    private static void initAppScreen(SimpleApplication sa){
        AppSettings apps = new AppSettings(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen.width *=0.75;
        screen.height *= 0.75;
        apps.setResolution(screen.width, screen.height);
        sa.setSettings(apps);
        sa.setShowSettings(false);
    }
    //==========================================================================
    public void initPhysics(){
        bullet = new BulletAppState();
        stateManager.attach(bullet);
        bullet.setDebugEnabled(true);

    }
    //==========================================================================
    public void initSky(){
        sky = SkyFactory.createSky(assetManager, 
        assetManager.loadTexture("Textures/SkyboxN.png"), 
        assetManager.loadTexture("Textures/SkyboxN.png"), 
        assetManager.loadTexture("Textures/SkyboxN.png"), 
        assetManager.loadTexture("Textures/SkyboxN.png"), 
        assetManager.loadTexture("Textures/SkyboxTop.png"), 
        assetManager.loadTexture("Textures/SkyboxBottom.png"));
        rootNode.attachChild(sky);
    }
    //==========================================================================
    public void initCharacter(){
        character = new CharacterObject(this);
        flyCam.setEnabled(false);
        tower.getTowerNode().attachChild(character.getCharacterNode());
        tower.getTowerNode().attachChild(character.follow);

    }
    //==========================================================================
    public void initTower(){
        tower = new TowerObject(this, 1);
    }
    //==========================================================================
    public void initAudio(){
        ambientSound = new AudioNode(assetManager, "Sounds/TEMPLE-Adam_Goh-7394_hifi.wav", false);
        ambientSound.setPositional(false);
        ambientSound.setLooping(true);
        ambientSound.setVolume(4);
        rootNode.attachChild(ambientSound);
        ambientSound.play();
    }
    //==========================================================================
    public void initCamera(){
        customCamera = new CustomCamera(this, character);    
    }
    //==========================================================================
    public Node getCharacter(){
        return character.getCharacterNode();
    }
    //==========================================================================
    public void initStart(){
        startNode.setLocalTranslation(new Vector3f(0,0,4));
        rootNode.attachChild(startNode);
    }
}
