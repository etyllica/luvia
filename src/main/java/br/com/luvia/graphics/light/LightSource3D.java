package br.com.luvia.graphics.light;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import br.com.abby.linear.Shape;
import br.com.luvia.core.graphics.Graphics3D;

public class LightSource3D extends Shape {

	public void configureLight(Graphics3D g, int lightId) {
		GL2 gl = g.getGL2();
		
		//Default specular and shininess
		float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float mat_shininess[] = { 50.0f };
		gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, mat_specular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, mat_shininess, 0);

		FloatBuffer lightPosition = FloatBuffer.wrap(new float[]{position.x,position.y,position.z});
		gl.glLightfv(lightId, GL2.GL_POSITION, lightPosition);

		float[] array = g.colorAsArray(color);

		FloatBuffer lightAmbientColor = FloatBuffer.wrap(array);
		gl.glLightfv(lightId, GL2.GL_AMBIENT, lightAmbientColor);
	}

	
}
