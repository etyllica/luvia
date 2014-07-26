package br.com.luvia.core;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.swing.JPanel;

import sound.MultimediaLoader;
import br.com.etyllica.core.InnerCore;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.loader.FontLoader;
import br.com.etyllica.core.loader.image.ImageLoader;
import br.com.luvia.core.glg2d.GLG2DCanvas;
import br.com.luvia.core.glg2d.GLGraphics2D;
import br.com.luvia.core.video.Graphics3D;
import br.com.luvia.loader.TextureLoader;
import br.com.luvia.loader.mesh.MeshLoader;

import com.jogamp.opengl.util.FPSAnimator;


public class GLCore extends InnerCore implements GLEventListener, Runnable {

	private static final int UPDATE_DELAY = 20;

	private static final int REFRESH_FPS = 20; // Display refresh frames per second
	
	private JPanel panel = new JPanel();

	private GLGraphics2D glGraphics;

	private Graphics3D graphic;

	private String url;

	private GLG2DCanvas canvas = new GLG2DCanvas();

	private FPSAnimator animator;  // Used to drive display()

	//TODO Most Important
	private WindowGL activeWindowGL;

	private ExecutorService loadExecutor;
	
	private final Font defaultFont = new Font("ARIAL", Font.PLAIN, 14);
	
	private final Color defaultColor = Color.BLACK;

	public GLCore(Component component, int w, int h) {
		super();
				
		glGraphics = new GLGraphics2D();

		graphic = new Graphics3D(w,h);

		activeWindowGL = new WindowGL(0,0,w,h);
				
		canvas.addMouseMotionListener(mouse);
		canvas.addMouseWheelListener(mouse);
		canvas.addMouseListener(mouse);

		panel.setSize(w, h);
		panel.setLayout(new BorderLayout());
		panel.add(canvas, BorderLayout.CENTER);

		canvas.getCanvas().addGLEventListener(this);

		animator = new FPSAnimator(REFRESH_FPS, true);
		animator.add(canvas.getCanvas());
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {

		//For Windows
		String s = url.replaceAll("%20"," ");

		this.url = s;

		//Update Loaders
		ImageLoader.getInstance().setUrl(s);
		
		FontLoader.getInstance().setUrl(s);
		
		MultimediaLoader.getInstance().setUrl(s);
		
		MeshLoader.getInstance().setUrl(s);
		
		TextureLoader.getInstance().setUrl(s);
		
	}

	public void start() {
		animator.start();
	}

	public void stop() {
		animator.stop();
	}

	//TODO Core must implement GLEventListener
	@Override
	public void init(GLAutoDrawable drawable) {

		resetGraphics(drawable);

		//TODO verify
		activeWindowGL.getLoadApplication3D().init(drawable);
		System.out.println("Init Application");

	}
	
	private void resetGraphics(GLAutoDrawable drawable) {
		glGraphics.setCanvas(drawable);

		glGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		glGraphics.setColor(defaultColor);
		glGraphics.setFont(defaultFont);
		
		graphic.setGraphics(glGraphics);
		
		//glGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		activeWindowGL.getApplication3D().dispose(drawable);
	}

	@Override
	public void display(final GLAutoDrawable drawable) {

		if(changeApp) {

			DefaultLoadApplicationGL load3D = activeWindowGL.getLoadApplication3D();

			load3D.init(drawable);
			load3D.load();

			anotherApplication3D.init(drawable);
			anotherApplication3D.load();

			loadExecutor = Executors.newSingleThreadExecutor();

			loadExecutor.execute(new Runnable() {

				@Override
				public void run() {

					activeWindowGL.clearComponents();

					activeWindowGL.setApplication3D(anotherApplication3D);
										
					//desktop.reload(anotherApplication3D);

				}
			});

			loadExecutor.shutdown();

			changeApp = false;

		}else{

			reshape(drawable, panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
			activeWindowGL.getApplication3D().display(drawable);
			
			resetGraphics(drawable);

			draw((Graphic)graphic);
		}

	}

	private ApplicationGL anotherApplication3D;

	private boolean changeApp = false;

	@Override
	protected void changeApplication() {

		setMainApplication3D(activeWindowGL.getApplication3D().getReturnApplication3D());		
	}

	public void setMainApplication3D(ApplicationGL application3D) {
		//this.mainApplication = application3D;

		anotherApplication3D = application3D;
		anotherApplication3D.setSessionMap(activeWindowGL.getSessionMap());

		addWindow(activeWindowGL);
				
		reload();

	}

	private void reload() {

		activeWindowGL.reload(anotherApplication3D);
		
		changeApp = true;
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		activeWindowGL.getApplication3D().reshape(drawable, x, y, width, height);
	}
	
	public void run() {
		
		update(System.currentTimeMillis());

	}

	public JPanel getPanel() {
		return panel;
	}
}
