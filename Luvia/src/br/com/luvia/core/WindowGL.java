package br.com.luvia.core;

import javax.media.opengl.GLAutoDrawable;

import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.loader.ApplicationLoader;
import br.com.etyllica.core.video.Grafico;
import br.com.etyllica.gui.Window;
import br.com.luvia.loader.TextureLoader;

public class WindowGL extends Window {
	
	private ApplicationGL application3D;
	
	private DefaultLoadApplicationGL load3D;
	
	public WindowGL(int x, int y, int w, int h){
		super(x,y,w,h);
		
		load3D = new DefaultLoadApplicationGL(x,y,w,h);
		application3D = load3D;
	}
	
	public ApplicationGL getApplication3D() {
		return application3D;
	}
	
	@Override
	public Application getApplication() {
		return (Application)application3D;
	}

	@Override
	public void setApplication(Application application) {
		setApplication3D((ApplicationGL)application3D);
	}
	
	public void setApplication3D(ApplicationGL application3D) {
		this.application = application3D;
		this.application3D = application3D;
		
		//TODO Based on Window
		clearComponents();
		
		add(application3D);
	}
		
	/*@Override
	public void changeApplication(){
		
		System.out.println("Window3D change Action");
				
		//TODO Change Application
		//changeApplication(drawable, application3D.getReturnApplication3D());
		
		//TextureLoader.getInstance().setContext(drawable.getContext());
		
	}
	
	public void changeApplication(GLAutoDrawable drawable){
		changeApplication(drawable, application3D.getReturnApplication3D());
	}
	
	public void changeApplication(GLAutoDrawable drawable, Application3D application3D){
		
		//TODO drawable = null
		
		if(drawable==null){
			System.out.println("Window3D - NULL");
		}
		
		System.out.println("Window3D changeApplication");
		
		anotherApplication = application3D;
		
		anotherApplication.setSessionMap(sessionMap);
		anotherApplication.init(drawable);
		anotherApplication.load();
		
		load3D.init(drawable);
		load3D.load();
		
		this.application3D = load3D;
		
		c = new ApplicationLoader(anotherApplication, load3D);
		
		new Thread(this).start();
		
	}

	@Override
	public void run() {
		
		c.run();
		
		System.out.println("Window 3D Carregou");
			
		this.application3D = anotherApplication;
		
		clearComponents();
		
		setApplication(anotherApplication);
		
	}*/
	
	public DefaultLoadApplicationGL getLoadApplication3D(){
		return load3D;
	}
		
	@Override
	public void draw(Grafico g) {
		this.application3D.draw(g);
	}
	
}