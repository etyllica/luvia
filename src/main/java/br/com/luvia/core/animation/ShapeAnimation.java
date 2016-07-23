package br.com.luvia.core.animation;

import br.com.abby.linear.Shape;
import br.com.etyllica.core.animation.AnimationHandler;
import br.com.etyllica.core.animation.script.AnimationScript;
import br.com.etyllica.core.interpolation.Interpolator;

public class ShapeAnimation extends AnimationScript {

	protected Shape target;
	protected ShapeAnimation root = this;

	public ShapeAnimation(long time) {
		super(time);
	}

	public ShapeAnimation(Shape target) {
		super();
		setTarget(target);
	}

	public ShapeAnimation(Shape target, long time) {
		this(target);
		this.duration = time;
	}
	
	public ShapeAnimation(Shape target, long time, long delay) {
		this(target);
		this.duration = time;
		this.delay = delay;
	}

	public ShapeAnimation(long delay, long time) {
		super(delay, time);
	}
	
	public static ShapeAnimation animate(Shape target) {
		return new ShapeAnimation(target);
	}
	
	public static ShapeAnimation animate(Shape target, long time) {
		return new ShapeAnimation(target, time);
	}
	
	public static ShapeAnimation animate(Shape target, long time, long delay) {
		return new ShapeAnimation(target, time, delay);
	}

	public Shape getTarget() {
		return target;
	}

	public void setTarget(Shape target) {
		this.target = target;
	}

	@Override
	public void calculate(double factor) {
		// TODO Auto-generated method stub
	}

	public ShapeAnimation startAt(long delayValue) {
		this.delay = delayValue;
		return this;
	}

	public ShapeAnimation during(long time) {
		this.duration = time;
		return this;
	}

	public ShapeAnimation interpolate(Interpolator interpolator) {
		this.interpolator = interpolator;
		return this;
	}

	public ShapeAnimation twice() {
		return loop(2);
	}
	
	public ShapeAnimation loop(int loop) {
		this.loop = loop;
		return this;
	}

	public Move move(long time) {
		Move script = new Move(target, time);
		concatenate(script);

		return script;
	}

	public Move move() {
		Move script = new Move(target);
		concatenate(script);

		return script;
	}
	
	public ShapeAnimation concatenate(ShapeAnimation script) {
		addNext(script);
		setupRoot(script);
		return script;
	}
	
	private void setupRoot(ShapeAnimation script) {
		script.root = getRoot();
	}
	
	private ShapeAnimation getRoot() {
		if(root!=null) {
			return this;
		} else {
			return root;
		}
	}

	public ShapeAnimation and() {
		return root;
	}
	
	public ShapeAnimation and(ShapeAnimation script) {
		root.addNext(script);
		return this;
	}
	
	public ShapeAnimation then(ShapeAnimation script) {
		addNext(script);
		return this;
	}

	public ShapeAnimation start() {
		root.startChildren();
		return this;
	}
	
	private void startChildren() {
		onStart();

		if(next != null) {
			for(AnimationScript s: next) {
				AnimationHandler.getInstance().add(s);
			}
		}
	}

	public void onStart() { }
	
}
