package br.com.luvia.graphics;

import br.com.abby.linear.Shape;

import com.badlogic.gdx.graphics.g3d.environment.BaseLight;

import java.util.ArrayList;
import java.util.List;

/**
 * Block used in grid
 */
public class Block extends Shape {
	protected int version;
	//BoundingBox measures
	protected int rows = 1;//X measure
	protected int columns = 1;//Y measure
	protected int height = 1;//Z measure
	
	String[] models;
	
	protected List<BaseLight> lights = new ArrayList<BaseLight>();
	protected List<ModelInstance> instances = new ArrayList<ModelInstance>();
	
	public Block() {
		super();
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
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
}
