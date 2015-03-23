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

import br.com.abby.linear.AimPoint;
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

	public GLBase getGL() {
		return drawable.getGL();
	}
}
