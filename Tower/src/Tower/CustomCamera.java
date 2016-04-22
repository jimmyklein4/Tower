package Tower;

import com.jme3.input.ChaseCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 * @author Jimmy 
 */
public class CustomCamera {

    private final int STATE_CAM_FLAT = 0;
    private final int STATE_CAM_UP = 1;
    
    private Main msa;
    private Camera camera; 
    private ChaseCamera chase;
    private CharacterObject character;
    private float radian;
    private boolean isCamFlat = true;

    
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
        if(radian==(float)Math.toRadians(90)){
            return "Front";
        } else if(radian==(float)Math.toRadians(180)){
            return "Left";
        } else if(radian==(float)Math.toRadians(0)){
            return "Right";
        } else if(radian==(float)Math.toRadians(-90)){
            return "Back";
        }
        return null;
    }
    
    public Vector3f getWalkDirection(){
        if(radian==(float)Math.toRadians(90)||radian==(float)Math.toRadians(-270)){
            return new Vector3f(1, 0, 0);
        } else if(radian==(float)Math.toRadians(180)||radian==(float)Math.toRadians(-180)){
            return new Vector3f(0,0,1);
        } else if(radian==(float)Math.toRadians(0)||radian==(float)Math.toRadians(-360)||radian==(float)Math.toRadians(360)){
            return new Vector3f(0,0,-1);
        } else if(radian==(float)Math.toRadians(-90)||radian==(float)Math.toRadians(270)){
            return new Vector3f(-1,0,0);
        }
        System.out.print(Math.toDegrees(radian));
        return null;
    }
    
    public void setHRotate(float rotate){
        if(Math.abs(Math.toDegrees(radian))<271){
            radian = radian+rotate;
        }
        else{
            radian = 0+rotate;
        }
        System.out.println("radians = "+ Math.toDegrees(radian));
//
//        if(Math.abs(radian)>=Math.PI*3/2){
//            radian = (float)Math.toRadians(90);
//            character.setFaceDirection(new Vector3f(0,radian,0));
//        } else {
            character.setFaceDirection(new Vector3f(0,-rotate,0));

//        }
        chase.setDefaultHorizontalRotation(radian);
        character.setCheckPoint();
    }
    
    public void setVRotate(int state){
        if(state==STATE_CAM_UP && isCamFlat){
            /*If you set this to 90 degrees, the camera will not rotate along 
            * the horizontal
            */
            chase.setDefaultVerticalRotation((float)Math.toRadians(89));
            isCamFlat = false;
        } else if(state==STATE_CAM_FLAT && !isCamFlat){
            chase.setDefaultVerticalRotation((float)Math.toRadians(0));            
            chase.setDefaultHorizontalRotation(radian);
            isCamFlat = true;
        }
    }
    
}
