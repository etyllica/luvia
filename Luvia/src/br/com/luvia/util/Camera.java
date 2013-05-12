package br.com.luvia.util;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import br.com.luvia.linear.Ponto3D;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class Camera extends Ponto3D{

	private Ponto3D alvo;
	
	public Camera(double x, double y, double z){
		super(x, y, z);
		alvo = new Ponto3D(0,0,0);
	}
	
	public Ponto3D getTarget(){
		return alvo;
	}
	
	public void setTarget(Ponto3D target){
		this.alvo = target;
	}
	
	public void setTarget(double x, double y, double z){
		alvo.setCoordenadas(x, y, z);
	}
	
	
}
