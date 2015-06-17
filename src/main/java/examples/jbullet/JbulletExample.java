package examples.jbullet;

import br.com.luvia.Luvia;
import br.com.luvia.core.context.ApplicationGL;
import examples.jbullet.basic.BasicDemo;
import examples.jbullet.bsp.BspDemo;
import examples.jbullet.character.CharacterDemo;
import examples.jbullet.concave.ConcaveDemo;
import examples.jbullet.dynamiccontrol.DynamicControlDemo;
import examples.jbullet.forklift.ForkLiftDemo;
import examples.jbullet.movingconcave.MovingConcaveDemo;
import examples.jbullet.vehicle.VehicleDemo;

public class JbulletExample extends Luvia {

	public JbulletExample() {
		super(800,600);
	}

	// Main program
	public static void main(String[] args) {
		JbulletExample demo = new JbulletExample();
		demo.init();
	}
	
	@Override
	public ApplicationGL startApplication() {
		initialSetup("../../../");
		
		//return new BasicDemo(w, h);
		//return new BspDemo(w, h);
		//return new CharacterDemo(w, h);
		//return new ConcaveDemo(w, h);
		//return new DynamicControlDemo(w, h);
		//return new ForkLiftDemo(w, h);
		//return new GenericJointDemo(w, h);
		//return new MovingConcaveDemo(w, h);
		return new VehicleDemo(w, h);
	}
}
