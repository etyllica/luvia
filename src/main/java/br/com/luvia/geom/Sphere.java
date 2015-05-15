package br.com.luvia.geom;

import br.com.luvia.core.video.Graphics3D;

public class Sphere extends GeometricForm {

	private static final int DEFAULT_RESOLUTION = 16;
	
	private double radius = 1;
	
	public Sphere(double radius) {
		super();		
		this.radius = radius;
	}
	
	public void draw(Graphics3D g) {
	  g.drawSphere(radius, x, y, z, DEFAULT_RESOLUTION, color);	  
	}
	
}
