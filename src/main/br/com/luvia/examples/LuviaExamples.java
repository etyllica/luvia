package br.com.luvia.examples;

import br.com.luvia.Luvia;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.examples.GridPerspective;
import br.com.luvia.examples.MarkerApplication;
import br.com.luvia.examples.Ortographic;
import br.com.luvia.examples.Perspective;
import br.com.luvia.examples.tutorial1.Tutorial1;

public class LuviaEngine extends Luvia {

	public LuviaEngine() {
		//super(640,480);
		super(1024,576);
	}

	// Main program
	public static void main(String[] args) {

		LuviaEngine engine = new LuviaEngine();
		
		engine.init();
	}
	
	@Override
	public ApplicationGL startApplication(){

		initialSetup("../../");
						
		//return new Tutorial1(w, h);
		//return new Perspective(w, h);
		return new Ortographic(w, h);
		//return new MarkerApplication(w, h);
		//return new GridPerspective(w, h);
	}

}
