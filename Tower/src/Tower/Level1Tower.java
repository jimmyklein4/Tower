/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tower;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Theo
 */
public class Level1Tower extends Node{
    Main msa;
    Box meshTowerBase, meshLedge, meshLedge2;
    Geometry geomTowerBase;
    Geometry ledges[];
    Material matTowerBase, matLedge;
    Vector2f originalCursor;
    
    public Level1Tower(Main msa){
        this.msa = msa;
        init();
    }
    
    private void init(){
        initMesh();
        initMat();
        initGeo();
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
    private void initPhysics(){
        RigidBodyControl towerBodyControl = new RigidBodyControl(0.0f);
        this.addControl(towerBodyControl);
        towerBodyControl.setKinematic(true);
        msa.bullet.getPhysicsSpace().add(this);
    }

}
