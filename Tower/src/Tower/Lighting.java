/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tower;

import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;

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
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        this.msa.getRootNode().addLight(ambient);  
    }
}
