package br.com.luvia.util;

import com.badlogic.gdx.math.Vector3;

import br.com.etyllica.core.linear.Point3D;

public class VectorUtil {

	public static Vector3 pointToVector(Point3D p) {
		return new Vector3((float)p.getX(), (float)p.getY(), (float)p.getZ());
	}
	
}
