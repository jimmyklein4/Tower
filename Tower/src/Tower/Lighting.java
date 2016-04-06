/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tower;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 *
 * @author Chris 
 */
public class Lighting {
    Main msa;
    public Lighting(){
        
    }
    public Lighting(Main msa){
        this.msa = msa;
        //AmbientLight ambient = new AmbientLight();
        //Changing this just for rotation reference 
        DirectionalLight ambient = new DirectionalLight();
        ambient.setDirection((new Vector3f(-2, -5, -10)).normalizeLocal());
        ambient.setColor(ColorRGBA.White);
        this.msa.getRootNode().addLight(ambient);  
    }
}
