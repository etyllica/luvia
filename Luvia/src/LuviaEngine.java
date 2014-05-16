import examples.AugmentedReality;
import examples.Ortographic;
import examples.Perspective;
import examples.tutorial1.Tutorial1;
import br.com.luvia.Application3D;
import br.com.luvia.Luvia;
import br.com.luvia.core.ApplicationGL;


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

		//setMainApplication(new Tutorial1(w, h));
		//setMainApplication(new Perspective(w, h));
		//setMainApplication(new Ortographic(w, h));
		return new AugmentedReality(w, h);
		
	}

}
