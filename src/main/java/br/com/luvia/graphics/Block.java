package br.com.luvia.graphics;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import br.com.abby.linear.Shape;
import br.com.luvia.core.graphics.GLDrawable;
import br.com.luvia.core.graphics.Graphics3D;

import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * Block used in grid
 */
public class Block extends Shape implements GLDrawable {
	protected String id;
	protected int version;
	//BoundingBox measures
	protected int rows = 1;//X measure
	protected int columns = 1;//Y measure
	protected int height = 1;//Z measure
	
	String[] models;
	
	protected List<BaseLight> lights = new ArrayList<BaseLight>();
	protected List<ModelInstance> instances = new ArrayList<ModelInstance>();
	public BoundingBox bounds;
	
	public Block() {
		super();
	}
	
	public Block(int rows, int columns, int height) {
		super();
		this.rows = rows;
		this.columns = columns;
		this.height = height;
		
		updateBounds();
	}

	protected void updateBounds() {
		bounds = new BoundingBox(new Vector3(-rows/2, -height/2, -columns/2), new Vector3(rows/2, height/2, columns/2));
	}

	public List<BaseLight> getLights() {
		return lights;
	}

	public List<ModelInstance> getInstances() {
		return instances;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public void draw(Graphics3D g) {
		GL2 gl = g.getGL2();
		
		gl.glPushMatrix();
		gl.glMultMatrixf(transform.val, 0);
		for (ModelInstance instance : instances) {
			instance.draw(g);
		}
		gl.glPopMatrix();
	}

}
