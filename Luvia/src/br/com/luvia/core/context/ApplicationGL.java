package br.com.luvia.core.context;

import java.awt.Color;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import br.com.abby.linear.AimPoint;
import br.com.abby.linear.Point3D;
import br.com.etyllica.context.Application;
import br.com.luvia.core.G3DEventListener;
import br.com.luvia.core.video.Graphics3D;

public abstract class ApplicationGL extends Application implements G3DEventListener {
		
	protected double zoom = 1;
	
	protected ApplicationGL returnApplication3D = this;
	
	public ApplicationGL(int w, int h) {
		super(w,h);
		init();
	}
	
	public ApplicationGL(int x, int y, int width, int height) {
		super(x,y,width,height);
		init();
	}
	
	private void init() {
		
		nextApplication = this;
		
		clearBeforeDraw = false;
	}
	
	@Override
	public void timeUpdate(long now) {
		// TODO Auto-generated method stub
		
	}

	public void preDisplay(Graphics3D g) {
		
	}
		
	public abstract void display(Graphics3D g);
		
	@Override
	public void dispose(Graphics3D g) {
		// TODO Auto-generated method stub
	}
	
	public ApplicationGL getReturnApplication3D() {
		return returnApplication3D;
	}

	protected Point3D transformPosition(Graphics3D g, Point3D point) {
				
		double[] m = g.getModelView();
		
		double x = m[0]*point.getX()+m[4]*point.getY()+m[8]*point.getZ();//+m[12]*0;
		
		double y = m[1]*point.getX()+m[5]*point.getY()+m[9]*point.getZ();//+m[13]*0;
		
		double z = m[2]*point.getX()+m[6]*point.getY()+m[10]*point.getZ();//+m[14]*0;
		
		//double w = m[3]*point.getX()+m[7]*point.getY()+m[11]*point.getZ()+m[15]*0;
		
		return new Point3D(x, y, z);
	}
			
	public void setColor(GL2 gl, Color color) {
		float red = ((float)color.getRed()/255);
		float green = ((float)color.getGreen()/255);
		float blue = ((float)color.getBlue()/255);
				
		gl.glColor3f(red, green, blue);
	}
	
}
