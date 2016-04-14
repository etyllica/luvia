package br.com.luvia.core.context;

import javax.media.opengl.GL2;

import br.com.abby.linear.ColoredPoint3D;
import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.graphics.Graphic;
import br.com.luvia.core.G3DEventListener;
import br.com.luvia.core.video.Graphics3D;

import com.badlogic.gdx.math.Matrix4;

public abstract class ApplicationGL extends Application implements com.badlogic.gdx.Application, G3DEventListener {
		
	protected double zoom = 1;
	
	protected ApplicationGL returnApplication3D = this;
	
	public ApplicationGL(int w, int h) {
		super(w,h);
		init();
	}
	
	public ApplicationGL(int x, int y, int width, int height) {
		super(x,y,width,height);
		init();
	}
	
	private void init() {
		nextApplication = this;		
		clearBeforeDraw = false;
	}
	
	public void preDisplay(Graphics3D g) {
		
	}
		
	public abstract void display(Graphics3D g);
		
	@Override
	public void dispose(Graphics3D g) {
		// TODO Auto-generated method stub
	}
	
	public ApplicationGL getReturnApplication3D() {
		return returnApplication3D;
	}

	protected ColoredPoint3D transformPosition(Graphics3D g, ColoredPoint3D point) {
				
		double[] m = g.getModelView();
		
		double x = m[0]*point.getX()+m[4]*point.getY()+m[8]*point.getZ();//+m[12]*0;
		
		double y = m[1]*point.getX()+m[5]*point.getY()+m[9]*point.getZ();//+m[13]*0;
		
		double z = m[2]*point.getX()+m[6]*point.getY()+m[10]*point.getZ();//+m[14]*0;
		
		//double w = m[3]*point.getX()+m[7]*point.getY()+m[11]*point.getZ()+m[15]*0;
		
		return new ColoredPoint3D(x, y, z);
	}
	
	protected int[] getViewPort(Graphics3D g) {
		return g.getViewPort();
	}

	protected double[] getModelView(Graphics3D g) {
		return g.getModelView();
	}
	
	protected double[] getProjection(Graphics3D g) {
		return g.getProjection();
	}
	
	protected Matrix4 getProjectionMatrix(Graphics3D g) {
		GL2 gl = g.getGL2();
		
		float[] projection = new float[16];
		gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection, 0);

		return new Matrix4(projection);
	}
	
	@Override
	public void load() { }

	@Override
	public void draw(Graphic g) { }

	//LibGDX methods
	public void debug(String tag, String message) {
		System.out.println(tag+" "+message);
	}
	
	public void debug(String tag, String message, Exception exception) {
		System.out.println(tag+" "+message+" "+exception);
	}
	
	public void log(String tag, String message) {
		System.out.println(tag+" "+message);
	}
	
	public void log(String tag, String message, Exception exception) {
		System.out.println(tag+" "+message+" "+exception);
	}
	
	public void error(String tag, String message) {
		System.err.println(tag+" "+message);
	}
	
	public void error(String tag, String message, Throwable exception) {
		System.err.println(tag+" "+message+" "+exception);
	}

	public ApplicationType getType() {
		return ApplicationType.Desktop;
	}

}
