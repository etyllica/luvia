package examples.tutorial1;

import java.awt.Color;

import javax.media.opengl.GLAutoDrawable;

import br.com.etyllica.core.event.Action;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.gui.Button;
import br.com.etyllica.gui.label.TextLabel;
import br.com.luvia.core.ApplicationGL;

public class Tutorial1 extends ApplicationGL {

	public Tutorial1(int width, int height) {
		super(width, height);
	}
	
	private Color color = Color.WHITE;
	
	@Override
	public void init(GLAutoDrawable drawable) {
		//Init 3d Stuff		
	}

	@Override
	public void load() {
		
		//Load 2D and 3D stuff
		Button exit = new Button(w/2-200/2, h/2-60/2, 200, 60);
		exit.setLabel(new TextLabel("BUTTON"));
		exit.addAction(GUIEvent.MOUSE_LEFT_BUTTON_DOWN, new Action(this, "changeColor"));
		exit.addAction(GUIEvent.MOUSE_RIGHT_BUTTON_DOWN, new Action(this, "clearColor"));
		
		add(exit);
				
		loading = 100;
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}
		
	public void changeColor(){
		this.color = Color.BLACK;
	}
	
	public void clearColor(){
		this.color = Color.WHITE;
	}

	@Override
	public void draw(Graphic g) {
		g.setColor(color);
		g.fillRect(0, 0, w, h);
	}
	
}
