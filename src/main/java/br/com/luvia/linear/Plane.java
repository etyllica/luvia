package br.com.luvia.linear;

import br.com.etyllica.linear.Point3D;

/**
 * A plane can be defined as ax + by + cz + d = 0
 */
public class Plane {
  double a = 0;
  double b = 0;
  double c = 0;
  double d = 0;
  
  public Plane(double d) {
    super();
    this.d = d; 
  }
  
  public Plane(double a, double b, double c) {
    super();
    this.a = a;
    this.b = b;
    this.c = c;
  }
  
  public Plane(double a, double b, double c, double d) {
    super();
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }
  
  /**
   * Based on https://www.youtube.com/watch?v=0qYJfKG-3l8
   * @param p arbitrary point
   * @param q arbitrary point
   * @param r arbitrary point
   */
  public Plane(Point3D p, Point3D q, Point3D r) {
    super();
    
    Vector3 vp = new Vector3(p);
    Vector3 pq = new Vector3(q).sub(vp);
    Vector3 pr = new Vector3(r).sub(vp);
    
    a = pq.getY()*pr.getZ()-pq.getZ()*pr.getY();
    b = pq.getX()*pr.getZ()-pq.getZ()*pr.getX();
    c = pq.getX()*pr.getY()-pq.getY()*pr.getX();
    d = -(a*p.getX()+b*p.getY()+c*p.getZ());    
  }

  public double getA() {
    return a;
  }

  public void setA(double a) {
    this.a = a;
  }

  public double getB() {
    return b;
  }

  public void setB(double b) {
    this.b = b;
  }

  public double getC() {
    return c;
  }

  public void setC(double c) {
    this.c = c;
  }

  public double getD() {
    return d;
  }

  public void setD(double d) {
    this.d = d;
  }
  
}
