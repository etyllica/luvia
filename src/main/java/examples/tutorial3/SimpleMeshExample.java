package examples.tutorial3;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import br.com.abby.core.loader.MeshLoader;
import br.com.abby.core.model.Model;
import br.com.etyllica.awt.SVGColor;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.graphics.AWTGraphics3D;
import br.com.abby.core.graphics.Graphics3D;
import br.com.luvia.graphics.ModelInstance;

public class SimpleMeshExample extends ApplicationGL {
	
	private ModelInstance bunny;
	
	public SimpleMeshExample(int width, int height) {
		super(width, height);
	}
		
	@Override
	public void init(Graphics3D graphics) {
		//Init 3d Stuff
		AWTGraphics3D g = (AWTGraphics3D) graphics;
		GL2 gl = g.getGL2(); // get the OpenGL graphics context

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
		gl.glClearDepth(1.0f);      // set clear depth value to farthest
		gl.glEnable(GL.GL_DEPTH_TEST); // enables depth testing
		gl.glDepthFunc(GL.GL_LEQUAL);  // the type of depth test to do
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); // best perspective correction
		gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
		
		//Load bunny model
		Model bunnyVBO = MeshLoader.getInstance().loadModel("bunny.obj");
		bunny = new ModelInstance(bunnyVBO);
		bunny.setColor(SVGColor.GHOST_WHITE);
		
		loading = 100;
	}

	@Override
	public void load() {
		loading = 50;
	}
	
	@Override
	public void display(Graphics3D graphics) {
		AWTGraphics3D g = (AWTGraphics3D) graphics;
		GL2 gl = g.getGL2();  // get the OpenGL 2 graphics context
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
		gl.glLoadIdentity();  // reset the model-view matrix

		gl.glTranslatef(0.0f, 0.0f, -6.0f); // translate into the screen
		gl.glColor3f(1, 1, 1); //set the triangle color
		gl.glScaled(5, 5, 5);
				
		bunny.draw(g);
	}

	@Override
	public void reshape(Graphics3D graphics, int x, int y, int width, int height) {
		AWTGraphics3D g = (AWTGraphics3D) graphics;
		GL2 gl = g.getGL2(); // get the OpenGL graphics context
		GLU glu = g.getGLU();

		if (height == 0) height = 1;   // prevent divide by zero
		float aspect = (float)width / height;

		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL2.GL_PROJECTION);  // choose projection matrix
		gl.glLoadIdentity();             // reset projection matrix

		glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear, zFar

		// Enable the model-view transform
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
	}
	
}
