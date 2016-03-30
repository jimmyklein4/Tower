package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 * test
 * @author Chris
 */
public class Main extends SimpleApplication {
    
    Material eyeMat, irisMat, bodyMat, footMat, handMat, headMat;
    Box head, body, antanae, iris, foot;
    Sphere eye, hand, antanaeTop;
    Geometry geomHead, geomBody, geomLAnt, geomRAnt, geomIris, geomRFoot,
             geomLFoot, geomEye, geomRHand, geomLHand, geomAntTopR, geomAntTopL;
    Node headNode, eyeNode;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.White);
        
        initLightandShadow();
        initMaterials();
        initMesh();
        initGeometry();
        placeOnBoard();
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    private void initLightandShadow(){
       DirectionalLight ambient = new DirectionalLight();
        ambient.setDirection((new Vector3f(0, 0, -10)).normalizeLocal());
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
    }
    
    private void initMaterials(){
        eyeMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        irisMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        bodyMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        footMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        handMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        headMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");

        eyeMat.setBoolean("UseMaterialColors", true);
        irisMat.setBoolean("UseMaterialColors", true);
        bodyMat.setBoolean("UseMaterialColors", true);
        footMat.setBoolean("UseMaterialColors", true);
        handMat.setBoolean("UseMaterialColors", true);
        headMat.setBoolean("UseMaterialColors", true);
        
        eyeMat.setColor("Specular", ColorRGBA.White);
        eyeMat.setColor("Diffuse", ColorRGBA.White);
        eyeMat.setColor("Ambient", ColorRGBA.White);
        eyeMat.setFloat("Shininess", 10f);
        
        irisMat.setColor("Specular", ColorRGBA.Black);
        irisMat.setColor("Diffuse", ColorRGBA.Black);
        irisMat.setColor("Ambient", ColorRGBA.Black);
        irisMat.setFloat("Shininess", 10f);
        
        bodyMat.setColor("Specular", ColorRGBA.Red);
        bodyMat.setColor("Diffuse", ColorRGBA.Red);
        bodyMat.setColor("Ambient", ColorRGBA.Red);
        bodyMat.setFloat("Shininess", 10f);
        
        footMat.setColor("Specular", ColorRGBA.LightGray);
        footMat.setColor("Diffuse", ColorRGBA.LightGray);
        footMat.setColor("Ambient", ColorRGBA.LightGray);
        footMat.setFloat("Shininess", 10f);
        
        handMat.setColor("Specular", ColorRGBA.LightGray);
        handMat.setColor("Diffuse", ColorRGBA.LightGray);
        handMat.setColor("Ambient", ColorRGBA.LightGray);
        handMat.setFloat("Shininess", 10f);
        
        headMat.setColor("Specular", ColorRGBA.DarkGray);
        headMat.setColor("Diffuse", ColorRGBA.DarkGray);
        headMat.setColor("Ambient", ColorRGBA.DarkGray);
        headMat.setFloat("Shininess", 10f);
    }
    
    private void initMesh(){
        head = new Box(1, 1, 1); 
        body = new Box(0.5f, 1, 0.5f);
        antanae = new Box(0.1f, 0.3f, 0.1f);
        iris = new Box(0.1f, 0.1f, 0.1f);
        foot = new Box(0.2f, 0.2f, 0.2f);
        
        eye = new Sphere(15, 15, 0.5f);
        hand = new Sphere(15, 15, 0.3f);
        antanaeTop = new Sphere(15, 15, 0.1f);
    }
    
    private void initGeometry(){
        geomHead = new Geometry("Head", head);
        geomBody = new Geometry("Body", body);
        geomLAnt = new Geometry("LAnt", antanae);
        geomRAnt = new Geometry("RAnt", antanae);
        geomIris = new Geometry("Iris", iris);
        geomRFoot = new Geometry("RFoot", foot);
        geomLFoot = new Geometry("LFoot", foot);
        geomEye = new Geometry("Eye", eye);
        geomRHand = new Geometry("RHand", hand);
        geomLHand = new Geometry("LHand", hand);
        geomAntTopR = new Geometry("LTop", antanaeTop);
        geomAntTopL = new Geometry("RTop", antanaeTop);
        
        geomHead.setMaterial(headMat);
        geomBody.setMaterial(bodyMat);
        geomLAnt.setMaterial(headMat);
        geomRAnt.setMaterial(headMat);
        geomIris.setMaterial(irisMat);
        geomRFoot.setMaterial(footMat);
        geomLFoot.setMaterial(footMat);
        geomEye.setMaterial(eyeMat);
        geomRHand.setMaterial(handMat);
        geomLHand.setMaterial(handMat);
        geomAntTopL.setMaterial(headMat);
        geomAntTopR.setMaterial(headMat);
    }
    
    private void placeOnBoard(){
        headNode = new Node();
        eyeNode = new Node();
        
        eyeNode.setLocalTranslation(0, 0, .75f);
        eyeNode.attachChild(geomEye);
        eyeNode.attachChild(geomIris);
        eyeNode.rotate(0, .25f, 0);
        geomIris.setLocalTranslation(0, 0, 0.4f);
        headNode.attachChild(eyeNode);
        headNode.attachChild(geomHead);
        
        rootNode.attachChild(headNode);
    }
}
