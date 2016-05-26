package br.com.luvia.core.context;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.context.Context;
import br.com.etyllica.core.graphics.Graphics;
import br.com.luvia.core.G3DEventListener;

public abstract class ApplicationWrapper extends ApplicationGL implements G3DEventListener {
	
	protected Application application;
	
	public ApplicationWrapper(Application application) {
		super(application.getX(), application.getY(), application.getW(), application.getH());
		this.application = application;
		init();
	}
	
	private void init() {
		nextApplication = this;
		clearBeforeDraw = true;
	}
	
	@Override
	public void draw(Graphics g) {
		application.draw(g);
	}
	
	@Override
	public Context getNextApplication() {
		return application.getNextApplication();
	}
}
