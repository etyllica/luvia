package br.com.luvia.core.glg2d;

import org.jogamp.glg2d.G2DDrawingHelper;

import com.jogamp.opengl.GLContext;


public class GLGraphics2D extends org.jogamp.glg2d.GLGraphics2D {

	@Override
	public void setCanvas(GLContext glContext) {
		this.glContext = glContext;
		
		for (G2DDrawingHelper helper : helpers) {
			helper.setG2D(this);
		}
	}
	
}
