package Tower;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Chris
 * @author Jimmy
 */
public class CharacterObject {
    
    private Main sa;
    private Node characterNode;
    
    public CharacterObject(Main sa){
        this.sa = sa;
        initKeys();
        initModel();
        initPhysics();
    }
    
    public Node getCharacterNode(){
        return characterNode;
    }
    
    private void initKeys(){
        sa.getInputManager().addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        sa.getInputManager().addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        sa.getInputManager().addListener(analogListener, new String[]{"Left", "Right"});
        
        sa.getInputManager().addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
    }
    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            if(name.equals("Left")){
                Vector3f v = characterNode.getLocalTranslation();
                if(!(v.x < -5))
                    characterNode.setLocalTranslation(v.x - value*5, v.y, v.z);
            }
            if(name.equals("Right")){
                Vector3f v = characterNode.getLocalTranslation();
                if(!(v.x < -5))
                    characterNode.setLocalTranslation(v.x + value*5, v.y, v.z);
            }
        }
    };
    private void initModel(){
        //TODO: Get a real character model
        //Using oto as a placeholder model
        characterNode = (Node)sa.getAssetManager().loadModel("Models/Sinbad/Sinbad.mesh.xml");
        //characterNode.setLocalTranslation(-3.5f,1.0f,4.5f);
//        
        Quaternion faceRight = new Quaternion(); 
        faceRight.fromAngleAxis(FastMath.PI/2 , new Vector3f(0,1,0)); 
        characterNode.setLocalRotation(faceRight);
        
        characterNode.setLocalScale(0.1f);
        sa.getRootNode().attachChild(characterNode);
    }
    
    private void initPhysics(){
        BetterCharacterControl characterBodyControl = new BetterCharacterControl(0.5f, 6.0f, 1.0f);
        characterNode.addControl(characterBodyControl);
        sa.bullet.getPhysicsSpace().add(characterNode);
        characterBodyControl.warp(new Vector3f(-3.5f, 2.0f, 4.5f));
    }
}
