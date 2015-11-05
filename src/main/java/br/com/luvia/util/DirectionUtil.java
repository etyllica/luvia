package br.com.luvia.util;

import com.badlogic.gdx.math.Vector3;

public class DirectionUtil {

	/**
	 * Angles in degree
	 * @param yaw
	 * @return directional vector
	 */
	public static Vector3 direction(double yaw) {
		double radians = Math.toRadians(yaw);
		return new Vector3((float)Math.cos(radians), 0, (float)Math.sin(radians));
	}
	
	/**
	 * Angles in degree
	 * @param yaw
	 * @param pitch
	 * @return directional vector
	 */
	public static Vector3 direction(double yaw, double pitch) {
		double radYaw = Math.toRadians(yaw);
		double radPitch = Math.toRadians(pitch);

		float x = (float)(Math.sin(radPitch) * Math.cos(radYaw));
		float y = (float)(Math.sin(radPitch) * Math.sin(radYaw));
		float z = (float)(Math.cos(radPitch));

		return new Vector3(x,y,z);
	}

	/**
	 * Angles in degree
	 * @param yaw
	 * @param pitch
	 * @param length
	 * @return directional vector
	 */
	public static Vector3 direction(double yaw, double pitch, double length) {
		double radYaw = Math.toRadians(yaw);
		double radPitch = Math.toRadians(pitch);

		float x = (float)(length * Math.sin(radPitch) * Math.cos(radYaw));
		float y = (float)(length * Math.sin(radPitch) * Math.sin(radYaw));
		float z = (float)(length * Math.cos(radPitch));

		return new Vector3(x,y,z);
	}


}
