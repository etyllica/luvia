package br.com.luvia.core;

import java.awt.Graphics2D;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import br.com.etyllica.core.application.Application;

public abstract class ApplicationGL extends Application implements GLEventListener{

	protected GLU glu;
	
	protected ApplicationGL returnApplication3D = this;
	
	public ApplicationGL(int x, int y, int width, int height){
		super(x,y,width,height);

		glu = new GLU(); // GL Utilities
	}
	
	public ApplicationGL(int width, int height){
		super(width,height);

		glu = new GLU(); // GL Utilities
	}

	public abstract void timeUpdate();

	public void preDisplay(Graphics2D g) {
		
	}
		
	@Override
	public void dispose(GLAutoDrawable drawable){
		// TODO Auto-generated method stub
	}
	
	public ApplicationGL getReturnApplication3D(){
		return returnApplication3D;
	}
	
	/*//Texture routines
	protected void enableTextureDefault(GL2 gl){

		gl.glShadeModel (GL2.GL_FLAT);

		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
		gl.glTexParameteri (GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri (GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);

		gl.glTexParameteri (GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl.glTexParameteri (GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);


		gl.glEnable (GL2.GL_DEPTH_TEST);
		gl.glDepthFunc (GL2.GL_LESS);

		gl.glEnable (GL2.GL_TEXTURE_2D);

		gl.glEnable (GL2.GL_CULL_FACE);
		gl.glCullFace (GL2.GL_BACK);		

	}*/
	

}
