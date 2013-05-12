package br.com.luvia.core.video;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.jogamp.glg2d.GLGraphics2D;

import br.com.etyllica.core.video.Grafico;

public class Graphics3D extends Grafico{

	public Graphics3D(int width, int heigth){
		super(width,heigth);
	}

	public void setGraphics(Graphics2D graphics){		
		this.screen = graphics;
		this.screen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public void setGraphics(GLGraphics2D graphics){		
		this.screen = graphics;
		this.screen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
}
