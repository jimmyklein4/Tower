/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tower;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author Chris 
 */
public class Lighting {
    Main msa;
    private DirectionalLight directionalLight;
    private AmbientLight ambientLight;
    
    public Lighting(){
        
    }
    public Lighting(Main msa){
        this.msa = msa;
        //TODO: Switch to ambient light
        
        //ambientLight = new AmbientLight();
        
        directionalLight = new DirectionalLight();
        directionalLight.setDirection((new Vector3f(-2, -5, -10)).normalizeLocal());
        directionalLight.setColor(ColorRGBA.White);
        //ambientLight.setColor(ColorRGBA.White);
        
        this.msa.getRootNode().addLight(directionalLight);  
    }
    
    public Vector3f getDirection(){
        return directionalLight.getDirection();
    }
    
    public void setDirection(Vector3f direction){
        directionalLight.setDirection(direction);
    }
}
