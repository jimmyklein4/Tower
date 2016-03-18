/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame;

import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author Expression Chris is undefined on line 12, column 14 in Templates/Classes/Class.java.
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
