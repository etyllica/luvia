package examples.custom;

import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import br.com.abby.util.CameraGL;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.video.Graphics3D;
import br.com.luvia.loader.TextureLoader;

import com.jogamp.opengl.util.texture.Texture;

public class MarkerApplication extends ApplicationGL {

	private Texture marker;
	
	private CameraGL camera;
	
	protected float mx = 0;
	protected float my = 0;

	protected boolean click = false;
	
	private double angleX = 0;
	
	private double angleY = 0;
	
	private double angleZ = 0;

	public MarkerApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void init(Graphics3D drawable) {
		
		//marker = TextureLoader.getInstance().loadTexture("/mark.png");
		
		BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 200, 200);
		
		g.setColor(Color.RED);
		g.fillOval(50, 50, 100, 100);
		
		g.setColor(Color.BLACK);
		
		int stroke = 8;
		
		g.setStroke(new BasicStroke(stroke));
		g.drawRect(stroke, stroke, 200-stroke*2, 200-stroke*2);
		
		marker = TextureLoader.getInstance().loadTexture(image);
	}
	
	@Override
	public void load() {
		
		camera = new CameraGL(0,15,1);
		
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

		double tileSize = 5;

		drawTile(gl, -.5, -.5, tileSize, marker);
		
		drawTile(gl, -2.5, -.5, tileSize, marker);
		
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
	
	private boolean upArrow = false;
	private boolean downArrow = false;
	private boolean leftArrow = false;
	private boolean rightArrow = false;
	
	@Override
	public void update(long now) {
		
		if(upArrow) {
			angleX += 1;
		} else if(downArrow) {
			angleX -= 1;
		}
		
		if(leftArrow) {
			angleY += 1;
		} else if(rightArrow) {
			angleY -= 1;
		}
		
	}
	
	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		
		if(event.isKeyDown(KeyEvent.TSK_UP_ARROW)) {
			
			upArrow = true;
			
		} else if (event.isKeyUp(KeyEvent.TSK_UP_ARROW)) {
			
			upArrow = false;
		}
				
		if(event.isKeyDown(KeyEvent.TSK_DOWN_ARROW)) {
			
			downArrow = true;
			
		} else if(event.isKeyUp(KeyEvent.TSK_DOWN_ARROW)) {
			
			downArrow = false;
		}
		
		if(event.isKeyDown(KeyEvent.TSK_LEFT_ARROW)) {
			
			leftArrow = true;
			
		} else if(event.isKeyUp(KeyEvent.TSK_LEFT_ARROW)) {
			
			leftArrow = false;
		}
		
		if(event.isKeyDown(KeyEvent.TSK_RIGHT_ARROW)) {
			
			rightArrow = true;
			
		} else if (event.isKeyUp(KeyEvent.TSK_RIGHT_ARROW)) {
			
			rightArrow = false;
		}
		
		if(event.isKeyDown(KeyEvent.TSK_VIRGULA)) {
			
			angleZ += 5;
			
		} else if(event.isKeyDown(KeyEvent.TSK_PONTO)) {
			
			angleZ -= 5;
			
		}
		
		return GUIEvent.NONE;
	}
	
	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		mx = event.getX();
		my = event.getY();

		if(event.isButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {
			camera.setZ(camera.getZ()+0.1f);
			click = true;
		}

		if(event.isButtonUp(MouseButton.MOUSE_BUTTON_LEFT)) {
			camera.setZ(camera.getZ()-0.1f);
			click = false;
		}

		return GUIEvent.NONE;
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
		lookCamera(drawable);
		
		gl.glRotated(angleX, 1, 0, 0);
		gl.glRotated(angleY, 0, 1, 0);
		gl.glRotated(angleZ, 0, 0, 1);

		//Draw Scene
		drawFloor(gl);
		

		gl.glFlush();

	}
	

	@Override
	public void draw(Graphic g) {

		//Draw Gui
		g.setColor(Color.WHITE);
		g.drawShadow(20,20, "Scene",Color.BLACK);
		
		g.drawShadow(20,40, "AngleX: "+(angleX-5),Color.BLACK);
		
		g.drawShadow(20,60, "AngleY: "+(angleY),Color.BLACK);
		
		g.drawShadow(20,80, "AngleZ: "+(angleZ),Color.BLACK);
		
		
	}
	
}