package br.com.luvia.core;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JPanel;

import org.jogamp.glg2d.GLGraphics2D;

import br.com.etyllica.core.Core;
import br.com.etyllica.core.loader.FontLoader;
import br.com.etyllica.core.loader.ImageLoader;
import br.com.etyllica.core.loader.MultimediaLoader;
import br.com.etyllica.core.video.Grafico;
import br.com.luvia.core.video.Graphics3D;
import br.com.luvia.loader.TextureLoader;
import br.com.luvia.loader.mesh.MeshLoader;

import com.jogamp.opengl.util.FPSAnimator;


public class LuviaCore extends Core implements GLEventListener, Runnable{

	private static final int UPDATE_DELAY = 10;    // Display refresh frames per second

	private static final int REFRESH_FPS = 60;    // Display refresh frames per second
	
	private JPanel panel = new JPanel();

	private GLGraphics2D glGraphics;

	private Graphics3D grafico;

	private String url;

	private GLCanvas canvas;

	private FPSAnimator animator;  // Used to drive display()

	//TODO Most Important
	private Window3D desktop;

	private ExecutorService loadExecutor;

	public LuviaCore(int w, int h){
		super();

		canvas = new GLCanvas();

		glGraphics = new GLGraphics2D();

		grafico = new Graphics3D(w,h);

		desktop = new Window3D(0,0,w,h);

		add(desktop);
		
		canvas.addMouseMotionListener(mouse);
		canvas.addMouseWheelListener(mouse);
		canvas.addMouseListener(mouse);
		//canvas.addKeyListener(core.getControl().getTeclado());

		panel.setSize(w, h);
		panel.setLayout(new BorderLayout());
		panel.add(canvas, BorderLayout.CENTER);

		canvas.addGLEventListener(this);

		animator = new FPSAnimator(canvas, REFRESH_FPS, true);		

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {

		//For Windows
		String s = url.replaceAll("%20"," ");

		this.url = s;

		ImageLoader.getInstance().setUrl(s);
		FontLoader.getInstance().setUrl(s);
		MultimediaLoader.getInstance().setUrl(s);
		MeshLoader.getInstance().setUrl(s);
		TextureLoader.getInstance().setUrl(s);
	}

	public void start(){
		animator.start();
	}

	public void stop(){
		animator.stop();
	}

	//TODO Core must implement GLEventListener
	@Override
	public void init(GLAutoDrawable drawable) {

		glGraphics.setCanvas(drawable);

		glGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		glGraphics.setColor(Color.BLACK);
		glGraphics.setFont(new Font("ARIAL", Font.PLAIN, 12));
		
		//glGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//TODO verify
		desktop.getLoadApplication3D().init(drawable);

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		desktop.getApplication3D().dispose(drawable);
	}

	@Override
	public void display(GLAutoDrawable drawable) {

		if(changeApp){

			DefaultLoadApplication3D load3D = desktop.getLoadApplication3D();

			load3D.init(drawable);
			load3D.load();

			anotherApplication3D.init(drawable);
			anotherApplication3D.load();

			loadExecutor = Executors.newSingleThreadExecutor();

			loadExecutor.execute(new Runnable() {

				@Override
				public void run() {

					applicationLoader.run();

					desktop.clearComponents();

					desktop.setApplication3D(anotherApplication3D);

				}
			});

			loadExecutor.shutdown();

			changeApp = false;

		}else{

			desktop.getApplication3D().reshape(drawable, panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
			desktop.getApplication3D().display(drawable);
			
			glGraphics.setCanvas(drawable);
			glGraphics.setColor(Color.BLACK);
			glGraphics.setFont(new Font("ARIAL", Font.PLAIN, 18));
			grafico.setGraphics(glGraphics);

			draw((Grafico)grafico);
		}

	}

	private Application3D anotherApplication3D;

	private boolean changeApp = false;

	@Override
	protected void changeApplication(){

		System.out.println("LuviaCore.changeApplication()");

		setMainApplication3D(desktop.getApplication3D().getReturnApplication3D());

	}

	public void setMainApplication3D(Application3D application3D) {
		//this.mainApplication = application3D;

		anotherApplication3D = application3D;
		anotherApplication3D.setSessionMap(desktop.getSessionMap());

		reload();

	}

	private void reload(){

		applicationLoader.setApplication(anotherApplication3D);
		applicationLoader.setLoadApplication(desktop.getLoadApplication3D());

		changeApp = true;
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		desktop.getApplication3D().reshape(drawable, x, y, width, height);
	}

	public void run() {

		gerencia();

	}

	public JPanel getPanel(){
		return panel;
	}
}
