package Tower;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author jimmyklein
 */
public class Level2Maze extends Node{
    
    Main msa;
    private Spatial mazeModel;
    private RigidBodyControl mazeBodyControl;
    
    
    public Level2Maze(Main msa){
        this.msa = msa;
        initModel();
        initPhysics();
    }
    
    
    private void initModel(){
        mazeModel = msa.getAssetManager().loadModel("Models/tower_level2/tower_level2.j3o");
        mazeModel.setMaterial(msa.getAssetManager().loadMaterial("Materials/Generated/tower_level2Mat.j3m"));
        mazeModel.scale(5.5f);
        this.attachChild(mazeModel);
    }
    
    private void initPhysics(){
        mazeBodyControl = new RigidBodyControl(0.0f);
        this.addControl(mazeBodyControl);
        mazeBodyControl.setKinematic(true);
        msa.bullet.getPhysicsSpace().add(this);
    }
}
