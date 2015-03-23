package br.com.luvia.core.context;

import java.awt.Color;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import org.lwjgl.util.vector.Vector3f;

import br.com.abby.linear.AimPoint;
import br.com.abby.linear.Point3D;
import br.com.abby.util.CameraGL;
import br.com.etyllica.context.Application;
import br.com.luvia.core.G3DEventListener;
import br.com.luvia.core.video.Graphics3D;

public abstract class ApplicationGL extends Application implements G3DEventListener {
	
	protected GLU glu;
	
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
		
		glu = new GLU(); // GL Utilities
		clearBeforeDraw = false;
	}
	
	@Override
	public void timeUpdate(long now) {
		// TODO Auto-generated method stub
		
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
		
	protected int[] getViewPort(GL2 gl) {

		int viewport[] = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

		return viewport;
	}

	protected double[] getModelView(GL2 gl) {

		double modelView[] = new double[16];
		gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelView, 0);

		return modelView;
	}

	protected double[] getProjection(GL2 gl) {

		double projection[] = new double[16];

		gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projection, 0);

		return projection;
	}
	
	protected Point3D get3DPointerFromMouse(GL2 gl, float mx, float my, float zPlane) {
		
		final int X = 0;
		final int Y = 1;
		final int Z = 2;

		int[] viewport = getViewPort(gl);
		double[] modelview = getModelView(gl);
		double[] projection = getProjection(gl);

		//World near Coordinates
		double wncoord[] = new double[4];

		//World far Coordinates
		double wfcoord[] = new double[4];

		glu.gluUnProject((double) mx, (double) my, 0.0, modelview, 0, projection, 0, viewport, 0, wncoord, 0);
		Vector3f v1 = new Vector3f ((float)wncoord[X], (float)wncoord[Y], (float)wncoord[Z] );

		glu.gluUnProject((double) mx, (double) my, 1.0, modelview, 0, projection, 0, viewport, 0, wfcoord, 0);
		Vector3f v2 = new Vector3f ((float)wfcoord[X], (float)wfcoord[Y], (float)wfcoord[Z] );

		float t = (v1.getY() - zPlane) / (v1.getY() - v2.getY());

		// so here are the desired (x, y) coordinates
		float fX = v1.getX() + (v2.getX() - v1.getX()) * t;
		float fZ = v1.getZ() + (v2.getZ() - v1.getZ()) * t;

		Point3D point = new Point3D(fX, 0, fZ);
		
		return point;
		
	}
	
	protected Point3D get3DPointerFromMouse(GL2 gl, float mx, float my) {
		
		return get3DPointerFromMouse(gl, mx, my, 0);

	}
	
	protected double[] get2DPositionFromPoint(GL2 gl, double px, double py, double pz) {

		double[] position = new double[3];

		int[] viewport = getViewPort(gl);
		double[] modelview = getModelView(gl);
		double[] projection = getProjection(gl);

		glu.gluProject(px, py, pz, modelview, 0, projection, 0, viewport, 0, position, 0);

		return position;

	}
	
	protected Point3D transformPosition(GL2 gl, Point3D point) {
				
		double[] m = getModelView(gl);
		
		double x = m[0]*point.getX()+m[4]*point.getY()+m[8]*point.getZ();//+m[12]*0;
		
		double y = m[1]*point.getX()+m[5]*point.getY()+m[9]*point.getZ();//+m[13]*0;
		
		double z = m[2]*point.getX()+m[6]*point.getY()+m[10]*point.getZ();//+m[14]*0;
		
		//double w = m[3]*point.getX()+m[7]*point.getY()+m[11]*point.getZ()+m[15]*0;
		
		return new Point3D(x, y, z);
	}
	
	public void updateCamera(GL2 gl, CameraGL camera) {
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		glu.gluLookAt( camera.getX(), camera.getY(), camera.getZ(), camera.getTarget().getX(), camera.getTarget().getY(), camera.getTarget().getZ(), 0, 1, 0 );
	}
	
	protected void aimCamera(GL2 gl, AimPoint aim) {
		gl.glMatrixMode(GL2.GL_MODELVIEW);
							
		gl.glLoadIdentity();
		
		gl.glRotated(360-aim.getAngleX(), 1, 0, 0);
		gl.glRotated(360-aim.getAngleY(), 0, 1, 0);
		gl.glRotated(360-aim.getAngleZ(), 0, 0, 1);
		
		gl.glTranslated(-aim.getX(), -aim.getY(), -aim.getZ());
	}
		
	public void setColor(GL2 gl, Color color) {
		float red = ((float)color.getRed()/255);
		float green = ((float)color.getGreen()/255);
		float blue = ((float)color.getBlue()/255);
				
		gl.glColor3f(red, green, blue);
	}
	
}
