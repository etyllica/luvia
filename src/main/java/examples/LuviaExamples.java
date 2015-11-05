package examples;

import br.com.luvia.Luvia;
import br.com.luvia.core.context.ApplicationGL;
import examples.box.OctreeClipping;
import examples.box.OctreeRender;
import examples.collision.CollisionApplication;
import examples.custom.GridPerspective;
import examples.custom.MarkerApplication;
import examples.custom.Ortographic;
import examples.custom.Perspective;
import examples.custom.SkyboxGridPerspective;
import examples.frustrum.FrustrumRender;
import examples.orthographic.GridMenuApplication;
import examples.tutorial1.Tutorial1;
import examples.tutorial2.Tutorial2;
import examples.tutorial4.BillboardExample;

public class LuviaExamples extends Luvia {

	public LuviaExamples() {
		//super(640,480);
		super(1024,576);
	}

	// Main program
	public static void main(String[] args) {

		LuviaExamples engine = new LuviaExamples();		
		engine.init();
	}
	
	@Override
	public ApplicationGL startApplication() {

		initialSetup("../../../");
		
		//return new Tutorial1(w, h);
		//return new Tutorial2(w, h);
		//return new Tutorial4(w, h);
		//return new Perspective(w, h);
		//return new Ortographic(w, h);
		//return new MarkerApplication(w, h);
		//return new GridPerspective(w, h);
		//return new RadialMarkerApplication(w, h);
		//return new SkyboxGridPerspective(w, h);
		
		//return new OctreeRender(w, h);
		//return new OctreeClipping(w, h);
		//return new FrustrumRender(w, h);
		
		//return new CollisionApplication(w, h);
		return new BillboardExample(w, h);
	}

}
