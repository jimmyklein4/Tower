/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tower;

import com.jme3.input.ChaseCamera;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 *
 * @author Chris 
 */
public class CustomCamera {

    private Main msa;
    private Camera camera; 
    private ChaseCamera chase;
    private CharacterObject character;
    private float radian;
    
    public CustomCamera(Main msa, CharacterObject character){
        this.msa = msa;
        this.camera = msa.getCamera();
        this.character = character;
        
        camera.setLocation(new Vector3f(0,0,8));
        chase = new ChaseCamera(camera, character.getCharacterNode(), msa.getInputManager());
        chase.setMinDistance(6);
        chase.setMaxDistance(8);
        chase.setUpVector(new Vector3f(0,1,0));
        chase.setMaxVerticalRotation(0);
        chase.setMinVerticalRotation(0);
        radian = (float)Math.toRadians(90f);
        chase.setDefaultHorizontalRotation(radian);
        chase.setDragToRotate(false);
        chase.setRotationSpeed(0);
        chase.setRotationSensitivity(0);
        chase.setDefaultVerticalRotation(0);
    }
    
    public ChaseCamera getChaseCamera(){
        return chase;
    }
    
    public float getRotate(){
        return radian;
    }
    
    public String getFaceDirection(){
        if(radian==Math.toRadians(90)){
            return "Front";
        } else if(radian==Math.toRadians(180)){
            return "Left";
        } else if(radian==Math.toRadians(0)){
            return "Right";
        } else if(radian==Math.toRadians(-90)){
            return "Back";
        }
        return null;
    }
    
    public Vector3f getWalkDirection(){
        if(radian==(float)Math.toRadians(90)){
            return new Vector3f(1, 0, 0);
        } else if(radian==(float)Math.toRadians(180)){
            return new Vector3f(0,0,1);
        } else if(radian==(float)Math.toRadians(0)){
            return new Vector3f(0,0,-1);
        } else if(radian==(float)Math.toRadians(-90)){
            return new Vector3f(-1,0,0);
        }
        return null;
    }
    
    public void setRotate(float rotate){
        radian = radian+rotate;

        if(Math.abs(radian)>=Math.PI*3/2){
            radian = (float)Math.toRadians(90);
            character.setFaceDirection(new Vector3f(0,radian,0));

        } else {
            character.setFaceDirection(new Vector3f(0,-rotate,0));

        }
        chase.setDefaultHorizontalRotation(radian);
        System.out.println(radian);

    }
    
}
