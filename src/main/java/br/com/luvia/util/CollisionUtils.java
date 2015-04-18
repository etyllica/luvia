package br.com.luvia.util;

import br.com.luvia.linear.Line;
import br.com.luvia.linear.Plane;
import br.com.luvia.linear.Vector3;

public class CollisionUtils {

  /**
   * Based on http://stackoverflow.com/a/21114992
   */
  public static boolean intersectRayWithSquare(Vector3 R1, Vector3 R2,
      Vector3 S1, Vector3 S2, Vector3 S3) {
    
    double error = 1e-6f;
    
    Vector3 dS21 = S2.sub(S1);
    Vector3 dS31 = S3.sub(S1);
    Vector3 n = dS21.cross(dS31);

    // 2.
    Vector3 dR = R1.sub(R2);

    double ndotdR = n.dot(dR);

    if (Math.abs(ndotdR) < error) { // Choose your tolerance
      return false;
    }

    double t = -n.dot(R1.sub(S1)) / ndotdR;
    Vector3 M = R1.add(dR.scale(t));

    // 3.
    Vector3 dMS1 = M.sub(S1);
    double u = dMS1.dot(dS21);
    double v = dMS1.dot(dS31);

    // 4.
    return (u >= 0.0f && u <= dS21.dot(dS21)
        && v >= 0.0f && v <= dS31.dot(dS31));
  }
  
  public static Vector3 intersectLinePlane(Line l, Plane p) {

    double t = p.getA()*l.getT().getX()+p.getB()*l.getT().getY()+p.getC()*l.getT().getZ();
    double i = p.getA()*l.getOrigin().getX()+p.getB()*l.getOrigin().getY()+p.getC()*l.getOrigin().getZ();
    
    t = (p.getD()-i)/t;
    
    double px = l.getOrigin().getX()+l.getT().getX()*t;
    double py = l.getOrigin().getY()+l.getT().getY()*t;
    double pz = l.getOrigin().getZ()+l.getT().getZ()*t;
    
    Vector3 point = new Vector3(px, py, pz);
    
    return point;
  }

}
