package Tower;

import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author Chris 
 * @author Jimmy
 */
public class Lighting {
    Main msa;
    private AmbientLight ambientLight;
    
    public Lighting(){
        
    }
    public Lighting(Main msa){
        this.msa = msa;
        
        ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(1.3f));
        this.msa.getRootNode().addLight(ambientLight);
    }
}
