package examples.tutorial2;

import java.awt.Color;

import br.com.etyllica.commons.event.Action;
import br.com.etyllica.commons.event.GUIEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.ui.Button;
import br.com.etyllica.ui.label.TextLabel;
import br.com.etyllica.ui.UI;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.graphics.Graphics3D;

public class MixedRenderingExample extends ApplicationGL {

	public MixedRenderingExample(int width, int height) {
		super(width, height);
	}
	
	private Color color = Color.WHITE;
	
	@Override
	public void init(Graphics3D drawable) {
		//Init 3d Stuff		
	}

	@Override
	public void load() {
		
		//Load 2D and 3D stuff
		Button exit = new Button(w/2-200/2, h/2-60/2, 200, 60);
		exit.setLabel(new TextLabel("BUTTON"));
		exit.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOWN, new Action(this, "changeColor"));
		exit.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_DOWN, new Action(this, "clearColor"));
		
		UI.add(exit);
				
		loading = 100;
	}
	
	@Override
	public void display(Graphics3D drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshape(Graphics3D drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}
		
	public void changeColor() {
		this.color = Color.BLACK;
	}
	
	public void clearColor() {
		this.color = Color.WHITE;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, w, h);
	}
	
}
