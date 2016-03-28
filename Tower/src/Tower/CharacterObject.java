package Tower;

import com.jme3.app.SimpleApplication;
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
    
    private SimpleApplication sa;
    private Node characterNode;
    
    public CharacterObject(SimpleApplication sa){
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
    }
    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            if(name.equals("Left")){
                //TODO: Add movement Left
            }
            if(name.equals("Right")){
                //TODO: Add movement Right
            }
        }
    };
    private void initModel(){
        //TODO: Get a real character model
        //Using oto as a placeholder model
        characterNode = (Node)sa.getAssetManager().loadModel("Models/Sinbad/Sinbad.mesh.xml");
        characterNode.setLocalTranslation(-3.5f,0,4.5f);
        
        Quaternion faceRight = new Quaternion(); 
        faceRight.fromAngleAxis(FastMath.PI/2 , new Vector3f(0,1,0)); 
        characterNode.setLocalRotation(faceRight);
        
        characterNode.scale(0.1f);
        sa.getRootNode().attachChild(characterNode);
    }
    
    private void initPhysics(){
        //TODO: Add physics
    }
}
