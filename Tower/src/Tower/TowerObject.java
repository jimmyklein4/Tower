package Tower;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.CameraNode;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * @author Chris
 */
public class TowerObject {

    Main msa;
    boolean rotated = false;
    Box meshTowerBase;
    Geometry geomTowerBase;
    Material matTowerBase;
    Vector2f originalCursor;
    Node tower = new Node();
    private boolean initialPress;
    private Vector3f previousCollision;
    private  Quaternion totalRot = Quaternion.IDENTITY;
    
    private Quaternion front, left, right, back;
    //private CameraNode cameraNode;
    //==========================================================================

    public TowerObject() {
    }
    //==========================================================================

    public TowerObject(Main msa) {
        this.msa = msa;
        init();
        front = new Quaternion();
        left = new Quaternion();
        right = new Quaternion();
        back = new Quaternion();
        
        front.fromAngleAxis(0, new Vector3f(0,1,0));
        left.fromAngleAxis(3*FastMath.PI/2, new Vector3f(0,1,0));
        right.fromAngleAxis(FastMath.PI/2, new Vector3f(0,1,0));
        back.fromAngleAxis(FastMath.PI, new Vector3f(0,1,0));
        
    }

    public Node getTowerNode() {
        return tower;
    }

    private void init() {
        initMesh();
        initMat();
        initGeo();
        initPhysics();
        msa.getRootNode().attachChild(tower);
        initKeys();

    }

    private void initPhysics() {
        RigidBodyControl towerBodyControl = new RigidBodyControl(0.0f);
        tower.addControl(towerBodyControl);
        msa.bullet.getPhysicsSpace().add(tower);
    }
    //==========================================================================

    private void initMesh() {
        meshTowerBase = new Box(3, 10, 3);
    }
    //==========================================================================

    private void initMat() {
        matTowerBase = new Material(msa.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        matTowerBase.setBoolean("UseMaterialColors", true);
        matTowerBase.setColor("Specular", ColorRGBA.Yellow);
        matTowerBase.setColor("Diffuse", ColorRGBA.LightGray);
        matTowerBase.setColor("Ambient", ColorRGBA.Yellow);
        matTowerBase.setFloat("Shininess", 10f);
    }
    //==========================================================================

    private void initGeo() {
        geomTowerBase = new Geometry("TowerBase", meshTowerBase);
        geomTowerBase.setMaterial(matTowerBase);
        tower.attachChild(geomTowerBase);
    }
    //TODO: Change this to click and drag for the tower

    private void initKeys() {
        msa.getInputManager().addMapping("Select", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

        msa.getInputManager().addMapping("RLeft", new KeyTrigger(KeyInput.KEY_Q));
        msa.getInputManager().addMapping("RRight", new KeyTrigger(KeyInput.KEY_E));

        msa.getInputManager().addListener(actionListener, new String[]{"RLeft", "RRight", "Select"});
        msa.getInputManager().addListener(analogListener, new String[]{"Select"});
    }
    //==========================================================================
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            
            if (isPressed) {
                if (name.equals("Select")) {
                    initialPress = true;
                }
            }
            else{
                Vector3f axis = new Vector3f();
                float angle = tower.getWorldRotation().toAngleAxis(axis);
                if(angle <= Math.PI/4 || angle >= 7*Math.PI/4){
                    tower.setLocalRotation(front);
                    System.out.println("Front " + totalRot);
                    totalRot = tower.getLocalRotation();
                }
                if(angle <= 3*Math.PI/4 && angle > Math.PI/4){
                    tower.setLocalRotation(right);
                    System.out.println("Right " + totalRot);
                    totalRot = tower.getLocalRotation();
                }
                if(angle <= 5*Math.PI/4 && angle > 3*Math.PI/4){
                    tower.setLocalRotation(back);
                    System.out.println("Back " + totalRot);
                    totalRot = tower.getLocalRotation();
                }
                if(angle <= 7*Math.PI/4 && angle >= 5*Math.PI/4){
                    tower.setLocalRotation(left);
                    System.out.println("Left " + totalRot);
                    totalRot = tower.getLocalRotation();
                }
            }
        }
    };
    /* TODO: Set this so that you can click and drag to position the tower in 
     * different locations
     */
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("Select")) {
                Vector3f currentCollision = getRayCollision();
                if (currentCollision != null) {
                    currentCollision = currentCollision.subtract(geomTowerBase.getWorldTranslation());
                    if (initialPress) {
                        initialPress = false;
                        previousCollision = currentCollision;
                    } else {
                        Quaternion q = computeRotation(currentCollision);
                        Quaternion q2 = new Quaternion(0, q.getY()*13, 0,q.getW()).normalizeLocal();
                        previousCollision = currentCollision;
                        totalRot = q2.mult(totalRot);
                        tower.setLocalRotation(totalRot);
                        //msa.getCamera().setRotation(totalRot);
                    }
                }
            }
        }
    };

    private Vector3f getRayCollision() {
        Vector3f hitVector = null;
        CollisionResults results = new CollisionResults();
        Vector2f click2d = msa.getInputManager().getCursorPosition();
        Vector3f click3d = msa.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
        /*
        Vector3f dir = msa.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        Ray ray = new Ray(click3d, dir);
        tower.collideWith(ray, results);
        float minDist = Float.MAX_VALUE;
        for (int i = 0; i < results.size(); i++) {
            Vector3f pt = results.getCollision(i).getContactPoint();
            String target = results.getCollision(i).getGeometry().getName();
            //if (target.equals("TowerBase")) {
                float dist = results.getCollision(i).getDistance();
                if (dist < minDist) {
                    hitVector = pt;
  
                    System.out.println("Selection #" + i + ": " + target + " at " + pt + ", " + dist + " WU away.");
                    minDist = dist;
                }
           // }
        }
        */
        return click3d;
    }
    private Quaternion computeRotation(Vector3f currentCollision){
        Vector3f axis = currentCollision.cross(previousCollision);
        float cosine = currentCollision.dot(previousCollision);
        float alpha = -FastMath.atan2(axis.length(), cosine);
        Quaternion q = new Quaternion();
        q.fromAngleAxis(alpha, axis);
        return q;
    }
}
