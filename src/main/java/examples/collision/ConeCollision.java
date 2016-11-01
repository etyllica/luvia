package examples.collision;

import java.awt.Color;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.collision.Ray;

import examples.simple.StandardExample;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.controller.FlyView;
import br.com.luvia.core.graphics.Graphics3D;
import br.com.luvia.graphics.Cone;
import br.com.luvia.graphics.Cylinder;

public class ConeCollision extends ApplicationGL {

	protected int mx = 0;
	protected int my = 0;

	protected boolean click = false;

	protected FlyView view;

	protected static final int NONE = -1;

	protected boolean drawRay = true;
	protected double tileSize = 1;
	protected int colide = NONE;
	protected int selected = NONE;

	private Cone cone;
	private Cylinder cylinder;

	public ConeCollision(int w, int h) {
		super(w, h);
	}

	@Override
	public void init(Graphics3D drawable) {
		view = new FlyView(0, 3.6f, -10);
		view.getAim().setAngleY(180);

		cone = new Cone(6, 6, 3);
		cylinder = new Cylinder(6, 6, 3);
		cylinder.transform.translate(8, 0, 0);

		GL2 gl = drawable.getGL2(); // get the OpenGL graphics context

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
		gl.glClearDepth(1.0f);      // set clear depth value to farthest
		gl.glEnable(GL.GL_DEPTH_TEST); // enables depth testing
		gl.glDepthFunc(GL.GL_LEQUAL);  // the type of depth test to do
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); // best perspective correction
		gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
	}

	@Override
	public void reshape(Graphics3D drawable, int x, int y, int width, int height) {
		StandardExample.standardScene(drawable, x, y, w, h);
	}

	public void updateMouse(PointerEvent event) {
		mx = event.getX();
		my = event.getY();
	}
	
	@Override
	public void updateKeyboard(KeyEvent event) {
		view.updateKeyboard(event);
	}

	@Override
	public void display(Graphics3D drawable) {
		view.update(0);

		GL2 gl = drawable.getGL().getGL2();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor(1f, 1f, 1f, 1);

		//Transform by Aim
		drawable.aimCamera(view.getAim());

		//Draw Scene
		StandardExample.drawAxis(gl, 100);

		//Draw Grid
		drawable.setColor(Color.BLACK);
		drawable.drawGrid(1, 150, 150);
		

		Ray ray = drawable.getCameraRay(mx, my);

		if (colide >= 0) {
			gl.glColor3d(1.0, 0.0, 0.0);
		} else {
			gl.glColor3d(0.0, 0.0, 1.0);
		}

		if (drawRay) {
			float raySize = 500;
			StandardExample.drawRay(gl, ray, view, raySize);
		}
		
		drawable.setColor(Color.BLUE);
		cone.draw(drawable);
		
		if (Intersector.intersectRayCylinderFast(ray, cylinder)) {
			drawable.setColor(Color.YELLOW);
		} else {
			drawable.setColor(Color.GREEN);
		}
		cylinder.draw(drawable);

		gl.glFlush();
	}

}
