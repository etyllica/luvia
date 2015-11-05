/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package br.com.luvia.linear;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/** Encapsulates an oriented bounding box represented by a minimum and a maximum Vector. Additionally you can query for the
 * bounding box's center, dimensions and corner points.
 *
 * @author badlogicgames@gmail.com */
public class OrientedBoundingBox extends BoundingBox {
	private static final long serialVersionUID = -563414328395949544L;
		
	public final Vector3 orientation = new Vector3();
	
	public void rotateByYaw(float yaw) {
		Matrix4 matrix = new Matrix4();
		
		Vector3 inv = new Vector3(cnt).scl(-1);
		matrix.translate(inv);
		matrix.rotate(new Vector3(0, 1, 0), yaw);
				
		min.mul(matrix);
		max.mul(matrix);
		
		min.add(cnt);
		max.add(cnt);
	}

}
