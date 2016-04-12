package Tower;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.debug.SkeletonDebugger;

/**
 *
 * @author Chris
 * @author Jimmy
 */
public class CharacterObject extends AbstractControl {
    
    private Main msa;
    private Node characterNode = new Node();
    private AnimChannel channel;
    private AnimControl control;
    public Node follow = new Node();
    private Spatial cNode;
    private BetterCharacterControl characterBodyControl;
    private Vector3f camLoc, charLoc;
    private Vector3f walkDirection = new Vector3f(0,0,0);
    
    public CharacterObject(Main sa){
        this.msa = sa;
        initKeys();
        initModel();
        initPhysics();
        sa.getRootNode().attachChild(characterNode);
        characterNode.scale(0.20f);
        characterNode.setName("characterNode");
        
        initAnimation();
    }
    
    public Node getCharacterNode(){
        return characterNode;
    }
    
    private void initKeys(){
        msa.getInputManager().addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        msa.getInputManager().addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        msa.getInputManager().addListener(analogListener, new String[]{"Left", "Right"});
        
        msa.getInputManager().addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        msa.getInputManager().addListener(actionListener, new String[]{"Jump", "Left", "Right"});
    }
    
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            if(name.equals("Left")){
                camLoc = msa.getCamera().getLocation();
                charLoc = characterNode.getWorldTranslation();
                msa.getCamera().setLocation(new Vector3f(charLoc.x, charLoc.y, camLoc.z));
                follow.setLocalTranslation(characterNode.getLocalTranslation());
            }
            if(name.equals("Right")){ 
                camLoc = msa.getCamera().getLocation();
                charLoc = characterNode.getWorldTranslation();
                msa.getCamera().setLocation(new Vector3f(charLoc.x, charLoc.y, camLoc.z));
                follow.setLocalTranslation(characterNode.getLocalTranslation());
            }
        }
    };
    
    private ActionListener actionListener = new ActionListener(){
        public void onAction(String name, boolean isPressed, float tpf) {
            if(name.equals("Jump")){
                if(isPressed)
                    characterBodyControl.jump();
                else{
                    camLoc = msa.getCamera().getLocation();
                    camLoc.setY(characterNode.getLocalTranslation().y);
                    msa.getCamera().setLocation(camLoc);
                    follow.setLocalTranslation(characterNode.getLocalTranslation());
                }
            }
            if(name.equals("Left")){
                if(isPressed){
                    walkDirection = new Vector3f(-1, 0,0).mult(1.5f);
                    characterBodyControl.setWalkDirection(walkDirection);
                    characterBodyControl.setViewDirection(new Vector3f(-1,0,0));
                }
                else{
                    walkDirection.set(0,0,0);
                    characterBodyControl.setWalkDirection(walkDirection);
                }
            }
            if(name.equals("Right")){
                if(isPressed){
                    walkDirection = new Vector3f(1, 0,0).mult(1.5f);
                    characterBodyControl.setWalkDirection(walkDirection);
                    characterBodyControl.setViewDirection(walkDirection);
                }
                else{
                    walkDirection.set(0,0,0);
                    characterBodyControl.setWalkDirection(new Vector3f(0, 0,0));
                }                
            }    
        }
    };
    private void initModel(){
        cNode = (Node)msa.getAssetManager().loadModel("Models/character/char2.j3o");
        characterNode.attachChild(cNode);

//        Quaternion faceRight = new Quaternion(); 
//        faceRight.fromAngleAxis(FastMath.PI/2 , new Vector3f(0,1,0)); 
//        cNode.setLocalRotation(faceRight);
        //To set the camera in the location of the character
        //WARNING: I suspect this is causing the tower to not rotate correctly
        Vector3f camLocation = msa.getCamera().getLocation();
        camLocation.x-=4.5f;
        msa.getCamera().setLocation(camLocation);
    }
    
    private void initPhysics(){
        characterBodyControl = new BetterCharacterControl(0.2f, 1f, 20f);
        
        //characterBodyControl.setApplyPhysicsLocal(true);
        characterNode.addControl(characterBodyControl);
        msa.bullet.getPhysicsSpace().add(characterNode);
        characterBodyControl.warp(new Vector3f(-4.5f, 2.0f, 4.5f));
        
        follow.setLocalTranslation(characterNode.getLocalTranslation());
    }
    
    @Override
    protected void controlUpdate(float tpf) {
    }
    
    private void initAnimation(){
        Node child = (Node)characterNode.getChild(0);
        Node grandChild = (Node)child.getChild(0);
        control = grandChild.getChild(0).getControl(AnimControl.class);
        
        channel = control.createChannel();

        System.out.println(control.toString());
        channel.setAnim("Walk_Blocking");
    }
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
    public BetterCharacterControl getCharacterBodyControl(){
        return characterBodyControl;
    }
}
