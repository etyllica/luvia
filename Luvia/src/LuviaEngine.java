import examples.Ortographic;
import examples.Perspective;
import examples.Tutorial1;
import br.com.luvia.Luvia;


public class LuviaEngine extends Luvia{

	public LuviaEngine(int w, int h) {
		super(w, h);
	}

	// Main program
	public static void main(String[] args) {

		//LuviaEngine engine = new LuviaEngine(640,480);
		LuviaEngine engine = new LuviaEngine(1024,576);
		engine.init();

	}
	
	@Override
	public void startGame(){

		setMainApplication(new Tutorial1(w, h));
		//setMainApplication(new Perspective(w, h));
		//setMainApplication(new Ortographic(w, h));
		
	}

}
