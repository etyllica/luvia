package br.com.luvia.graphics;

import br.com.luvia.core.video.Graphics3D;

public class Sphere extends GeometricForm {

	private double radius = 1;

	public Sphere(double radius) {
		super();
		this.radius = radius;
	}

	public void draw(Graphics3D g) {
		g.drawSphere(radius, x, y, z, Graphics3D.DEFAULT_RESOLUTION, color);	  
	}

}
