package br.com.luvia.graphics;

import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import java.util.ArrayList;
import java.util.List;

public class Block extends GeometricForm {

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
	
}
