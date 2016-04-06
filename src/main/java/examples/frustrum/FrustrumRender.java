package examples.frustrum;

import java.awt.Color;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import br.com.abby.core.loader.MeshLoader;
import br.com.abby.core.vbo.Face;
import br.com.abby.core.vbo.VBO;
import br.com.abby.linear.Frustrum;
import br.com.etyllica.awt.SVGColor;
import br.com.etyllica.core.collision.CollisionStatus;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.linear.Point3D;
import br.com.etyllica.storage.octree.Octree;
import br.com.etyllica.storage.octree.OctreeNode;
import br.com.etyllica.storage.octree.VolumeOctree;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.video.Graphics3D;
import br.com.luvia.linear.Mesh;

import com.badlogic.gdx.math.Vector3;

public class FrustrumRender extends ApplicationGL {
	
	private Octree<Face> octree;
	
	private VBO bunnyVBO;
	
	private Mesh bunny;
	
	private double angleY = 0;
	
	private boolean rotate = true;
	
	private Frustrum frustrum;
	
	public FrustrumRender(int width, int height) {
		super(width, height);
	}
		
	@Override
	public void init(Graphics3D g) {
		GL2 gl = g.getGL2(); // get the OpenGL graphics context

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
		gl.glClearDepth(1.0f);      // set clear depth value to farthest
		gl.glEnable(GL.GL_DEPTH_TEST); // enables depth testing
		gl.glDepthFunc(GL.GL_LEQUAL);  // the type of depth test to do
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); // best perspective correction
		gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
		
		frustrum = new Frustrum();
				
		//Load bunny model
		bunnyVBO = MeshLoader.getInstance().loadModel("bunny.obj");
		bunny = new Mesh(bunnyVBO);
		bunny.setColor(SVGColor.GHOST_WHITE);
		
		octree = new VolumeOctree<Face>(bunnyVBO.getBoundingBox());
		
		for (Face face:bunnyVBO.getFaces()) {
			Point3D centroid = bunnyVBO.centroid(face);
			octree.add(centroid, face);	
		}
		
		loading = 100;
	}

	@Override
	public void load() {
		loading = 50;
	}
	
	@Override
	public void display(Graphics3D g) {
		GL2 gl = g.getGL2();  // get the OpenGL 2 graphics context
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
		gl.glLoadIdentity();  // reset the model-view matrix
				
		gl.glTranslatef(0.0f, 0.0f, -5.0f); // translate into the screen
		gl.glScaled(2, 2, 2);
		gl.glRotated(angleY, 0, 1, 0);
		
		g.drawFrustrum(frustrum);
				
		//Draw Bunny Model
		bunny.draw(gl);
		
		//Draw Bounding Box
		gl.glColor3f(0, 1, 1);
		renderOctree(g, octree);
				
		//Rotate Model
		if(rotate) {
			angleY += 1;
		}
	}
	
	public void renderOctree(Graphics3D g, Octree<?> tree) {
		OctreeNode<?> root = tree.getRoot();
		renderOctreeNode(g, root);
	}
	
	public void renderOctreeNode(Graphics3D g, OctreeNode<?> node) {
		
		CollisionStatus collision = frustrum.boxInFrustum(node.getBox());
		
		switch (collision) {
		case INSIDE:
			g.setColor(Color.YELLOW);
			break;
		case INTERSECT:
			g.setColor(Color.CYAN);
			break;
		default:
			g.setColor(Color.RED);
			break;
		}
		
		g.drawBoundingBox(node.getBox());
		
		for(OctreeNode<?> child: node.getChildrenNodes()) {
			renderOctreeNode(g, child);
		}
	}

	@Override
	public void reshape(Graphics3D g, int x, int y, int width, int height) {
		GL2 gl = g.getGL2(); // get the OpenGL graphics context
		GLU glu = g.getGLU();

		if (height == 0) height = 1;   // prevent divide by zero
		float aspect = (float)width / height;

		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL2.GL_PROJECTION);  // choose projection matrix
		gl.glLoadIdentity();             // reset projection matrix

		double angle = 45;
		
		double nearDistance = 0.1;
		double farDistance = 100;
		
		glu.gluPerspective(angle, aspect, nearDistance, farDistance); // fovy, aspect, zNear, zFar
		
		frustrum.setCamInternals(angle, aspect, nearDistance, 1);
		frustrum.setCamDef(new Vector3(0, 1, 0.6f), new Vector3(0, 0, 0), new Vector3(0, 1, 0));

		// Enable the model-view transform
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
	}

	public void updateKeyboard(KeyEvent event) {
		if(event.isKeyDown(KeyEvent.VK_SPACE)) {
			rotate = !rotate;
		} else if(event.isKeyUp(KeyEvent.VK_SPACE)) {
			rotate = !rotate;
		}
	}
	
	@Override
	public void draw(Graphic g) {
		
	}
	
}
