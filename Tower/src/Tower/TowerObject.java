package Tower;

/**
 * Audio Credit: Martin Brizuela
 * Audio Source: http://www.flashkit.com/soundfx/Ambience/FSWISH-Martin_B-7856/index.php
 */

import com.jme3.audio.AudioNode;
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
    AudioNode turnSound;
    boolean rotated = false;
    Level1Tower tower;
    private RigidBodyControl towerBodyControl;
    
    public TowerObject() {
    }

    public TowerObject(Main msa, int level) {
        this.msa = msa;
        if(level==1)
            tower = new Level1Tower(msa);
        init();
    }

    public Node getTowerNode() {
        return tower;
    }

    private void init() {
        initAudio();
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
    
    private void initAudio(){
        turnSound = new AudioNode(msa.getAssetManager(), "Sounds/FSWISH-Martin_B-7856_hifi.wav", false);
        turnSound.setPositional(false);
        turnSound.setVolume(1);
        turnSound.setLooping(false);
        
        msa.getRootNode().attachChild(turnSound);
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
                    turnSound.playInstance();
                }
                if(name.equals("CamRight")){
                    msa.getCustomCamera().setHRotate((float)Math.toRadians(-90));
                    turnSound.playInstance();
                }
                if(name.equals("CamUp")){
                    msa.getCustomCamera().setVRotate(1);
                    turnSound.playInstance();
                }                
                if(name.equals("CamDown")){
                    msa.getCustomCamera().setVRotate(0);
                    turnSound.playInstance();
                }
            }
        }
    };
}
