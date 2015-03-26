package br.com.luvia.core.video;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLBase;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import org.jogamp.glg2d.GLGraphics2D;
import org.lwjgl.util.vector.Vector3f;

import com.jogamp.opengl.util.texture.Texture;

import br.com.abby.linear.AimPoint;
import br.com.abby.linear.Point3D;
import br.com.abby.util.CameraGL;
import br.com.etyllica.core.graphics.Graphic;

public class Graphics3D extends Graphic {
	
	private GLU glu;
	
	private GLAutoDrawable drawable;

	public Graphics3D(int width, int heigth) {
		super(width,heigth);
		
		glu = new GLU(); // GL Utilities
	}

	public void setGraphics(Graphics2D graphics) {
		this.screen = graphics;
		this.screen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
	}
	
	public void setGraphics(GLGraphics2D graphics) {
		this.screen = graphics;
		this.screen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public GLAutoDrawable getDrawable() {
		return drawable;
	}

	public void setDrawable(GLAutoDrawable drawable) {
		this.drawable = drawable;
	}	
	
	public int[] getViewPort() {
		GL2 gl = drawable.getGL().getGL2();
		
		int viewport[] = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

		return viewport;
	}
	
	public void drawSphere(AimPoint point, double radius) {
		
		GL2 gl = drawable.getGL().getGL2();
		
		GLUquadric sphere = glu.gluNewQuadric();

		glu.gluQuadricDrawStyle(sphere, GLU.GLU_FILL);
		glu.gluQuadricTexture(sphere, true);
		glu.gluQuadricNormals(sphere, GLU.GLU_SMOOTH);
				  		
		// draw a sphere
        gl.glPushMatrix();                  
            gl.glTranslated(point.getX(), point.getY(), point.getZ());
            gl.glRotated(point.getAngleY(), 0, 1, 0);
            gl.glRotated(point.getAngleX(), 1, 0, 0);
            glu.gluSphere(sphere, radius, 32, 32);
        gl.glPopMatrix();
	}
	
	public void drawTile(double x, double y, double tileSize, Texture texture) {

		GL2 gl = drawable.getGL().getGL2();
		
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
	
	public double[] getModelView() {

		double modelView[] = new double[16];
		drawable.getGL().getGL2().glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelView, 0);

		return modelView;
	}

	public double[] getProjection() {

		double projection[] = new double[16];

		getGL2().glGetDoublev(GL2.GL_PROJECTION_MATRIX, projection, 0);

		return projection;
	}
	
	public Point3D get3DPointerFromMouse(float mx, float my) {
		
		return get3DPointerFromMouse(mx, my, 0);

	}
	
	public double[] get2DPositionFromPoint(double px, double py, double pz) {

		double[] position = new double[3];

		int[] viewport = getViewPort();
		double[] modelview = getModelView();
		double[] projection = getProjection();

		glu.gluProject(px, py, pz, modelview, 0, projection, 0, viewport, 0, position, 0);

		return position;

	}
	
	public Point3D get3DPointerFromMouse(float mx, float my, float zPlane) {
		
		final int X = 0;
		final int Y = 1;
		final int Z = 2;

		int[] viewport = getViewPort();
		double[] modelview = getModelView();
		double[] projection = getProjection();

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
	
	public void updateCamera(CameraGL camera) {
		drawable.getGL().getGL2().glMatrixMode(GL2.GL_MODELVIEW);
		drawable.getGL().getGL2().glLoadIdentity();
		
		glu.gluLookAt( camera.getX(), camera.getY(), camera.getZ(), camera.getTarget().getX(), camera.getTarget().getY(), camera.getTarget().getZ(), 0, 1, 0 );
	}
	
	public void aimCamera(AimPoint aim) {
		GL2 gl = getGL2();
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
							
		gl.glLoadIdentity();
		
		gl.glRotated(360-aim.getAngleX(), 1, 0, 0);
		gl.glRotated(360-aim.getAngleY(), 0, 1, 0);
		gl.glRotated(360-aim.getAngleZ(), 0, 0, 1);
		
		gl.glTranslated(-aim.getX(), -aim.getY(), -aim.getZ());
	}

	public GLBase getGL() {
		return drawable.getGL();
	}
	
	public GL2 getGL2() {
		return getGL().getGL2();
	}

	public GLU getGLU() {
		return glu;
	}
	
}
