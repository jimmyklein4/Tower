package Tower;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Theo
 */
public class Level1Tower extends Node{
    Main msa;
    Box meshTowerBase, meshLedge, meshLedge2;
    Geometry geomTowerBase;
    Spatial towerModel;
    Geometry ledges[] = new Geometry[20];
    Material matTowerBase, matLedge;
    Vector2f originalCursor;
    LedgeControl lc[] = new LedgeControl[20];
    RigidBodyControl ledgeBodyControl[] = new RigidBodyControl[20];
    
    public Level1Tower(Main msa){
        this.msa = msa;
        init();
    }
    
    private void init(){
        initMesh();
        initMat();
        //initGeo();
        initModel();
        initPhysics();
    }
    private void initMesh() {
        meshTowerBase = new Box(4, 10, 4);
        meshLedge = new Box(0.25f,0.1f,1f);
        meshLedge2 = new Box(1f, 0.1f, 0.25f);
    }
    //==========================================================================

    private void initMat() {
        matTowerBase = new Material(msa.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        matTowerBase.setBoolean("UseMaterialColors", true);
        matTowerBase.setColor("Specular", ColorRGBA.Yellow);
        matTowerBase.setColor("Diffuse", ColorRGBA.Yellow);
        matTowerBase.setColor("Ambient", ColorRGBA.Yellow);
        matTowerBase.setFloat("Shininess", 10f);
        
        matLedge = new Material(msa.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        matLedge.setBoolean("UseMaterialColors", true);
        matLedge.setColor("Specular", ColorRGBA.Black);
        matLedge.setColor("Diffuse", ColorRGBA.Gray);
        matLedge.setColor("Ambient", ColorRGBA.Black);
    }
    //==========================================================================

    private void initGeo() {
        geomTowerBase = new Geometry("TowerBase", meshTowerBase);
        geomTowerBase.setMaterial(matTowerBase);
        geomTowerBase.setLocalTranslation(0,8f,0);
        this.attachChild(geomTowerBase);
        
        ledges = new Geometry[100];
        for(int i = 0; i < 8; i++){
            ledges[i] = new Geometry("Ledge" + i, meshLedge);
            ledges[i].setMaterial(matLedge);
            ledges[i].setLocalTranslation(-4+((i+1)*0.5f), -0.60f , 5f);
            this.attachChild(ledges[i]);
        }
        for(int i =8; i < 16; i++){
            ledges[i] = new Geometry("Ledge" + i, meshLedge);
            ledges[i].setMaterial(matLedge);
            ledges[i].setLocalTranslation(-4+((i+1)*0.5f), -0.60f+ ((i-8)*0.5f), 5f);
            this.attachChild(ledges[i]);
        }
        ledges[16] = new Geometry("Ledge16", meshLedge);
        ledges[16].setMaterial(matLedge);
        ledges[16].setLocalTranslation(4.5f, 2.9f, 5f);
        ledges[17] = new Geometry("Ledge17", meshLedge);
        ledges[17].setMaterial(matLedge);
        ledges[17].setLocalTranslation(5.0f,2.9f,5f);
        this.attachChild(ledges[16]);
        this.attachChild(ledges[17]);
        
        for(int i = 18; i < 27; i++){
            ledges[i] = new Geometry("Ledge"+i, meshLedge2);
            ledges[i].setMaterial(matLedge);
            ledges[i].setLocalTranslation(5f, 2.9f, 4f - ((i-18)*1f));
            this.attachChild(ledges[i]);
        }
        ledges[27] = new Geometry("Ledge27", meshLedge2);
        ledges[27].setMaterial(matLedge);
        ledges[27].setLocalTranslation(5f, 2.9f, -4.5f);
        this.attachChild(ledges[27]);
        ledges[28] = new Geometry("Ledge28", meshLedge2);
        ledges[28].setMaterial(matLedge);
        ledges[28].setLocalTranslation(5f, 2.9f, -5f);
        this.attachChild(ledges[28]);
        
    }
    
    private void initModel(){
        towerModel = msa.getAssetManager().loadModel("Models/towerExterior/towerExterior.j3o");
        towerModel.setMaterial(msa.getAssetManager().loadMaterial("Materials/Generated/towerExteriorMat.j3m"));
        towerModel.scale(3);
        towerModel.move(new Vector3f(0,-2,0));
        this.attachChild(towerModel);
        
        ledges[0] = new Geometry("Ledge0", meshLedge);
        ledges[0].setMaterial(matLedge);
        this.attachChild(ledges[0]);  
               
        ledges[1] = new Geometry("Ledge1", meshLedge);
        ledges[1].setMaterial(matLedge);
        this.attachChild(ledges[1]);
        
        for(int i = 2; i< 10; i++){
            ledges[i] = new Geometry("Ledge"+i, meshLedge2);
            ledges[i].setMaterial(matLedge);
            this.attachChild(ledges[i]);
        }
    }
    
    private void initPhysics(){
        RigidBodyControl towerBodyControl = new RigidBodyControl(0.0f);
        this.addControl(towerBodyControl);
        towerBodyControl.setKinematic(true);
        msa.bullet.getPhysicsSpace().add(this);
        
        ledges[0].setLocalTranslation(-2.1f,6.5f,-5);
        ledgeBodyControl[0] = new RigidBodyControl(0.0f);
        ledges[0].addControl(ledgeBodyControl[0]);
        ledgeBodyControl[0].setKinematic(true);
        msa.bullet.getPhysicsSpace().add(ledges[0]);
        //add control
        lc[0] = new LedgeControl(ledges[0],"x", ledges[0].getLocalTranslation());
        ledges[0].addControl(lc[0]);
        
        ledges[1].setLocalTranslation(-1.2f,5f,-5);
        ledgeBodyControl[1] = new RigidBodyControl(0.0f);
        ledges[1].addControl(ledgeBodyControl[1]);
        ledgeBodyControl[1].setKinematic(true);
        msa.bullet.getPhysicsSpace().add(ledges[1]);
        //add control
        lc[1] = new LedgeControl(ledges[1],"y", ledges[1].getLocalTranslation());
        ledges[1].addControl(lc[1]);
        
        for(int i = 2; i < 10; i++){
            ledges[i].setLocalTranslation(-5f, 5f+(1f*i), -5f + ((i)*1f));
            ledgeBodyControl[i] = new RigidBodyControl(0.0f);
            ledges[i].addControl(ledgeBodyControl[i]);
            ledgeBodyControl[i].setKinematic(true);
            msa.bullet.getPhysicsSpace().add(ledges[i]);
            
            lc[i] = new LedgeControl(ledges[i],"z", ledges[i].getLocalTranslation());
            ledges[i].addControl(lc[i]);
        }
        
    }
    
    class LedgeControl extends AbstractControl{
        float time =0;
        boolean dir = true;
        String axis = "x";
        Vector3f st;
        Geometry ledge;
        
        public LedgeControl(Geometry ledge, String axis, Vector3f stPos){
            this.axis = axis;
            st = stPos.clone();
            this.ledge = ledge;
        }
        @Override
        protected void controlUpdate(float tpf){
            Vector3f ledgePos = ledge.getLocalTranslation();
            if((ledgePos.x<=(st.x-2)||ledgePos.y<=(st.y-2))||(ledgePos.x>=(st.x+1)||ledgePos.y>=(st.y+1))||ledgePos.z<=(st.z-1)||ledgePos.z>=(st.z+2)){
                dir = !dir;
            }
            if(axis=="z"&&(ledgePos.z<=-5||ledgePos.z>=5)){
                dir = !dir;
            }
            if(axis == "x"){
                if(dir){
                    ledge.setLocalTranslation(ledgePos.x+tpf, ledgePos.y, ledgePos.z);
                }
                if(!dir){
                    ledge.setLocalTranslation(ledgePos.x-tpf, ledgePos.y, ledgePos.z);
                }
            }
            else if (axis == "y"){
                if(dir){
                    ledge.setLocalTranslation(ledgePos.x, ledgePos.y-tpf, ledgePos.z);
                }
                if(!dir){
                    ledge.setLocalTranslation(ledgePos.x, ledgePos.y+tpf, ledgePos.z);
                }
            }
            else{
                if(dir){
                    ledge.setLocalTranslation(ledgePos.x, ledgePos.y, ledgePos.z+ tpf);
                }
                if(!dir){
                    ledge.setLocalTranslation(ledgePos.x, ledgePos.y, ledgePos.z- tpf);
                }
            }
        }
        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
        }
        
    }

}
