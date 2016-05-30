package examples.tutorial5;	

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
import br.com.luvia.graphics.CustomBillboard;
import br.com.luvia.loader.TextureLoader;
import br.com.luvia.util.DirectionUtil;

import com.badlogic.gdx.math.Vector3;
import com.jogamp.opengl.util.texture.Texture;

public class RotateAroundExample extends ApplicationGL {

	private Camera camera;

	private CustomBillboard billboard;
	private Texture texture;
	
	private Vector3 sphereCenter;
	private Vector3 rotationAxis;

	private int markerCount = 3;

	protected float mx = 0;
	protected float my = 0;

	protected boolean click = false;

	private List<Boolean> activeMarkers = new ArrayList<Boolean>(markerCount);

	public RotateAroundExample(int w, int h) {
		super(w, h);
	}

	@Override
	public void init(Graphics3D drawable) {
		for (int i = 0; i < markerCount; i++) {
			activeMarkers.add(false);
		}

		billboard = new CustomBillboard(5,5,new Vector3(2,0,0));
		billboard.turnTo(new Vector3(0,2,0));

		BufferedImage spriteImage = ImageLoader.getInstance().getImage("active_mark.png");		
		texture = TextureLoader.getInstance().loadTexture(spriteImage);

		activeMarkers.add(1, true);
		
		sphereCenter = new Vector3(billboard.center).add(0,1,0);
		rotationAxis = DirectionUtil.directionOrthogonal(sphereCenter, billboard.center);
		
		billboard.center.setZero();
	}

	@Override
	public void load() {
		camera = new Camera(0, 15, 0.001f);
		loading = 100;
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
		gl.glEnable(GL.GL_DEPTH_TEST);
	}
	
	float angle = 2;

	@Override
	public void display(Graphics3D drawable) {

		GL2 gl = drawable.getGL().getGL2();
		drawable.setColor(Color.WHITE);

		//Transform by Camera
		drawable.updateCamera(camera);

		//Draw Billboard
		texture.enable(gl);
		texture.bind(gl);
		drawable.drawBillboard(billboard);
		texture.disable(gl);
		
		DirectionUtil.rotateAround(sphereCenter, billboard.center, rotationAxis, angle);
		
		billboard.turnTo(sphereCenter);
		
		gl.glPushMatrix();
		drawable.setColor(Color.BLUE);
		drawable.drawSphere(1, sphereCenter.x, sphereCenter.y, sphereCenter.z);
		gl.glPopMatrix();

		gl.glFlush();
	}

}