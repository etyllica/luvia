package examples.tutorial1;

import java.awt.Color;

import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.layer.ImageLayer;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.graphics.AWTGraphics3D;
import br.com.abby.core.graphics.Graphics3D;

public class OrthographicDrawingExample extends ApplicationGL {

	public OrthographicDrawingExample(int width, int height) {
		super(width, height);
	}
	
	private ImageLayer layer;
	
	@Override
	public void init(Graphics3D graphics) {
		//Init 3d Stuff
	}

	@Override
	public void load() {
		layer = new ImageLayer("active_mark.png");
		
		loading = 100;
	}
	
	@Override
	public void display(Graphics3D graphics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshape(Graphics3D graphics, int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, w, h);
		
		layer.simpleDraw(g);
	}
	
}
