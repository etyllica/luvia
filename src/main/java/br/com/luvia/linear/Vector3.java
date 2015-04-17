package br.com.luvia.linear;

import br.com.etyllica.linear.Point3D;

public class Vector3 extends Point3D {

  public Vector3(double x, double y, double z) {
    super(x,y,z);
  }

  public Vector3 add(Vector3 other) {
    return new Vector3(x + other.x, y + other.y, z + other.z);
  }

  public Vector3 sub(Vector3 other) {
    return new Vector3(x - other.x, y - other.y, z - other.z);
  }

  public Vector3 scale(double f) {
    return new Vector3(x * f, y * f, z * f);
  }

  public Vector3 cross(Vector3 other) {
    return new Vector3(y * other.z - z * other.y,
        z - other.x - x * other.z,
        x - other.y - y * other.x);
  }

  public double dot(Vector3 other) {
    return x * other.x + y * other.y + z * other.z;
  }
}
