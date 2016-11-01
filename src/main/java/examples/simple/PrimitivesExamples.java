package examples.simple;

import br.com.luvia.Luvia;
import br.com.luvia.core.context.ApplicationGL;

public class PrimitivesExamples extends Luvia {

	public PrimitivesExamples() {
		super(1024,576);
	}

	// Main program
	public static void main(String[] args) {
		PrimitivesExamples engine = new PrimitivesExamples();
		engine.setTitle("Primitives Examples");
		engine.init();
	}
	
	@Override
	public ApplicationGL startApplication() {
		initialSetup("../../../../");
		return new ConeDrawing(w, h);
	}

}
