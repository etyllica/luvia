package br.com.luvia;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import br.com.etyllica.util.PathHelper;
import br.com.luvia.core.GLCore;
import br.com.luvia.core.context.ApplicationGL;

public abstract class Luvia {

	private static final long serialVersionUID = -6060864375960874373L;

	private static final int UPDATE_DELAY = 10;    // Delay Between control updates
	private static final int TIME_UPDATE_INTERVAL = 80;    // Display refresh frames per second 

	private ScheduledFuture<?> future;
	
	private ScheduledExecutorService executor;

	protected int w = 640;
	protected int h = 480;

	protected GLCore luviaCore;
	
	protected JFrame frame;
	
	protected String title = "Luvia - Window";
	
	// Constructor
	public Luvia(int w, int h) {
		super();
		
		this.w = w;
		this.h = h;
		
		luviaCore = new GLCore(w,h);
		luviaCore.initMonitors(w, h);
		
		frame = createFrame(w, h);
		luviaCore.setComponent(frame);

		String path = PathHelper.currentDirectory();
		setPath(path);
		
		setMainApplication(startApplication());
		
		frame.requestFocus();
		frame.setVisible(true);
	}
	
	protected void setPath(String path) {
		luviaCore.setPath(path);
	}
	
	protected JFrame createFrame(int w, int h) {
				
		JFrame frame = new JFrame();
				
		frame.setSize(w, h);
		
		frame.setUndecorated(false);

		frame.setTitle(title);
		
		frame.addWindowListener(buildWindowAdapter());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addKeyListener((KeyListener) luviaCore.getKeyboard());
		
		luviaCore.setComponent(frame);
		
		//frame.setLocation(p);
		luviaCore.moveToCenter();
		
		return frame;
	}

	private WindowAdapter buildWindowAdapter() {
		return new WindowAdapter() {
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
			
		};
	}
	
	public void hideCursor() {
		luviaCore.hideCursor();
	}
	
	public void setTitle(String title) {
		frame.setTitle(title);
	}
	
	protected void init() {
		//startThread();
		//TODO Update Methods
		executor = Executors.newSingleThreadScheduledExecutor();
		
		future = executor.scheduleAtFixedRate(luviaCore, UPDATE_DELAY, UPDATE_DELAY, TimeUnit.MILLISECONDS);
		
		frame.add(luviaCore.getPanel());
						
		luviaCore.start();
	}
	
	protected void centerCursor() {
		try {
			Robot robot = new Robot();
			Rectangle bounds =  frame.getBounds();			
			robot.mouseMove(bounds.x+bounds.width/2, bounds.y+bounds.height/2);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	

	protected void initialSetup(String path) {
		String directory = PathHelper.currentDirectory()+path;
		luviaCore.setPath(directory);
		luviaCore.initDefault();
	}
	
	public abstract ApplicationGL startApplication();
	
	private void setMainApplication(ApplicationGL applicationGL) {
		luviaCore.setMainApplication3D(applicationGL);
	}
	
}
