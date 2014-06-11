package br.com.luvia.linear;

import org.lwjgl.util.vector.Vector3f;

public class Vector extends Vector3f {

	private static final long serialVersionUID = -292334515787858614L;

	public Vector(float x, float y, float z) {
		super(x,y,z);
	}
	
	public void rotateY(float angle, float px, float py, float pz){

		rotateOver(angle, px, py, pz, rotationMatrixY(angle, px, py, pz));

	}
	
	private void rotateOver(float angle, float px, float py, float pz, float m[][]){

		float ix = x;
		float iy = y;
		float iz = z;

		x = ix*m[0][0]+iy*m[0][1]+iz*m[0][2]+1*m[0][3];
		y = ix*m[1][0]+iy*m[1][1]+iz*m[1][2]+1*m[1][3];
		y = ix*m[2][0]+iy*m[2][1]+iz*m[2][2]+1*m[2][3];

	}
	
	private float[][] rotationMatrixX(float angle, float px, float py, float pz) {

		float m[][] = new float[4][4];

		m[0][0] = 1;
		m[0][1] = 0;
		m[0][2] = 0;
		m[0][3] = px - m[0][0]*px - m[0][1]*py - m[0][2]*pz;

		m[1][0] = 0;
		m[1][1] = cos(angle);
		m[1][2] = -sin(angle);
		m[1][3] = py - m[1][0]*px - m[1][1]*py - m[1][2]*pz;

		m[2][0] = 0;
		m[2][1] = sin(angle);
		m[2][2] = cos(angle);
		m[2][3] = pz - m[2][0]*px - m[2][1]*py - m[2][2]*pz;

		m[3][0] = 0;
		m[3][1] = 0;
		m[3][2] = 0;
		m[3][3] = 1;

		return m;
	}

	private float[][] rotationMatrixY(float angle, float px, float py, float pz){

		float m[][] = new float[4][4];

		m[0][0] = cos(angle);
		m[0][1] = 0;
		m[0][2] = sin(angle);
		m[0][3] = px - m[0][0]*px - m[0][1]*py - m[0][2]*pz;

		m[1][0] = 0;
		m[1][1] = 1;
		m[1][2] = 0;
		m[1][3] = py - m[1][0]*px - m[1][1]*py - m[1][2]*pz;

		m[2][0] = -sin(angle);
		m[2][1] = 0;
		m[2][2] = cos(angle);
		m[2][3] = pz - m[2][0]*px - m[2][1]*py - m[2][2]*pz;

		m[3][0] = 0;
		m[3][1] = 0;
		m[3][2] = 0;
		m[3][3] = 1;

		return m;
	}

	private float[][] rotationMatrixZ(float angle, float px, float py, float pz){

		float m[][] = new float[4][4];

		m[0][0] = cos(angle);
		m[0][1] = -sin(angle);
		m[0][2] = 0;
		m[0][3] = px - m[0][0]*px - m[0][1]*py - m[0][2]*pz;

		m[1][0] = sin(angle);
		m[1][1] = cos(angle);
		m[1][2] = 0;
		m[1][3] = py - m[1][0]*px - m[1][1]*py - m[1][2]*pz;

		m[2][0] = 0;
		m[2][1] = 0;
		m[2][2] = 1;
		m[2][3] = pz - m[2][0]*px - m[2][1]*py - m[2][2]*pz;

		m[3][0] = 0;
		m[3][1] = 0;
		m[3][2] = 0;
		m[3][3] = 1;

		return m;
	}
	
	protected float cos(float angle){
		return (float)Math.cos(Math.toRadians(angle));
	}

	protected float sin(float angle){
		return (float)Math.sin(Math.toRadians(angle));
	}
	
}
