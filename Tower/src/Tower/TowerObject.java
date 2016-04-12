package Tower;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * @author Chris
 * @author Jimmy
 */
public class TowerObject {

    Main msa;
    boolean rotated = false;
    Level1Tower tower;
    private RigidBodyControl towerBodyControl;
    
    public TowerObject() {
    }

    public TowerObject(Main msa) {
        this.msa = msa;
        tower = new Level1Tower(msa);
        init();
    }

    public Node getTowerNode() {
        return tower;
    }

    private void init() {

        initPhysics();
        msa.getRootNode().attachChild(tower);
        initKeys();
    }

    private void initPhysics() {
        towerBodyControl = new RigidBodyControl(0.0f);
        tower.addControl(towerBodyControl);
        towerBodyControl.setKinematic(true);
        msa.bullet.getPhysicsSpace().add(tower);
    } 
    
    private void initKeys() {
        msa.getInputManager().addMapping("CamLeft", new KeyTrigger(KeyInput.KEY_J));
        msa.getInputManager().addMapping("CamRight", new KeyTrigger(KeyInput.KEY_K));

        msa.getInputManager().addListener(actionListener, new String[]{"CamLeft", "CamRight"});
    }
    
    //TODO: Make these numbers flow better.
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            
            /*
             * Changing the light for only when youre facing the back
             * I don't like this but jmonkey forums are down and I can't get
             * ambient light to work right.
             */ 
            
            if (isPressed) {
                if(name.equals("CamLeft")){
                    msa.getCustomCamera().setRotate((float)Math.toRadians(90));
                    if(msa.getCustomCamera().getFaceDirection().equals("Back")){
                        msa.getLighting().setDirection(msa.getLighting().getDirection().mult(-1));
                    } else {
                        msa.getLighting().setDirection(new Vector3f(-2, -5, -10));
                    }
                }
                if(name.equals("CamRight")){
                    msa.getCustomCamera().setRotate((float)Math.toRadians(-90));
                    if(msa.getCustomCamera().getFaceDirection().equals("Back")){
                        msa.getLighting().setDirection(msa.getLighting().getDirection().mult(-1));
                    } else {
                        msa.getLighting().setDirection(new Vector3f(-2, -5, -10));
                    }
                }
            }
        }
    };
}
