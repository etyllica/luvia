package br.com.luvia.geom;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Sphere extends GeometricForm {

	private static final int DEFAULT_RESOLUTION = 16;
	
	private double radius = 1;
	
	private int slices = DEFAULT_RESOLUTION;
	private int stacks = DEFAULT_RESOLUTION;

	public Sphere(double radius) {
		super();		
		this.radius = radius;
	}
	
	//TODO avoid GLU usage
	public void draw(GL2 gl, GLU glu) {

		gl.glPushMatrix();
	
		changeColor(gl);

		gl.glTranslated(x, y, z);

		drawSphere(glu);

		gl.glPopMatrix();
	}

	protected void drawSphere(GLU glu) {
		GLUquadric sphere = generateSphereQuadric(glu);

		glu.gluSphere(sphere, radius, slices, stacks);

		glu.gluDeleteQuadric(sphere);
	}
	
	private GLUquadric generateSphereQuadric(GLU glu) {
		GLUquadric sphere = glu.gluNewQuadric();

		// Draw sphere (possible styles: FILL, LINE, POINT)
		glu.gluQuadricDrawStyle(sphere, GLU.GLU_FILL);
		glu.gluQuadricNormals(sphere, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(sphere, GLU.GLU_OUTSIDE);
		
		return sphere;
	}
	
}
