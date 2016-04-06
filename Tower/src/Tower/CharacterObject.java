package Tower;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Chris
 * @author Jimmy
 */
public class CharacterObject {
    
    private Main sa;
    private Node characterNode = new Node();
    public Node follow = new Node();
    private Spatial cNode;
    private BetterCharacterControl characterBodyControl;
    private Vector3f camLoc;
    
    public CharacterObject(Main sa){
        this.sa = sa;
        initKeys();
        initModel();
        initPhysics();
        sa.getRootNode().attachChild(characterNode);
        characterNode.scale(0.25f);
        characterNode.setName("characterNode");
    }
    
    public Node getCharacterNode(){
        return characterNode;
    }
    
    private void initKeys(){
        sa.getInputManager().addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        sa.getInputManager().addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        sa.getInputManager().addListener(analogListener, new String[]{"Left", "Right"});
        
        sa.getInputManager().addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        sa.getInputManager().addListener(actionListener, new String[]{"Jump"});
    }
    
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            if(name.equals("Left")){
                Vector3f v = characterNode.getLocalTranslation();
                if(!(v.x < -5)){
                    characterBodyControl.warp(new Vector3f(characterNode.getWorldTranslation().x-tpf, 
                            characterNode.getWorldTranslation().y, 
                            characterNode.getWorldTranslation().z));
                    //cNode.move(-tpf, 0, 0);
                    
                    camLoc = sa.getCamera().getLocation();
                    sa.getCamera().setLocation(new Vector3f(camLoc.x-tpf, camLoc.y, camLoc.z));
                    follow.setLocalTranslation(characterNode.getLocalTranslation());
                }
            }
            if(name.equals("Right")){
                Vector3f v = characterNode.getLocalTranslation();
                if(!(v.x < -5)){
                    characterBodyControl.warp(new Vector3f(characterNode.getWorldTranslation().x+tpf, 
                            characterNode.getWorldTranslation().y, 
                            characterNode.getWorldTranslation().z));                   
                    //cNode.move(tpf,0,0);
                    
                    camLoc = sa.getCamera().getLocation();
                    sa.getCamera().setLocation(new Vector3f(camLoc.x+tpf, camLoc.y, camLoc.z));
                    follow.setLocalTranslation(characterNode.getLocalTranslation());
                }
            }
        }
    };
    private ActionListener actionListener = new ActionListener(){
        public void onAction(String name, boolean isPressed, float tpf) {
            if(name.equals("Jump")){
                if(isPressed)
                    characterBodyControl.jump();
                else{
                    camLoc = sa.getCamera().getLocation();
                    camLoc.setY(characterNode.getLocalTranslation().y);
                    sa.getCamera().setLocation(camLoc);
                    follow.setLocalTranslation(characterNode.getLocalTranslation());
                }
            }
        }
    };
    private void initModel(){
        cNode = sa.getAssetManager().loadModel("Models/character/character.j3o");
        characterNode.attachChild(cNode);
//        
        Quaternion faceRight = new Quaternion(); 
        faceRight.fromAngleAxis(FastMath.PI/2 , new Vector3f(0,1,0)); 
        cNode.setLocalRotation(faceRight);
        //To set the camera in the location of the character
        //WARNING: I suspect this is causing the tower to not rotate correctly
        Vector3f camLocation = sa.getCamera().getLocation();
        camLocation.x-=4.5f;
        sa.getCamera().setLocation(camLocation);
    }
    
    private void initPhysics(){
        characterBodyControl = new BetterCharacterControl(0.2f, 1f, 20f);
        
        //characterBodyControl.setApplyPhysicsLocal(true);
        characterNode.addControl(characterBodyControl);
        sa.bullet.getPhysicsSpace().add(characterNode);
        characterBodyControl.warp(new Vector3f(-4.5f, 2.0f, 4.5f));
        
        follow.setLocalTranslation(characterNode.getLocalTranslation());
    }
    public BetterCharacterControl getCharacterBodyControl(){
        return characterBodyControl;
    }
}
