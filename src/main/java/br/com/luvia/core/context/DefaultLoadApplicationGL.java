package br.com.luvia.core.context;

import java.awt.Color;

import br.com.etyllica.core.context.load.LoadApplication;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.luvia.core.video.Graphics3D;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class DefaultLoadApplicationGL extends ApplicationGL implements LoadApplication {

	public DefaultLoadApplicationGL(int x, int y, int w, int h) {
		super(x,y,w,h);
				
		load();
	}

	protected String loadingInfo = "Loading...";
	
	protected String percent = "0%";

	protected int fill = 0;

	private int bxLimite = 400;
	private int byLimite = 30;

	private float bx = (w/2)-bxLimite/2;

	private float by = h/3;

	public void load(){

		by = h-100;
		
		loading = 100;
	}

	@Override
	public void draw(Graphic g) {

		g.setColor(Color.BLUE);
		g.fillRect(x,y,w,h);

		g.setColor(Color.BLACK);
		g.drawRect(bx,by,bxLimite,byLimite);
		g.setColor(Color.WHITE);
		g.drawRect(bx-1,by-1,bxLimite+2,byLimite+2);

		g.setColor(Color.WHITE);
		g.writeX(100, loadingInfo,true);
			
		//Desenha preenchimento da barra
		g.fillRect(bx+2, by+2, fill*4, byLimite-3);


		g.setColor(Color.WHITE);
		
		///System.out.println(percent);
		g.writeX(h-85, percent,true);

	}

	public void setText(String phrase, int load){
		
		this.loadingInfo = phrase;
		
		this.percent = Integer.toString(load)+"%";

		this.fill = load;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		return GUIEvent.NONE;
	}

	@Override
	public void init(Graphics3D g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void display(Graphics3D g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reshape(Graphics3D g, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onChangeLoad(float load) {
		this.loading = load;
	}

	@Override
	public void onChangeText(String loadingInfo) {
		this.loadingInfo = loadingInfo;
	}

}