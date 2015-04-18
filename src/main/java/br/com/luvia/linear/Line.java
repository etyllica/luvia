package br.com.luvia.linear;

import br.com.etyllica.linear.Point3D;

public class Line {

  protected Vector3 origin;
  
  protected Vector3 t;
  
  public Line(double x, double xt, double y, double yt, double z, double zt) {
    super();
    origin = new Vector3(x,y,z);
    t = new Vector3(xt,yt,zt);
  }
  
  public Line(Point3D p, Point3D q) {
    super();
    
    this.origin = new Vector3(p);    
    this.t = new Vector3(q).sub(origin);    
  }

  public Vector3 getOrigin() {
    return origin;
  }

  public void setOrigin(Vector3 origin) {
    this.origin = origin;
  }

  public Vector3 getT() {
    return t;
  }

  public void setT(Vector3 t) {
    this.t = t;
  }  
  
}
