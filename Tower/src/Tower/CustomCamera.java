/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tower;

import com.jme3.input.ChaseCamera;
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
    
    public void setRotate(float rotate){
        radian = rotate;
        if(radian==360){
            radian = 0;
        }
        chase.setDefaultHorizontalRotation(radian);
    }
    
}
