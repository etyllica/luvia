package examples.animation;

import br.com.luvia.Luvia;
import br.com.luvia.core.context.ApplicationGL;

public class AnimationExamples extends Luvia {

	public AnimationExamples() {
		super(1024,576);
	}

	// Main program
	public static void main(String[] args) {
		AnimationExamples engine = new AnimationExamples();
		engine.setTitle("Animation Example");
		engine.init();
	}
	
	@Override
	public ApplicationGL startApplication() {

		initialSetup("../");
		
		return new AnimationApplication(w, h);
	}

}
