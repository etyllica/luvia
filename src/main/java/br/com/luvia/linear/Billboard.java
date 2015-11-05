package br.com.luvia.linear;

import com.badlogic.gdx.math.Vector3;

public class Billboard {

	private static final Vector3 UP = new Vector3(0, 1, 0);
	
	public float width, height;
	public Vector3 center;
	public Vector3 direction;
	
	private Vector3 p00, p10, p11, p01;
	
	public Billboard(Vector3 center) {
		this.center = center;
	}
	
	public Billboard(float width, float height, Vector3 center) {
		this.width = width;
		this.height = height;
		this.center = center;
	}
		
	public void directTo(Vector3 destination) {
		directTo(destination, UP);
	}
	
	public void directTo(Vector3 destination, Vector3 up) {
		direction = new Vector3(center).sub(destination);
		direction.nor();
		
		float dot = up.dot(destination);
		float len = up.len()*destination.len();
		float cos = dot/len;
		float angle = (float)Math.acos(cos);
		
		p00 = new Vector3(center.x-width/2, center.y, center.z-height/2);
		p10 = new Vector3(center.x+width/2, center.y, center.z-height/2);
		p11 = new Vector3(center.x+width/2, center.y, center.z+height/2);
		p01 = new Vector3(center.x-width/2, center.y, center.z+height/2);
		
		p00.rotateRad(direction, angle);
		p10.rotateRad(direction, angle);
		p11.rotateRad(direction, angle);
		p01.rotateRad(direction, angle);
	}

	public Vector3 getP00() {
		return p00;
	}

	public void setP00(Vector3 p00) {
		this.p00 = p00;
	}

	public Vector3 getP10() {
		return p10;
	}

	public void setP10(Vector3 p10) {
		this.p10 = p10;
	}

	public Vector3 getP11() {
		return p11;
	}

	public void setP11(Vector3 p11) {
		this.p11 = p11;
	}

	public Vector3 getP01() {
		return p01;
	}

	public void setP01(Vector3 p01) {
		this.p01 = p01;
	}
		
}
