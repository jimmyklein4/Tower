package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
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
        characterNode = (Node)sa.getAssetManager().loadModel("Models/Oto/Oto.mesh.xml");
        sa.getRootNode().attachChild(characterNode);
    }
    
    private void initPhysics(){
        //TODO: Add physics
    }
}
