package br.com.luvia.util;

import br.com.luvia.linear.Ponto3D;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class CameraGL extends Ponto3D{

	private Ponto3D target;
	
	public CameraGL(double x, double y, double z){
		super(x, y, z);
		target = new Ponto3D(0,0,0);
	}
	
	public Ponto3D getTarget(){
		return target;
	}
	
	public void setTarget(Ponto3D target){
		this.target = target;
	}
	
	public void setTarget(double x, double y, double z){
		target.setCoordenadas(x, y, z);
	}
	
	
}
