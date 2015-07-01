package br.com.luvia.light;

import java.awt.Color;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import br.com.abby.linear.AimPoint;

public class LightSource3D extends AimPoint {

	public void configureLight(GL2 gl, int lightId) {
		//Default specular and shininess
		float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float mat_shininess[] = { 50.0f };
		gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, mat_specular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, mat_shininess, 0);

		FloatBuffer lightPosition = FloatBuffer.wrap(new float[]{(float)x,(float)y,(float)z});
		gl.glLightfv(lightId, GL2.GL_POSITION, lightPosition);

		float[] array = colorAsArray(color);

		FloatBuffer lightAmbientColor = FloatBuffer.wrap(array);
		gl.glLightfv(lightId, GL2.GL_AMBIENT, lightAmbientColor);
	}

	private float[] colorAsArray(Color color) {
		float r = (float)color.getRed()/255;
		float g = (float)color.getGreen()/255;
		float b = (float)color.getBlue()/255;
		
		float[] array = new float[]{r,g,b};
		return array;
	}
	
}
