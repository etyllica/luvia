package br.com.luvia.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.luvia.linear.Vector3;

public class CollisionUtilTest {

  private Vector3 s1,s2,s3;
  
  @Before
  public void setUp() {
    s1 = new Vector3(-1.0f, 1.0f, 0.0f);
    s2 = new Vector3( 1.0f, 1.0f, 0.0f);
    s3 = new Vector3(-1.0f,-1.0f, 0.0f);
  }
  
  @Test
  public void testCollisionRayWithPlane() {
    
    Vector3 r1 = new Vector3(0.0f, 0.0f, -1.0f);
    Vector3 r2 = new Vector3(0.0f, 0.0f,  1.0f);
    
    Assert.assertTrue(CollisionUtils.intersectRayWithSquare(r1, r2, s1, s2, s3));
  }
  
  public void testNonCollisionRayWithPlane() {
    Vector3 r1 = new Vector3(1.5f, 1.5f, -1.0f);
    Vector3 r2 = new Vector3(1.5f, 1.5f,  1.0f);

    Assert.assertFalse(CollisionUtils.intersectRayWithSquare(r1, r2, s1, s2, s3));
  }
  
}
