package Tower;

import com.jme3.app.DebugKeysAppState;
import com.jme3.scene.Node;
import com.jme3.scene.CameraNode;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.system.AppSettings;
import com.jme3.util.SkyFactory;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * 
 * @author
 */
public class Main extends SimpleApplication {
    
    private Node startNode = new Node();
    private Vector3f st = new Vector3f(0,0,4);
    BulletAppState bullet; 
    TowerObject tower;
    Lighting light;
    Spatial sky;
    private CameraNode camNode;
    ChaseCamera chase;
    CharacterObject character;
    /*
    public Main(){
        super(new StatsAppState(), new DebugKeysAppState());
    }
    */
    public static void main(String[] args) {
        Main app = new Main();
        initAppScreen(app);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        initPhysics();
        initSky();
        startNode.setLocalTranslation(new Vector3f(0,0,4));
        rootNode.attachChild(startNode);
        viewPort.setBackgroundColor(ColorRGBA.White);
        tower = new TowerObject(this);
        initCharacter();
        light = new Lighting(this);
        initCamera();
        setDisplayStatView(false);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //cam.lookAt(tower.getTowerNode().getWorldTranslation(), Vector3f.UNIT_Y);
        Vector3f camLoc = cam.getLocation();
        cam.setLocation(new Vector3f(camLoc.x, character.getCharacterNode().getWorldTranslation().y, camLoc.z));
        
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public Node getStartNode(){
        return startNode;
    }
    
    private static void initAppScreen(SimpleApplication sa){
        AppSettings apps = new AppSettings(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen.width *=0.75;
        screen.height *= 0.75;
        apps.setResolution(screen.width, screen.height);
        sa.setSettings(apps);
        sa.setShowSettings(false);
    }
    
    private void initPhysics(){
        bullet = new BulletAppState();
        stateManager.attach(bullet);
        bullet.setDebugEnabled(true);

    }
    
    private void initSky(){
        sky = SkyFactory.createSky(assetManager, 
        assetManager.loadTexture("Textures/SkyboxN.png"), 
        assetManager.loadTexture("Textures/SkyboxN.png"), 
        assetManager.loadTexture("Textures/SkyboxN.png"), 
        assetManager.loadTexture("Textures/SkyboxN.png"), 
        assetManager.loadTexture("Textures/SkyboxTop.png"), 
        assetManager.loadTexture("Textures/SkyboxBottom.png"));
        rootNode.attachChild(sky);
    }
    
    private void initCharacter(){
        character = new CharacterObject(this);
        flyCam.setEnabled(false);
        tower.getTowerNode().attachChild(character.getCharacterNode());
        tower.getTowerNode().attachChild(character.follow);

        //camNode = new CameraNode("Camera Node",cam);
        //camNode.setControlDir(ControlDirection.CameraToSpatial);
        //oto.getCharacterNode().attachChild(camNode);
        //camNode.setLocalTranslation(0, 0, 10);
        //camNode.lookAt(oto.getCharacterNode().getLocalTranslation(), Vector3f.UNIT_Y);

    }
    
    private void initCamera(){
        //CameraNode cameraNode = new CameraNode("Camera Node", getCamera());
        //cameraTarget = new Node();
        cam.setLocation(new Vector3f(0,0,8));
        chase = new ChaseCamera(cam, character.getCharacterNode(), inputManager);
        chase.setMinDistance(6);
        chase.setMaxDistance(8);
        chase.setUpVector(new Vector3f(0,1,0));
        chase.setMaxVerticalRotation(0);
        chase.setMinVerticalRotation(0);
        float radian = (float)Math.toRadians(90f);
        chase.setDefaultHorizontalRotation(radian);
        chase.setDragToRotate(false);
        chase.setRotationSpeed(0);
        chase.setRotationSensitivity(0);
        chase.setDefaultVerticalRotation(0);
        //camNode = new CameraNode("Camera Node", cam);
        //This mode means that camera copies the movements of the target:
        //camNode.setControlDir(ControlDirection.SpatialToCamera);
        //Attach the camNode to the target:
        //character.follow.attachChild(camNode);
        //Move camNode, e.g. behind and above the target:
        //camNode.setLocalTranslation(new Vector3f(0, 0, 8));
        //camNode.
        //Rotate the camNode to look at the target:
        //camNode.lookAt(character.follow.getLocalTranslation(), Vector3f.UNIT_Y);
        
        //Setting to false for testing purposes
        //getFlyByCamera().setEnabled(true);
        //cameraNode.lookAt(tower.getTowerNode().getWorldTranslation(), Vector3f.UNIT_Y);
        //cameraTarget.attachChild(cameraNode);
        //attachChild(cameraTarget);
    }
}
