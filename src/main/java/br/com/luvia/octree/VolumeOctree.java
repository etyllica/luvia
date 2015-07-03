/* 
 * Copyright (c) 2002 Shaven Puppy Ltd
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are 
 * met:
 * 
 * * Redistributions of source code must retain the above copyright 
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'Shaven Puppy' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package br.com.luvia.octree;

import java.util.HashSet;
import java.util.Set;

import br.com.abby.linear.BoundingBox3D;
import br.com.etyllica.linear.Point3D;

/**
 * Octree highly based on Shaven Puppy's Octree, found at: 
 * http://spgl.cvs.sourceforge.net/viewvc/spgl/SPGLExtra/src/com/shavenpuppy/jglib/geometry/storage/OctTree.java?revision=1.1&view=markup
 * 
 * Note: Luvia's orientation is Y-up
 */
public class VolumeOctree implements Octree {
	
	private OctreeNode root;
	
	private double minVolume = 0.5;

	public VolumeOctree(BoundingBox3D box) {
		super();
		root = new OctreeNode(box);
	}

	public OctreeNode getRoot() {
		return root;
	}
	
	public void add(Point3D point) {
		add(root, point);
	}
	
	public void add(OctreeNode node, Point3D point) {
		if(node.box.getVolume() >= minVolume) {
			int c = calculateNode(node.box, point);
			addToOctant(node, point, c);
		} else {
			node.geometry.add(point);
		}
	}
	
	private void addToOctant(OctreeNode node, Point3D point, int c) {
		switch (c) {
		//Lower level octants
		case 1:
			addPointToSubNode(BELOW_LEFT_LOWER, node, point);
			break;
		case 2:
			addPointToSubNode(BELOW_LEFT_UPPER, node, point);
			break;
		case 4:
			addPointToSubNode(BELOW_RIGHT_LOWER, node, point);
			break;
		case 8:
			addPointToSubNode(BELOW_RIGHT_UPPER, node, point);
			break;
			//Upper level octants
		case 16:
			addPointToSubNode(ABOVE_LEFT_LOWER, node, point);
			break;
		case 32:
			addPointToSubNode(ABOVE_LEFT_UPPER, node, point);
			break;
		case 64:
			addPointToSubNode(ABOVE_RIGHT_LOWER, node, point);
			break;
		case 128:
			addPointToSubNode(ABOVE_RIGHT_UPPER, node, point);
			break;

		default:
			node.geometry.add(point);
			break;
		}
	}

	private void addPointToSubNode(int index, OctreeNode node, Point3D point) {
		if(node.children[index] == null) {
			node.children[index] = new OctreeNode(subBox(node, index));
		}

		add(node.children[index], point);
	}

	private BoundingBox3D subBox(OctreeNode node, int index) {
				
		Point3D minPoint = new Point3D(node.box.getMinPoint());
		Point3D maxPoint = new Point3D(node.box.getMaxPoint());
		Point3D center = node.box.getCenter();
		
		double halfSize = (maxPoint.getX()-minPoint.getX())/2;
		
		if(index < 4) {
			maxPoint = center;
		} else {
			minPoint = center;
		}
		
		if(index == BELOW_LEFT_LOWER) {
			//maxPoint = center;
		} else if(index == BELOW_LEFT_UPPER) {
			minPoint.offsetZ(halfSize);
			maxPoint.offsetZ(halfSize);
		} else if(index == BELOW_RIGHT_LOWER) {
			minPoint.offsetX(halfSize);
			maxPoint.offsetX(halfSize);
		} else if(index == BELOW_RIGHT_UPPER) {
			minPoint.offsetX(halfSize);
			maxPoint.offsetX(halfSize);
			minPoint.offsetZ(halfSize);
			maxPoint.offsetZ(halfSize);
		}
		//Above Octants
		else if(index == ABOVE_LEFT_LOWER) {
			minPoint.offsetZ(-halfSize);
			maxPoint.offsetZ(-halfSize);
			minPoint.offsetX(-halfSize);
			maxPoint.offsetX(-halfSize);
		} else if(index == ABOVE_LEFT_UPPER) {
			minPoint.offsetX(-halfSize);
			maxPoint.offsetX(-halfSize);
		} else if(index == ABOVE_RIGHT_LOWER) {
			minPoint.offsetZ(-halfSize);
			maxPoint.offsetZ(-halfSize);
		} else if(index == ABOVE_RIGHT_UPPER) {
			
		}
		
		BoundingBox3D box = new BoundingBox3D(minPoint, maxPoint);
		
		return box;
	}
	
	private int calculateNode(BoundingBox3D box, Point3D point) {
		int c = 0;

		Point3D center = box.getCenter();

		double qx = point.getX();
		double qy = point.getY();
		double qz = point.getZ();

		if (qz < center.getY()) {
			if (qx < center.getX()) {
				if (qy < center.getZ()) {
					c |= 1; // Below Bottom left
				} else if (qy > center.getY()) {
					c |= 2; // Below Top left
				}
			} else if (qx > center.getX()) {
				if (qy < center.getY()) {
					c |= 4; // Bottom right
				} else if (qy > center.getY()) {
					c |= 8; // Below Top right
				}
			}
		} else if (qz > center.getY()) {
			if (qx < center.getX()) {
				if (qy < center.getZ()) {
					c |= 16; // Above Bottom left
				} else if (qy > center.getZ()) {
					c |= 32; // Above Top left
				}
			} else if (qx > center.getX()) {
				if (qy < center.getZ()) {
					c |= 64; // Above Bottom right
				} else if (qy > center.getZ()) {
					c |= 128; // Above Top right
				}
			}
		}
		
		return c;
	}

	@Override
	public Set<OctreeNode> getNodes(BoundingBox3D box) {
		//Not implemented yet
		return new HashSet<OctreeNode>();
	}

	public double getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(double minVolume) {
		this.minVolume = minVolume;
	}
	
}