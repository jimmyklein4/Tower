package Tower;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.ColorRGBA;
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
    
    BulletAppState bullet; 
    TowerObject tower;
    Lighting light;
    Spatial sky;
    
    public static void main(String[] args) {
        Main app = new Main();
        initAppScreen(app);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        initPhysics();
        initSky();
        viewPort.setBackgroundColor(ColorRGBA.White);
        tower = new TowerObject(this);
        light = new Lighting(this);
        setDisplayStatView(false);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
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
}
