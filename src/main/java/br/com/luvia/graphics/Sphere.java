package br.com.luvia.graphics;

import com.badlogic.gdx.math.Vector3;

import br.com.abby.linear.Shape;
import br.com.luvia.core.graphics.Graphics3D;

public class Sphere extends Shape {

	private double radius = 1;

	public Sphere(double radius) {
		super();
		this.radius = radius;
	}

	public void draw(Graphics3D g) {
		Vector3 position = position();
		g.drawSphere(radius, position.x, position.y, position.z, Graphics3D.DEFAULT_RESOLUTION, color);	  
	}

}
