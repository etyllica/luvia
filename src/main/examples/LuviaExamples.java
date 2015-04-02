package examples;

import br.com.luvia.Luvia;
import br.com.luvia.core.context.ApplicationGL;
import examples.GridPerspective;
import examples.MarkerApplication;
import examples.Ortographic;
import examples.Perspective;
import examples.tutorial1.Tutorial1;

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
	public ApplicationGL startApplication(){

		initialSetup("../../");
						
		//return new Tutorial1(w, h);
		//return new Perspective(w, h);
		//return new Ortographic(w, h);
		//return new MarkerApplication(w, h);
		//return new GridPerspective(w, h);
		return new RadialMarkerApplication(w, h);
	}

}
