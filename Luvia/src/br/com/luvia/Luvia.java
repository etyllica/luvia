package br.com.luvia;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import br.com.etyllica.core.application.Application;
import br.com.luvia.core.ApplicationGL;
import br.com.luvia.core.LuviaCore;
import br.com.luvia.loader.TextureLoader;

public class Luvia extends JFrame {

	private static final long serialVersionUID = -6060864375960874373L;

	private static final int UPDATE_DELAY = 80;    // Delay Between control updates
	private static final int TIME_UPDATE_INTERVAL = 80;    // Display refresh frames per second 

	private ScheduledExecutorService executor;

	protected int w = 640;
	protected int h = 480;

	protected LuviaCore luviaCore;

	// Constructor
	public Luvia(int w, int h) {

		this.w = w;
		this.h = h;

		setSize(w, h);

		String s = getClass().getResource("").toString();

		luviaCore = new LuviaCore(w,h);

		luviaCore.setUrl(s);
		TextureLoader.getInstance().setUrl(s);

		addWindowListener(new WindowAdapter() {
			@Override 
			public void windowClosing(WindowEvent e) {
				// Use a dedicate thread to run the stop() to ensure that the
				// animator stops before program exits.
				new Thread() {
					@Override 
					public void run() {
						luviaCore.stop(); // stop the animator loop
						System.exit(0);
					}
				}.start();
			}
		});


		//addKeyListener(KeyListenerWrapper.init(Core.getInstance().getControl().getTeclado(),true));
		//addKeyListener(KeyListenerWrapper.init(Core.getInstance().getControl().getTeclado(),false));
		
		addKeyListener(luviaCore.getControl().getTeclado());

		setUndecorated(true);

		setTitle("Luvia - HighPerformance");


		startGame();

		setVisible(true);
		
	}
	
	protected void init(){
		//startThread();
		//TODO Update Methods
		executor = Executors.newScheduledThreadPool(2);
		
		executor.scheduleAtFixedRate(luviaCore, UPDATE_DELAY, UPDATE_DELAY, TimeUnit.MILLISECONDS);
		//executor.scheduleAtFixedRate(this, TIME_UPDATE_INTERVAL, TIME_UPDATE_INTERVAL, TimeUnit.MILLISECONDS);
		
		setContentPane(luviaCore.getPanel());
		
		luviaCore.start();
		
	}

	public void startGame(){

	}
	
	public void setMainApplication(Application application3D){

		//TODO Make adaptor
		
		System.err.println("Make Adaptor from Application3D to Application2D");
		
		//luviaCore.setMainApplication3D(application3D);

	}

	public void setMainApplication(ApplicationGL application3D){

		luviaCore.setMainApplication3D(application3D);

	}

}
