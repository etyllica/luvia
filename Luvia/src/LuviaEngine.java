import examples.GridPerspective;
import examples.MarkerApplication;
import examples.Ortographic;
import examples.Perspective;
import examples.tutorial1.Tutorial1;
import br.com.luvia.Luvia;
import br.com.luvia.core.context.ApplicationGL;


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

		String path = LuviaEngine.class.getResource("").toString();
		setPath(path);
				
		//return new Tutorial1(w, h);
		//return new Perspective(w, h);
		//return new Ortographic(w, h);
		return new MarkerApplication(w, h);
		//return new GridPerspective(w, h);
	}

}
