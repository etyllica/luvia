package examples;

import java.awt.Color;

import javax.media.opengl.GLAutoDrawable;

import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyboardEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Grafico;
import br.com.etyllica.gui.Button;
import br.com.etyllica.gui.label.TextLabel;
import br.com.luvia.core.ApplicationGL;

public class Tutorial1 extends ApplicationGL {

	public Tutorial1(int width, int height) {
		super(width, height);
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		//Init 3d Stuff		
	}

	@Override
	public void load() {
		
		//Load 2D and 3D stuff
		Button exit = new Button(w/2-200/2, h/2-60/2, 200, 60);
		exit.setLabel(new TextLabel("BUTTON"));
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
	public GUIEvent updateKeyboard(KeyboardEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public void timeUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Grafico g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);		
	}
	
}
