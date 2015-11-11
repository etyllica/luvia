package examples.tutorial1;

import java.awt.Color;

import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.layer.ImageLayer;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.video.Graphics3D;

public class OrthographicDrawingExample extends ApplicationGL {

	public OrthographicDrawingExample(int width, int height) {
		super(width, height);
	}
	
	private ImageLayer layer;
	
	@Override
	public void init(Graphics3D drawable) {
		//Init 3d Stuff
	}

	@Override
	public void load() {
		layer = new ImageLayer("active_mark.png");
		
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

	@Override
	public void draw(Graphic g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, w, h);
		
		layer.simpleDraw(g);
	}
	
}
