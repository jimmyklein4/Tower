package Tower;

import com.jme3.bullet.control.BetterCharacterControl;
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
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Chris
 * @author Jimmy
 */
public class TowerObject {

    Main msa;
    boolean rotated = false;
    Level1Tower tower;
    private boolean initialPress;
    private Node frontNode, leftNode, rightNode, backNode;
    private Vector3f previousCollision;
    private  Quaternion totalRot = Quaternion.IDENTITY;
    private RigidBodyControl towerBodyControl;
    
    private Quaternion front, left, right, back;
    //private CameraNode cameraNode;
    //==========================================================================

    public TowerObject() {
    }
    //==========================================================================

    public TowerObject(Main msa) {
        this.msa = msa;
        tower = new Level1Tower(msa);
        init();
        
        
    }

    public Node getTowerNode() {
        return tower;
    }

    private void init() {
        initNodes();
        initPhysics();
        msa.getRootNode().attachChild(tower);
        initKeys();
    }

    private void initPhysics() {
        towerBodyControl = new RigidBodyControl(0.0f);
        tower.addControl(towerBodyControl);
        towerBodyControl.setKinematic(true);
        msa.bullet.getPhysicsSpace().add(tower);
    }
    
    private void initKeys() {
        msa.getInputManager().addMapping("Select", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));


        msa.getInputManager().addListener(actionListener, new String[]{"Select"});
        msa.getInputManager().addListener(analogListener, new String[]{"Select"});
    }

    private void initNodes(){
        
        //Box is just here as a reference point
        Box box = new Box(1,1,1);
        Material boxmat = new Material(msa.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        boxmat.setColor("Diffuse", ColorRGBA.Blue);
        Geometry geobox = new Geometry("box", box);
        geobox.setMaterial(boxmat);
        frontNode = new Node();
        frontNode.setLocalTranslation(tower.getLocalTranslation().x, 
                tower.getLocalTranslation().y,
                tower.getLocalTranslation().z+4);
        
        rightNode = new Node();
        rightNode.setLocalTranslation(tower.getLocalTranslation().x+4, 
                tower.getLocalTranslation().y,
                tower.getLocalTranslation().z);
        
        leftNode = new Node();
        leftNode.setLocalTranslation(tower.getLocalTranslation().x-4, 
                tower.getLocalTranslation().y,
                tower.getLocalTranslation().z);
        
        backNode = new Node();
        backNode.setLocalTranslation(tower.getLocalTranslation().x, 
                tower.getLocalTranslation().y,
                tower.getLocalTranslation().z-4);
        
        leftNode.attachChild(geobox);
        tower.attachChild(frontNode);
        tower.attachChild(rightNode);
        tower.attachChild(leftNode);
        tower.attachChild(backNode); 
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            
            if (isPressed) {
                if (name.equals("Select")) {
                    initialPress = true;
                    Vector3f pos = msa.oto.getCharacterNode().getLocalTranslation();
                    msa.oto.follow.setLocalTranslation(pos.x, pos.y+0.1f, pos.z);
                    System.out.println("Follow posistion: "+ pos.x+ "," + pos.y + "," + pos.z);
                }
            }
            else{

                float leftDistance = leftNode.getWorldTranslation().distance(msa.getStartNode().getWorldTranslation());
                float rightDistance = rightNode.getWorldTranslation().distance(msa.getStartNode().getWorldTranslation());
                float frontDistance = frontNode.getWorldTranslation().distance(msa.getStartNode().getWorldTranslation());
                float backDistance = backNode.getWorldTranslation().distance(msa.getStartNode().getWorldTranslation());
                
                Float distances[] = {leftDistance,rightDistance,frontDistance,backDistance};
                Arrays.sort(distances);

                if(leftDistance == distances[0]){
                    tower.setLocalRotation(new Quaternion(0,0.7f,0,0.7f));
                    totalRot = tower.getLocalRotation();
                    
                    Node cn = (Node)msa.getRootNode().getChild("characterNode");
                    //cn.setRotation(totalRot);
                    
                    System.out.println("Left " + frontDistance);
                }else if(rightDistance == distances[0]){
                    tower.setLocalRotation(new Quaternion(0,0.7f,0,-0.7f));
                    System.out.println("Right " + rightDistance);
                    totalRot = tower.getLocalRotation();
                }else if(frontDistance == distances[0]){
                    tower.setLocalRotation(new Quaternion(0,0,0,1));
                    totalRot = tower.getLocalRotation();
                    System.out.println("Front " + frontDistance);
                }else if(backDistance == distances[0]){
                    tower.setLocalRotation(new Quaternion(0,1,0,0));
                    System.out.println("Back " + backDistance);
                    totalRot = tower.getLocalRotation();
                } else {
                    System.out.println("Error");
                }
                Vector3f pos = msa.oto.follow.getWorldTranslation();
                msa.oto.getCharacterNode().setLocalTranslation(pos.x, pos.y+0.05f, pos.z);
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
                    currentCollision = currentCollision.subtract(tower.getWorldTranslation());
                    if (initialPress) {
                        initialPress = false;
                        previousCollision = currentCollision;
                    } else {
                        Quaternion q = computeRotation(currentCollision);
                        Quaternion q2 = new Quaternion(0, q.getY()*13, 0,q.getW()).normalizeLocal();
                        previousCollision = currentCollision;
                        totalRot = q2.mult(totalRot);
                        tower.setLocalRotation(totalRot);
                        
                        msa.oto.getCharacterBodyControl().warp(msa.oto.follow.getWorldTranslation());
                    }
                }
            }
        }
    };

    private Vector3f getRayCollision() {
        Vector2f click2d = msa.getInputManager().getCursorPosition();
        Vector3f click3d = msa.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();

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
