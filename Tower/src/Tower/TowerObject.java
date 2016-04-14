package Tower;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
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
        msa.getInputManager().addMapping("CamLeft", new KeyTrigger(KeyInput.KEY_LEFT));
        msa.getInputManager().addMapping("CamRight", new KeyTrigger(KeyInput.KEY_RIGHT));
        msa.getInputManager().addMapping("CamUp", new KeyTrigger(KeyInput.KEY_UP));
        msa.getInputManager().addMapping("CamDown", new KeyTrigger(KeyInput.KEY_DOWN));
        msa.getInputManager().addListener(actionListener, new String[]{"CamLeft", "CamRight", "CamUp", "CamDown"});
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            
            if (isPressed) {
                if(name.equals("CamLeft")){
                    msa.getCustomCamera().setHRotate((float)Math.toRadians(90));
                }
                if(name.equals("CamRight")){
                    msa.getCustomCamera().setHRotate((float)Math.toRadians(-90));
                }
                if(name.equals("CamUp")){
                    msa.getCustomCamera().setVRotate(1);
                }                
                if(name.equals("CamDown")){
                    msa.getCustomCamera().setVRotate(0);
                }
            }
        }
    };
}
