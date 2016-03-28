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
    private Node characterNode = new Node();
    private Node cNode;
    private BetterCharacterControl characterBodyControl;
    public CharacterObject(Main sa){
        this.sa = sa;
        initKeys();
        initModel();
        initPhysics();
        sa.getRootNode().attachChild(characterNode);
        characterNode.scale(0.1f);
        characterNode.move(0, 1, 0);

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
                if(!(v.x < -5)){
                    characterBodyControl.warp(new Vector3f(characterNode.getLocalTranslation().x-tpf, 
                            characterNode.getLocalTranslation().y, 
                            characterNode.getLocalTranslation().z));
                    //cNode.move(-tpf, 0, 0);
                }
            }
            if(name.equals("Right")){
                Vector3f v = characterNode.getLocalTranslation();
                if(!(v.x < -5)){
                    characterBodyControl.warp(new Vector3f(characterNode.getLocalTranslation().x+tpf, 
                            characterNode.getLocalTranslation().y, 
                            characterNode.getLocalTranslation().z));                    //cNode.move(tpf,0,0);
                }
            }
        }
    };
    private void initModel(){
        //TODO: Get a real character model
        //Using oto as a placeholder model
        cNode =  (Node)sa.getAssetManager().loadModel("Models/Sinbad/Sinbad.mesh.xml");
        characterNode.attachChild(cNode);
        cNode.setLocalTranslation(0, 5, 0);
        //characterNode.setLocalTranslation(0,2.0f,4.5f);
//        
        Quaternion faceRight = new Quaternion(); 
        faceRight.fromAngleAxis(FastMath.PI/2 , new Vector3f(0,1,0)); 
        cNode.setLocalRotation(faceRight);
        
    }
    
    private void initPhysics(){
        characterBodyControl = new BetterCharacterControl(0.2f, 1f, 20f);
        
        characterBodyControl.setApplyPhysicsLocal(true);
        characterNode.addControl(characterBodyControl);
        sa.bullet.getPhysicsSpace().add(characterNode);
        characterBodyControl.warp(new Vector3f(0, 2.0f, 4.5f));
    }
}
