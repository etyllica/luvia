package br.com.luvia.geom;

import javax.media.opengl.GL2;

import br.com.abby.linear.AimPoint;

public abstract class GeometricForm extends AimPoint /*implements GL2Drawable*/ {

	protected void changeColor(GL2 gl) {
		float red = ((float)color.getRed()/255);
		float green = ((float)color.getGreen()/255);
		float blue = ((float)color.getBlue()/255);

		gl.glColor3f(red, green, blue);
	}	

}
