package br.com.luvia.graphics;

import javax.media.opengl.GL2;

import com.badlogic.gdx.math.Vector3;

import br.com.abby.linear.BaseCone;
import br.com.luvia.core.graphics.Graphics3D;

public class Cone extends BaseCone {

	public Cone(int sides) {
		super(sides);
	}
	
	public Cone(int sides, float height) {
		super(sides, height);
	}

	public void draw(Graphics3D g) {
		GL2 gl = g.getGL2();
		
		gl.glMultMatrixf(transform.val, 0);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glVertex3f(position.x, position.y + height/2, position.z);
		for (Vector3 point : lowerPoints) {
			g.vertex(point);
		}
		gl.glEnd();
		
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glVertex3f(position.x, position.y - height/2, position.z);
		for (Vector3 point : lowerPoints) {
			g.vertex(point);
		}
		gl.glEnd();
	}
	
}
