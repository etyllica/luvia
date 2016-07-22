package br.com.luvia.core.animation;

import br.com.abby.linear.Shape;

import com.badlogic.gdx.math.Vector3;

public class Move extends ShapeAnimation {
	
	protected Vector3 start = new Vector3();
	protected Vector3 end = new Vector3();
		
	public Move(Shape target) {
		super(target);
		this.start.set(target.position);
	}
	
	public Move(Shape target, long time) {
		this(target);
		this.duration = time;
	}
	
	@Override
	public void calculate(double x) {
		double valueX = interpolator.factor(start.x, end.x, x);
		double valueY = interpolator.factor(start.y, end.y, x);
		double valueZ = interpolator.factor(start.z, end.z, x);
		
		target.setPosition((float) valueX, (float) valueY, (float) valueZ);
	}

	public Move from(Vector3 start) {
		this.start.set(start);
		return this;
	}
	
	public Move from(float x, float y, float z) {
		start.x = x;
		start.y = y;
		start.z = z;
		
		return this;
	}
	
	public Move to(Vector3 end) {
		this.end.set(end);
		return this;
	}

	public Move to(float x, float y, float z) {
		end.x = x;
		end.y = y;
		end.z = z;
		
		return this;
	}
	
}

