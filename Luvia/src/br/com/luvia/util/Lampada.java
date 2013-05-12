package br.com.luvia.util;

import br.com.luvia.linear.Ponto3D;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class Lampada extends Ponto3D{
	
	protected float intensidade;
	
	public Lampada(double x, double y, double z){
		super(x,y,z);
	}
	
	public float getIntensidade(){
		return intensidade;
	}

}
