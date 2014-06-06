package br.com.luvia.core;

import java.awt.Graphics2D;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import org.lwjgl.util.vector.Vector3f;

import br.com.etyllica.context.Application;
import br.com.luvia.linear.AimPoint;
import br.com.luvia.linear.Point3D;
import br.com.luvia.util.CameraGL;

public abstract class ApplicationGL extends Application implements GLEventListener{
	
	protected GLU glu;
	
	protected double zoom = 1;
	
	protected ApplicationGL returnApplication3D = this;
	
	public ApplicationGL(int w, int h){
		super(w,h);
		glu = new GLU(); // GL Utilities
	}
	
	public ApplicationGL(int x, int y, int width, int height){
		super(x,y,width,height);

		glu = new GLU(); // GL Utilities
	}
	
	@Override
	public void timeUpdate(long now) {
		// TODO Auto-generated method stub
		
	}

	public void preDisplay(Graphics2D g) {
		
	}
		
	@Override
	public void dispose(GLAutoDrawable drawable){
		// TODO Auto-generated method stub
	}
	
	public ApplicationGL getReturnApplication3D(){
		return returnApplication3D;
	}
	
	/*//Texture routines
	protected void enableTextureDefault(GL2 gl){

		gl.glShadeModel (GL2.GL_FLAT);

		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
		gl.glTexParameteri (GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri (GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);

		gl.glTexParameteri (GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl.glTexParameteri (GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);


		gl.glEnable (GL2.GL_DEPTH_TEST);
		gl.glDepthFunc (GL2.GL_LESS);

		gl.glEnable (GL2.GL_TEXTURE_2D);

		gl.glEnable (GL2.GL_CULL_FACE);
		gl.glCullFace (GL2.GL_BACK);		

	}*/
	
	protected int[] getViewPort(GL2 gl){

		int viewport[] = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

		return viewport;
	}

	protected double[] getModelView(GL2 gl){

		double modelView[] = new double[16];
		gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelView, 0);

		return modelView;
	}

	protected double[] getProjection(GL2 gl){

		double projection[] = new double[16];

		gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projection, 0);

		return projection;
	}
	
	protected Point3D get3DPointerFromMouse(GL2 gl, float mx, float my, float zPlane){
		
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
	
	protected Point3D get3DPointerFromMouse(GL2 gl, float mx, float my){
		
		return get3DPointerFromMouse(gl, mx, my, 0);

	}
	
	protected double[] get2DPositionFromPoint(GL2 gl, double px, double py, double pz){

		double[] position = new double[3];

		int[] viewport = getViewPort(gl);
		double[] modelview = getModelView(gl);
		double[] projection = getProjection(gl);

		glu.gluProject(px, py, pz, modelview, 0, projection, 0, viewport, 0, position, 0);

		return position;

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
	
}
