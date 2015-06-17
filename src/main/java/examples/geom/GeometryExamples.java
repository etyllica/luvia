package examples.geom;

import br.com.luvia.Luvia;
import br.com.luvia.core.context.ApplicationGL;

public class GeometryExamples extends Luvia {

	public GeometryExamples() {
		super(1024,576);
	}

	// Main program
	public static void main(String[] args) {

		GeometryExamples examples = new GeometryExamples();		
		examples.init();
	}

	@Override
	public ApplicationGL startApplication() {

		initialSetup("../../../../");
		return new CubeExample(w, h);
		//return new CylinderExample(w, h);
		//return new PyramidExample(w, h);
		//return new SphereExample(w, h);
	}

}
