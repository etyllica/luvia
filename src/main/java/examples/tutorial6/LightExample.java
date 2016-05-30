package examples.tutorial6;	

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import br.com.abby.linear.Camera;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.graphics.Graphics3D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class LightExample extends ApplicationGL {

	private Camera camera;
	private PerspectiveCamera cam;
	
	public Model model;
    public ModelInstance instance;
    private ModelBatch modelBatch;

	public LightExample(int w, int h) {
		super(w, h);
	}

	@Override
	public void init(Graphics3D drawable) {
		Gdx.graphics = drawable;
		Gdx.gl = drawable.getGL2();
		Gdx.gl20 = drawable.getGL2();
		Gdx.gl30 = drawable.getGL3();
		Gdx.app = this;
		
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
		
		modelBatch = new ModelBatch();
		
		ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5f, 5f, 5f, 
            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
            Usage.Position | Usage.Normal);
        instance = new ModelInstance(model);
		
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
		drawable.setColor(java.awt.Color.WHITE);
		
		//Transform by Camera
		//drawable.updateCamera(camera);
		modelBatch.begin(cam);
        modelBatch.render(instance);
        modelBatch.end();
		
		gl.glFlush();
	}

}