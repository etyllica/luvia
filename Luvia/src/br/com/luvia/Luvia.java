package br.com.luvia;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.swing.JFrame;

import com.jogamp.newt.opengl.GLWindow;

import br.com.luvia.core.ApplicationGL;
import br.com.luvia.core.GLCore;

public abstract class Luvia {

	private static final long serialVersionUID = -6060864375960874373L;

	private static final int UPDATE_DELAY = 40;    // Delay Between control updates
	private static final int TIME_UPDATE_INTERVAL = 80;    // Display refresh frames per second 

	private ScheduledExecutorService executor;

	protected int w = 640;
	protected int h = 480;

	protected GLCore luviaCore;
	
	protected JFrame frame;
	
	// Constructor
	public Luvia(int w, int h) {
		super();
		
		this.w = w;
		this.h = h;
		
		luviaCore = new GLCore(w,h);
		
		frame = createFrame(w, h);
		luviaCore.setComponent(frame);

		String s = Luvia.class.getResource("").toString();
		
		luviaCore.setUrl(s);
		
		setMainApplication(startApplication());

		frame.setVisible(true);
	}
		
	private JFrame createFrame(int w, int h) {
				
		JFrame frame = new JFrame();
				
		frame.setSize(w, h);
		
		frame.setUndecorated(false);

		frame.setTitle("Luvia - Window");
		
		frame.addWindowListener(new WindowAdapter() {
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
		
		frame.addKeyListener(luviaCore.getControl().getKeyboard());
		
		return frame;
	}
	
	protected void init() {
		//startThread();
		//TODO Update Methods
		executor = Executors.newScheduledThreadPool(1);
		
		executor.scheduleAtFixedRate(luviaCore, UPDATE_DELAY, UPDATE_DELAY, TimeUnit.MILLISECONDS);
		//executor.scheduleAtFixedRate(this, TIME_UPDATE_INTERVAL, TIME_UPDATE_INTERVAL, TimeUnit.MILLISECONDS);
		
		frame.setContentPane(luviaCore.getPanel());
		
		luviaCore.start();		
	}

	public abstract ApplicationGL startApplication();
	
	private void setMainApplication(ApplicationGL applicationGL) {

		luviaCore.setMainApplication3D(applicationGL);
		
	}

}
