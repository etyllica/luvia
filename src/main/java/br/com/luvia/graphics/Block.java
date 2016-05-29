package br.com.luvia.graphics;

import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import java.util.ArrayList;
import java.util.List;

/**
 * Block used in grid
 */
public class Block extends GeometricForm {

	int version;//X measure
	//BoundingBox measures
	int rows = 1;//X measure
	int columns = 1;//Y measure
	int height = 1;//Z measure
	
	String[] models;
	
	List<BaseLight> lights = new ArrayList<BaseLight>();
	List<ModelInstance> instances = new ArrayList<ModelInstance>();
	
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
