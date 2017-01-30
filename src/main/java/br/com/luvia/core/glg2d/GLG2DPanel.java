package br.com.luvia.core.glg2d;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLCapabilitiesImmutable;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;


public class GLG2DPanel extends org.jogamp.glg2d.GLG2DPanel {

	private static final long serialVersionUID = 370275184911204974L;

	public GLG2DPanel() {
		super(GL2Capabilities());
	}
	
	private static GLCapabilities GL2Capabilities() {
		GLCapabilities caps = new GLCapabilities(GLProfile.getGL2ES1());
	    caps.setRedBits(8);
	    caps.setGreenBits(8);
	    caps.setBlueBits(8);
	    caps.setAlphaBits(8);
	    caps.setDoubleBuffered(true);
	    caps.setHardwareAccelerated(true);
	    caps.setNumSamples(4);
	    caps.setBackgroundOpaque(false);
	    caps.setSampleBuffers(true);
	    return caps;
	}

	public GLAutoDrawable getCanvas() {
		return canvas;
	}
	
	@Override
	protected GLAutoDrawable createGLComponent(GLCapabilitiesImmutable capabilities, GLContext shareWith) {
		GLJPanel canvas = new GLJPanel(capabilities);
		canvas.setEnabled(false);
		return canvas;
	}
	
}
