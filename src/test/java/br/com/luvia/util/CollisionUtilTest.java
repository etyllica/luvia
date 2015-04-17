package br.com.luvia.util;

import org.junit.Assert;
import org.junit.Test;

import br.com.luvia.linear.Vector3;

public class CollisionUtilTest {

  @Test
  public testCollisionRayWithPlane() {
    
    Vector3 R1 = new Vector3(0.0f, 0.0f, -1.0f);
    Vector3 R2 = new Vector3(0.0f, 0.0f,  1.0f);

    Vector3 S1 = new Vector3(-1.0f, 1.0f, 0.0f);
    Vector3 S2 = new Vector3( 1.0f, 1.0f, 0.0f);
    Vector3 S3 = new Vector3(-1.0f,-1.0f, 0.0f);
    
    boolean b = intersectRayWithSquare(R1, R2, S1, S2, S3);
    assert b;

    R1 = new Vector3(1.5f, 1.5f, -1.0f);
    R2 = new Vector3(1.5f, 1.5f,  1.0f);

    Assert.assertFalse(CollisionUtils.intersectRayWithSquare(R1, R2, S1, S2, S3));
    assert !b;    
    
  }
  
}
