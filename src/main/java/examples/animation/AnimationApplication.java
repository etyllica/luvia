package examples.animation;

import java.awt.Color;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import br.com.abby.core.loader.AnimationLoader;
import br.com.abby.core.model.Armature;
import br.com.abby.core.model.Bone;
import br.com.abby.core.model.Joint;
import br.com.abby.core.model.motion.KeyFrame;
import br.com.abby.core.model.motion.Motion;
import br.com.abby.core.view.FlyView;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.graphics.Graphics3D;
import br.com.luvia.core.view.UEView;

public class AnimationApplication extends ApplicationGL {
	
	protected FlyView view;
	private Motion motion;
		
	public AnimationApplication(int width, int height) {
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
		
		view = new UEView(-10, 0, 0);
		view.getAim().rotate(Vector3.Y, 90);
		
		//Load motion
		motion = AnimationLoader.getInstance().loadMotion("anim.bvh");
		//motion = AnimationLoader.getInstance().loadMotion("02_01.bvh");
		
		loading = 100;
	}

	@Override
	public void load() {
		loading = 50;
	}
	
	@Override
	public void display(Graphics3D g) {
		view.update(0);
		
		GL2 gl = g.getGL2();  // get the OpenGL 2 graphics context
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
		gl.glClearColor(1f, 1f, 1f, 1);
		gl.glLoadIdentity();  // reset the model-view matrix
		
		//Transform by Aim
		g.aimCamera(view.getAim());
		
		g.setColor(Color.CYAN);
		
		int i = 0;
		for (Bone bone : motion.getArmature().getBones()) {
			g.drawLine(bone.getOrigin().getOffset(), bone.getOffset());
			g.drawSphere(bone.getOffset(), 0.1);
		}
		
		updateAnimation();
	}
	
	private void updateAnimation() {
		long now = System.currentTimeMillis();
		long startAnimation = 0;
		
		float FPS = 1/motion.getFrameTime();
		int interval = (int)(1000/FPS);
		int keyFrameIndex = (int)((now-startAnimation)/interval);
		keyFrameIndex %= motion.getFrames();
		
		KeyFrame frame = motion.getKeyFrames().get(keyFrameIndex);
		
		int bones = frame.getTransforms().size();
		
		Joint root = motion.getArmature().getRoot();
		
		int i = 0;
		for (Bone bone : motion.getArmature().getBones()) {
			Matrix4 t = frame.getTransform(i);
			if (t == null) {
				continue;
			}
			for(Bone child : bone.getChildren()) {
				child.rotate(bone, t);
			}
			
			//Apply Transformation to Vertices
			//Matrix4 t2 = new Matrix4(t);
			//float weight = 1;
			//t2.scl(weight);
			//for (each associated vertex v)
			//Vector3 p = new Vector3(v);
			//p.sub(bone.getOrigin());
			//t2.transform(P);
			//p.add(bone.getOrigin());
			i++;
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

		glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear, zFar

		// Enable the model-view transform
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
	}
	
	@Override
	public void draw(Graphics g) {
		
	}
	
	@Override
	public void updateKeyboard(KeyEvent event) {
		view.updateKeyboard(event);
	}
}