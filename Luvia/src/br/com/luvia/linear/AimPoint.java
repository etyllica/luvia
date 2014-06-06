package br.com.luvia.linear;

public class AimPoint extends Point3D {

	protected double angleX = 0;
	
	protected double angleY = 0;
	
	protected double angleZ = 0;
	
	public AimPoint() {
		super();
	}
	
	public AimPoint(double x, double y, double z) {
		super(x, y, z);
	}

	public double getAngleX() {
		return angleX;
	}

	public void setAngleX(double angleX) {
		this.angleX = angleX;
	}
	
	public void setOffsetAngleX(double offsetAngleX) {
		this.angleX += offsetAngleX;
	}

	public double getAngleY() {
		return angleY;
	}

	public void setAngleY(double angleY) {
		this.angleY += angleY;
	}
	
	public void setOffsetAngleY(double offsetAngleY) {
		this.angleY += offsetAngleY;
	}

	public double getAngleZ() {
		return angleZ;
	}

	public void setAngleZ(double angleZ) {
		this.angleZ = angleZ;
	}
	
	public void setOffsetAngleZ(double offsetAngleZ) {
		this.angleZ += offsetAngleZ;
	}
	
}
