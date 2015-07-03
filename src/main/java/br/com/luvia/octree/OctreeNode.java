package br.com.luvia.octree;

import java.util.ArrayList;
import java.util.List;

import br.com.abby.linear.BoundingBox3D;
import br.com.etyllica.linear.Point3D;

public class OctreeNode {

	protected BoundingBox3D box;

	protected OctreeNode[] children;

	protected List<Point3D> geometry = new ArrayList<Point3D>();

	public OctreeNode(BoundingBox3D box) {
		super();
		this.box = box;

		children = new OctreeNode[8];
	}

	public OctreeNode[] getChildren() {
		return children;
	}
	
}

