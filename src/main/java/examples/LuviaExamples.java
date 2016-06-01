package examples;

import br.com.luvia.Luvia;
import br.com.luvia.core.context.ApplicationGL;
import examples.box.OctreeClipping;
import examples.box.OctreeRender;
import examples.collision.CollisionApplication;
import examples.collision.OrientedBoxCollision;
import examples.custom.GridPerspective;
import examples.custom.MarkerApplication;
import examples.custom.Ortographic;
import examples.custom.Perspective;
import examples.custom.SkyboxGridPerspective;
import examples.frustrum.FrustrumRender;
import examples.manipulation.PositionAxis;
import examples.manipulation.RotationAxis;
import examples.mesh.MeshExample;
import examples.orthographic.GridMenuApplication;
import examples.stamp.StampApplication;
import examples.tutorial1.OrthographicDrawingExample;
import examples.tutorial2.MixedRenderingExample;
import examples.tutorial3.SimpleMeshExample;
import examples.tutorial4.BillboardExample;
import examples.tutorial5.RotateAroundExample;
import examples.tutorial6.LightExample;

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
		
		//return new MixedRenderingExample(w, h);
		//return new OrthographicDrawingExample(w, h);
		//return new SimpleMeshExample(w, h);
		//return new BillboardExample(w, h);
		//return new RotateAroundExample(w, h);
		//return new Perspective(w, h);
		//return new Ortographic(w, h);
		//return new MarkerApplication(w, h);
		//return new GridPerspective(w, h);
		//return new OrientedBoxCollision(w, h);
		//return new RadialMarkerApplication(w, h);
		//return new SkyboxGridPerspective(w, h);
		
		//return new OctreeRender(w, h);
		//return new OctreeClipping(w, h);
		//return new FrustrumRender(w, h);
		
		//return new CollisionApplication(w, h);
		//return new MeshExample(w, h);		
		//return new LightExample(w, h);
		//return new StampApplication(w, h);
		
		//return new PositionAxis(w, h);
		return new RotationAxis(w, h);
	}

}
