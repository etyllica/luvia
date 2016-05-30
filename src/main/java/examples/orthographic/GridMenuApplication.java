package examples.orthographic;

import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import br.com.abby.linear.Camera;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.loader.image.ImageLoader;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.graphics.Graphics3D;
import br.com.luvia.loader.TextureLoader;

import com.jogamp.opengl.util.texture.Texture;

public class GridMenuApplication extends ApplicationGL {

	private Texture marker;
	private Texture active;
	
	private Camera camera;
	
	private int markerCount = 6;
	private double tileSize = 5;
	
	protected float mx = 0;
	protected float my = 0;

	protected boolean click = false;
	
	private List<Boolean> activeMarkers = new ArrayList<>(markerCount);

	public GridMenuApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void init(Graphics3D drawable) {
		for (int i = 0; i < markerCount; i++) {
			activeMarkers.add(false);
		}
		
		BufferedImage markImage = ImageLoader.getInstance().getImage("mark.png");
		BufferedImage activeMarkImage = ImageLoader.getInstance().getImage("active_mark.png");
		marker = TextureLoader.getInstance().loadTexture(markImage);
		active = TextureLoader.getInstance().loadTexture(activeMarkImage);
		
		activeMarkers.add(3, true);
	}
	
	@Override
	public void load() {
		camera = new Camera(0, 10, 0.001);
		
		loading = 100;
	}
	
	protected void lookCamera(Graphics3D g) {
		GL2 gl = g.getGL2();
		GLU glu = g.getGLU();
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		double targetx = 0;
		double targety = 0;
		double targetz = 0;
		
		glu.gluLookAt( camera.getX(), camera.getY(), camera.getZ(), targetx, targety, targetz, 0, 1, 0 );
	}
	
	protected void drawFloor(GL2 gl) {
		gl.glColor3d(1,1,1);

		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

		drawGrid(gl,200,120);
	}

	private void drawGrid(GL2 gl, double x, double y) {
		
		double spacing = 0.1;
		Texture texture = marker;
		
		int count = 0;
		for(int i=0;i<3;i++) {
			if (!activeMarkers.get(count)) {
				texture = marker;
			} else {
				texture = active;
			}
			
			//Upper Row
			drawTile(gl, -1.5+i+(i*spacing), -1, tileSize, texture);
			count++;
		}
		
		for(int i=0;i<3;i++) {
			if (!activeMarkers.get(count)) {
				texture = marker;
			} else {
				texture = active;
			}
			
			//Lower Row
			drawTile(gl, -1.5+i+(i*spacing), spacing, tileSize, texture);
			count++;
		}
	}

	private void drawTile(GL2 gl, double x, double y, double tileSize, Texture texture) {

		texture.enable(gl);
		texture.bind(gl);
		
		gl.glBegin(GL2.GL_QUADS);

		//(0,0)
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(x*tileSize, 0, y*tileSize);

		//(1,0)
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(x*tileSize+tileSize, 0, y*tileSize);

		//(1,1)
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(x*tileSize+tileSize, 0, y*tileSize+tileSize);

		//(0,1)
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(x*tileSize, 0, y*tileSize+tileSize);

		gl.glEnd();
		
		texture.disable(gl);
	}

	@Override
	public void reshape(Graphics3D drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL2();
		GLU glu = drawable.getGLU();

		gl.glViewport (x, y, width, height);

		gl.glMatrixMode(GL2.GL_PROJECTION);

		gl.glLoadIdentity();
				
		float aspect = (float)width / (float)height; 
		
		glu.gluPerspective(60, aspect, 1, 100);

		gl.glMatrixMode(GL2.GL_MODELVIEW);

		gl.glLoadIdentity();

	}
	
	@Override
	public void update(long now) {
		
	}
	
	@Override
	public void updateKeyboard(KeyEvent event) {
		
	}
	
	@Override
	public void updateMouse(PointerEvent event) {
		
	}

	@Override
	public void preDisplay(Graphics3D g) {
		
		GL2 gl = g.getDrawable().getGL().getGL2();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor(1f, 1f, 1f, 1);
		
		g.setColor(Color.YELLOW);
		g.fillRect(10, 10, w, 60);
	}
	
	@Override
	public void display(Graphics3D drawable) {

		GL2 gl = drawable.getGL().getGL2();
		
		//Transform by Camera
		drawable.updateCamera(camera);

		//Draw Scene
		drawFloor(gl);

		gl.glFlush();
	}
	
}