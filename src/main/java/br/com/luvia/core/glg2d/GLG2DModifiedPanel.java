package br.com.luvia.core.glg2d;

import javax.swing.JComponent;

import com.jogamp.opengl.GLAutoDrawable;


public class GLG2DModifiedPanel extends org.jogamp.glg2d.GLG2DPanel {

	public GLG2DModifiedPanel(JComponent drawableComponent) {
		super(drawableComponent);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 370275184911204974L;

	public GLAutoDrawable getCanvas() {
		return canvas;
	}
	
}
