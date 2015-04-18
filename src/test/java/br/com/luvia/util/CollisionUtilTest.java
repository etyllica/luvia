package br.com.luvia.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.luvia.linear.Line;
import br.com.luvia.linear.Plane;
import br.com.luvia.linear.Vector3;

public class CollisionUtilTest {

  private Vector3 s1,s2,s3;
  
  @Before
  public void setUp() {
    s1 = new Vector3(-1.0, 1.0, 0.0);
    s2 = new Vector3( 1.0, 1.0, 0.0);
    s3 = new Vector3(-1.0,-1.0, 0.0);
  }
  
  @Test
  public void testCollisionRayWithPlane() {
    
    Vector3 r1 = new Vector3(0.0, 0.0, -1.0);
    Vector3 r2 = new Vector3(0.0, 0.0,  1.0);
    
    Assert.assertTrue(CollisionUtils.intersectRayWithSquare(r1, r2, s1, s2, s3));
  }
  
  public void testNonCollisionRayWithPlane() {
    Vector3 r1 = new Vector3(1.5, 1.5, -1.0);
    Vector3 r2 = new Vector3(1.5, 1.5,  1.0);

    Assert.assertFalse(CollisionUtils.intersectRayWithSquare(r1, r2, s1, s2, s3));
  }
  
  /**
   * Tests based on:
   * http://ocw.mit.edu/courses/mathematics/18-02sc-multivariable-calculus-fall-2010/1.-vectors-and-matrices/part-c-parametric-equations-for-curves/session-16-intersection-of-a-line-and-a-plane/MIT18_02SC_we_9_comb.pdf
   */
  @Test
  public void testOnePointOfIntersection() {
    
    Plane p = new Plane(2, 1,-4, 4);
    Line l = new Line(0, 1, 2, 3, 0, 1);
    
    Vector3 point = CollisionUtils.intersectLinePlane(l, p);
    
    Assert.assertEquals(2, point.getX(), 0);
    Assert.assertEquals(8, point.getY(), 0);
    Assert.assertEquals(2, point.getZ(), 0);    
  }
  
  /**
   * There is no point of intersection (line and plane are parallel)
   */
  @Test
  public void testNoPointOfIntersection() {
    
    Plane p = new Plane(2, 1,-4, 4);
    Line l = new Line(1, 1, 4, 2, 0, 1);
    
    Vector3 point = CollisionUtils.intersectLinePlane(l, p);
    
    Assert.assertTrue(Double.isInfinite(point.getX()));
    Assert.assertTrue(Double.isInfinite(point.getY()));
    Assert.assertTrue(Double.isInfinite(point.getZ()));    
  }
  
  /**
   * All the line is in the plane 
   */
  @Test
  public void testAllPointsOfIntersection() {
    
    Plane p = new Plane(2, 1,-4, 4);
    Line l = new Line(0, 1, 4, 2, 0, 1);
    
    Vector3 point = CollisionUtils.intersectLinePlane(l, p);
    
    Assert.assertTrue(Double.isNaN(point.getX()));
    Assert.assertTrue(Double.isNaN(point.getY()));
    Assert.assertTrue(Double.isNaN(point.getZ()));
  }
  
}
