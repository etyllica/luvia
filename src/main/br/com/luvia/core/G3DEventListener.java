package br.com.luvia.core;

import br.com.luvia.core.video.Graphics3D;

public interface G3DEventListener {

	public void init(Graphics3D g);
	
	public void preDisplay(Graphics3D g);
	
	public void display(Graphics3D g);
	
	public void dispose(Graphics3D g);
	
	public void reshape(Graphics3D g, int x, int y, int width, int height);
	
}
