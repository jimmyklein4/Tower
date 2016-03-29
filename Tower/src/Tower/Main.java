package Tower;

import com.jme3.app.DebugKeysAppState;
import com.jme3.scene.Node;
import com.jme3.scene.CameraNode;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
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
    private Node cameraTarget;
    
    public Main(){
        super(new StatsAppState(), new DebugKeysAppState());
    }
    
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
        light = new Lighting(this);
        initCamera();
        
        CharacterObject oto = new CharacterObject(this);
        tower.tower.attachChild(oto.getCharacterNode());
        

        setDisplayStatView(false);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //cam.lookAt(tower.getTowerNode().getWorldTranslation(), Vector3f.UNIT_Y);

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
    
    /*TODO: Currently attaching a cameraTarget to the root node gets rid of the
     * tower and I don't know why
     */ 
    private void initCamera(){
        //CameraNode cameraNode = new CameraNode("Camera Node", getCamera());
        //cameraTarget = new Node();
        
        //Setting to false for testing purposes
        //getFlyByCamera().setEnabled(false);
        //cameraNode.lookAt(tower.getTowerNode().getWorldTranslation(), Vector3f.UNIT_Y);
        //cameraTarget.attachChild(cameraNode);
        //attachChild(cameraTarget);
    }
}
