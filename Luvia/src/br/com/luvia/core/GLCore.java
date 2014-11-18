package br.com.luvia.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.abby.loader.MeshLoader;
import br.com.etyllica.core.InnerCore;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.loader.FontLoader;
import br.com.etyllica.core.loader.Loader;
import br.com.etyllica.core.loader.image.ImageLoader;
import br.com.etyllica.util.io.IOHandler;
import br.com.luvia.core.glg2d.GLG2DPanel;
import br.com.luvia.core.glg2d.GLGraphics2D;
import br.com.luvia.core.video.Graphics3D;
import br.com.luvia.loader.TextureLoader;

import com.jogamp.opengl.util.FPSAnimator;


public class GLCore extends InnerCore implements GLEventListener, Runnable {

	private static final int UPDATE_DELAY = 20;

	private static final int REFRESH_FPS = 20; // Display refresh frames per second

	private GLGraphics2D glGraphics;

	private Graphics3D graphic;

	private String url;

	private GLG2DPanel canvas = new GLG2DPanel();

	private FPSAnimator animator;  // Used to drive display()

	//TODO Most Important
	private WindowGL activeWindowGL;

	private ExecutorService loadExecutor;

	private JFrame component;

	private int oldW = 0;
	private int oldH = 0;

	private ApplicationGL anotherApplication3D;

	private boolean changeApp = false;

	public JPanel glass;
	
	private Set<Loader> loaders = new HashSet<Loader>();

	public GLCore(int w, int h) {
		super();

		activeWindowGL = new WindowGL(0, 0, w, h);

		glGraphics = new GLGraphics2D();

		graphic = new Graphics3D(w,h);

		canvas.addMouseMotionListener(mouse);
		canvas.addMouseWheelListener(mouse);
		canvas.addMouseListener(mouse);

		canvas.getCanvas().addGLEventListener(this);			

		animator = new FPSAnimator(REFRESH_FPS, true);
		animator.add(canvas.getCanvas());
		
		initLoaders();
	}
	
	private void initLoaders() {
		loaders.add(ImageLoader.getInstance());
		loaders.add(FontLoader.getInstance());
		loaders.add(MeshLoader.getInstance());
		loaders.add(TextureLoader.getInstance());
	}

	public void setComponent(JFrame frame) {
		this.component = frame;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {

		String s = IOHandler.fixPath(url);

		this.url = s;
		
		initDefault();
	}
	
	public void initDefault() {

		for(Loader loader:loaders) {
			loader.setUrl(url);
			loader.start();
		}

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

		graphic.setGraphics(glGraphics);

		initGraphics(glGraphics);
	}

	private final Font defaultFont = new Font("ARIAL", Font.PLAIN, 14);

	private final Color defaultColor = Color.BLACK;

	private void initGraphics(GLGraphics2D graphics) {
		graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		graphic.setColor(defaultColor);
		graphic.setFont(defaultFont);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		activeWindowGL.getApplication3D().dispose(drawable);
	}

	@Override
	public void display(final GLAutoDrawable drawable) {

		if(changeApp) {

			changeApplication(drawable);

		} else {

			updateSuperEvents(superEvent);

			drawActiveWindow(drawable);
		}

	}

	private void updateSuperEvents(GUIEvent event) {

		if(event == GUIEvent.ENABLE_FULL_SCREEN) {

			this.oldW = component.getWidth();
			this.oldH = component.getHeight();

			component.setExtendedState(JFrame.MAXIMIZED_BOTH);

		} else if(event == GUIEvent.DISABLE_FULL_SCREEN) {

			component.setExtendedState(JFrame.NORMAL);
			component.setSize(oldW, oldH);			
		}

	}

	private void changeApplication(GLAutoDrawable drawable) {
		
		DefaultLoadApplicationGL load3D = activeWindowGL.getLoadApplication3D();

		load3D.init(drawable);
		load3D.load();

		anotherApplication3D.init(drawable);
		anotherApplication3D.load();

		loadExecutor = Executors.newSingleThreadExecutor();

		loadExecutor.execute(new Runnable() {

			@Override
			public void run() {

				activeWindowGL.setApplication3D(anotherApplication3D);

				//desktop.reload(anotherApplication3D);

			}
		});

		loadExecutor.shutdown();

		changeApp = false;

	}

	private void drawActiveWindow(GLAutoDrawable drawable) {
		
		//Pre Drawing
		resetGraphics(drawable);
		preDisplay(drawable, graphic);

		//Display 3D
		reshape(drawable, canvas.getX(), canvas.getY(), canvas.getWidth(), canvas.getHeight());
		activeWindowGL.getApplication3D().display(drawable);

		//Post Drawing
		resetGraphics(drawable);
		draw(graphic);
	}

	private void preDisplay(GLAutoDrawable drawable, Graphic g) {

		if(!canDraw())
			return;

		activeWindowGL.getApplication3D().preDisplay(drawable, g);
	}

	@Override
	protected void changeApplication() {

		ApplicationGL nextApplication = (ApplicationGL)activeWindowGL.getApplication3D().getNextApplication();
		
		setMainApplication3D(nextApplication);
	}

	public void setMainApplication3D(ApplicationGL application3D) {

		anotherApplication3D = application3D;
		anotherApplication3D.setSession(activeWindowGL.getSession());

		replaceWindow(activeWindowGL);

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

	public JComponent getPanel() {
		return canvas;
	}

}
