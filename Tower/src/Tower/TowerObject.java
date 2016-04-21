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
    //private Level1Tower tower;
    private int level = 0;
    private Level2Maze tower;
    private Level1Tower tower1;
    private RigidBodyControl towerBodyControl;
    
    public TowerObject() {
    }

    /*
     * Level1Tower commented out for testing reasons of level2Maze
     */
    public TowerObject(Main msa, int level) {
        this.msa = msa;
        this.level = level;
        if(level==1){
            tower1 = new Level1Tower(msa);
        }
        else if(level==2){
            tower = new Level2Maze(msa);
        }
        else
            tower1 = new Level1Tower(msa);
        init();
    }

    public Node getTowerNode() {
        if(level==1){
            return tower1;
        }
        else if(level == 2){
            return tower;
        }
        else
            return tower1;
    }

    private void init() {
        initAudio();
        initPhysics();
        if(level==1){
             msa.getRootNode().attachChild(tower1);
        }
        else if(level == 2){
             msa.getRootNode().attachChild(tower);
        }
        else
             msa.getRootNode().attachChild(tower1);
        initKeys();
    }

    private void initPhysics() {
        towerBodyControl = new RigidBodyControl(0.0f);
        
        if(level==1){
            tower1.addControl(towerBodyControl);
            towerBodyControl.setKinematic(true);
            msa.bullet.getPhysicsSpace().add(tower1);
        }
        else if(level == 2){
            tower.addControl(towerBodyControl);
            towerBodyControl.setKinematic(true);
            msa.bullet.getPhysicsSpace().add(tower);
        }
        else{
            tower.addControl(towerBodyControl);
            towerBodyControl.setKinematic(true);
            msa.bullet.getPhysicsSpace().add(tower1);
        }
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
