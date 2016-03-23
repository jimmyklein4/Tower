package mygame;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * @author Chris
 */
public class TowerObject{
    Main msa;
    boolean rotated = false;
    Box meshTowerBase;
    Geometry geomTowerBase;
    Material matTowerBase; 
    Node tower = new Node();
    //==========================================================================
    public TowerObject(){
        
    }
    //==========================================================================
    public TowerObject(Main msa){
        this.msa = msa;
        init();
    }
    //==========================================================================
    private void initPhysics(){
        RigidBodyControl towerBodyControl = new RigidBodyControl(0.0f);
        tower.addControl(towerBodyControl);
        msa.bullet.getPhysicsSpace().add(tower);
    }
    //==========================================================================
    private void initMesh(){
        meshTowerBase = new Box(3, 10, 3);
    }
    //==========================================================================
    private void initMat(){
        matTowerBase = new Material(msa.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        matTowerBase.setBoolean("UseMaterialColors", true);
        matTowerBase.setColor("Specular", ColorRGBA.Yellow);
        matTowerBase.setColor("Diffuse", ColorRGBA.LightGray);
        matTowerBase.setColor("Ambient", ColorRGBA.Yellow);
        matTowerBase.setFloat("Shininess", 10f);
    }
    //==========================================================================
    private void initGeo(){
        geomTowerBase = new Geometry("TowerBase", meshTowerBase);
        geomTowerBase.setMaterial(matTowerBase);
        tower.attachChild(geomTowerBase);
    }
    //==========================================================================
    private void initKeys(){
        msa.getInputManager().addMapping("RLeft", new KeyTrigger(KeyInput.KEY_Q));
        msa.getInputManager().addMapping("RRight", new KeyTrigger(KeyInput.KEY_E));
        msa.getInputManager().addListener(actionListener, new String[] {"RLeft", "RRight"});
    }
    //==========================================================================
    private void init(){
        initKeys();
        initMesh();
        initMat();
        initGeo();
        initPhysics();
        msa.getRootNode().attachChild(tower);
    }
    //==========================================================================
    private ActionListener actionListener = new ActionListener(){
        public void onAction(String name, boolean isPressed, float tpf) {
            float timer;
            float change = 0.1f;
            if(isPressed){
                if(name.equals("RRight")){
                    timer = 0;
                    while(timer < FastMath.PI/2){
                        tower.rotate(0, change, 0);
                        timer += change;
                    }
                    if(rotated){
                        rotated = false;
                    }
                    else{
                        rotated = true;
                    }
                }
                if(name.equals("RLeft")){
                    timer = 0;
                    while(timer < FastMath.PI/2){
                        tower.rotate(0, -change, 0);
                    }
                    if(rotated){
                        rotated = false;
                    }
                    else{
                        rotated = true;
                    }
                }
            }
        }
    };
}
