package Tower;

/**
 * Audio Credit: Ryan Curtis
 * Audio Source: http://www.flashkit.com/soundfx/Ambience/Woop-Ryan_Cur-8800/index.php
 */

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
/**
 *
 * @author Chris
 * @author Jimmy
 */
public class CharacterObject {

    private Main msa;
    private AudioNode jumpSound;
    private Node characterNode = new Node();
    private AnimChannel channel;
    private AnimControl control;
    public Node follow = new Node();
    private Spatial cNode;
    private BetterCharacterControl characterBodyControl;
    private Vector3f camLoc, charLoc;
    private Vector3f walkDirection = new Vector3f(0, 0, 0);
    private Vector3f checkPoint;
    private String faceDirection;
    public CharacterObject(Main sa) {
        this.msa = sa;
        initKeys();
        initModel();
        initPhysics();
        initAudio();
        sa.getRootNode().attachChild(characterNode);
        characterNode.scale(0.20f);
        characterNode.setName("characterNode");

        initAnimation();
    }

    public Node getCharacterNode() {
        return characterNode;
    }

    public Node getCNode() {
        return (Node) cNode;
    }

    public void setFaceDirection(Vector3f direction) {
        cNode.rotate(direction.x, direction.y, direction.z);
    }

    private void initKeys() {
        msa.getInputManager().addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        msa.getInputManager().addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        msa.getInputManager().addListener(analogListener, new String[]{"Left", "Right"});

        msa.getInputManager().addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        msa.getInputManager().addListener(actionListener, new String[]{"Jump", "Left", "Right"});
    }
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("Left")) {
                camLoc = msa.getCamera().getLocation();
                charLoc = characterNode.getWorldTranslation();
                msa.getCamera().setLocation(new Vector3f(charLoc.x, charLoc.y, camLoc.z));
                follow.setLocalTranslation(characterNode.getLocalTranslation());
            }
            if (name.equals("Right")) {
                camLoc = msa.getCamera().getLocation();
                charLoc = characterNode.getWorldTranslation();
                msa.getCamera().setLocation(new Vector3f(charLoc.x, charLoc.y, camLoc.z));
                follow.setLocalTranslation(characterNode.getLocalTranslation());
            }
        }
    };
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Jump")) {
                if (isPressed) {
                    characterBodyControl.jump();
                    jumpSound.playInstance();
                } else {
                    camLoc = msa.getCamera().getLocation();
                    camLoc.setY(characterNode.getLocalTranslation().y);
                    msa.getCamera().setLocation(camLoc);
                    follow.setLocalTranslation(characterNode.getLocalTranslation());
                }
            }
            if (name.equals("Left")) {
                if (isPressed) {
                    if(!faceDirection.equals(name)){
                        cNode.rotate(0, (float)Math.toRadians(180), 0);
                        faceDirection=name;
                    }
                    channel.setAnim("Run");
                    walkDirection = msa.getCustomCamera().getWalkDirection().mult(-1.5f);
                    characterBodyControl.setWalkDirection(walkDirection);
                } else {
                    channel.setAnim("Stand");
                    walkDirection.set(0, 0, 0);
                    characterBodyControl.setWalkDirection(walkDirection);
                }
            }
            if (name.equals("Right")) {
                if (isPressed) {
                    if(!faceDirection.equals(name)){
                        cNode.rotate(0, (float)Math.toRadians(180), 0);
                        faceDirection=name;
                    }
                    channel.setAnim("Run");
                    walkDirection = msa.getCustomCamera().getWalkDirection().mult(1.5f);
                    characterBodyControl.setWalkDirection(walkDirection);

                } else {
                    channel.setAnim("Stand");
                    walkDirection.set(0, 0, 0);
                    characterBodyControl.setWalkDirection(new Vector3f(0, 0, 0));
                }
            }
        }
    };

    private void initModel() {
        cNode = (Node) msa.getAssetManager().loadModel("Models/character/char2.j3o");
        cNode.setMaterial(msa.getAssetManager().loadMaterial("Materials/Generated/char2Mat.j3m"));
        characterNode.attachChild(cNode);

        Quaternion faceRight = new Quaternion();
        faceRight.fromAngleAxis(FastMath.PI / 2, new Vector3f(0, 1, 0));
        cNode.rotate(faceRight);
        Vector3f camLocation = msa.getCamera().getLocation();
        camLocation.x -= 4.5f;
        msa.getCamera().setLocation(camLocation);
        faceDirection = "Right";
    }

    private void initPhysics() {
        characterBodyControl = new BetterCharacterControl(0.2f, 1f, 20f);
        characterNode.addControl(characterBodyControl);
        msa.bullet.getPhysicsSpace().add(characterNode);
        checkPoint = new Vector3f(-4.5f, 2.0f, 5.5f);
        characterBodyControl.warp(checkPoint);

        follow.setLocalTranslation(characterNode.getLocalTranslation());
    }


    //TODO: Add more animations
    private void initAnimation() {
        Node child = (Node) characterNode.getChild(0);
        Node grandChild = (Node) child.getChild(0);
        control = grandChild.getChild(0).getControl(AnimControl.class);
        channel = control.createChannel();
        
        System.out.println(control.toString());
        channel.setAnim("Stand");
   }

    public BetterCharacterControl getCharacterBodyControl() {
        return characterBodyControl;
    }
    
    public void setCheckPoint(){
        checkPoint = characterNode.getLocalTranslation();
    }
    
    private void initAudio(){
        jumpSound = new AudioNode(msa.getAssetManager(), "Sounds/Woop-Ryan_Cur-8800_hifi.wav", false);
        jumpSound.setPositional(false);
        jumpSound.setLooping(false);
        jumpSound.setVolume(1);
        msa.getRootNode().attachChild(jumpSound);
    }

    
}
