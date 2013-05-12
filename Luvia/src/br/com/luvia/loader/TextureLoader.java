package br.com.luvia.loader;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GLContext;
import javax.media.opengl.GLException;

import br.com.etyllica.core.loader.Loader;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureLoader extends Loader{

	private static TextureLoader instance = null;
	
	private GLContext context; 
	
	private TextureLoader(){
		super();
	
		folder = "res/images/";
	}
	
	public static TextureLoader getInstance() {
		if(instance==null){
			instance = new TextureLoader();
		}

		return instance;
	}
	
	public Texture loadTexture(String fullPath, String textureName){
		
		if(GLContext.getCurrent()==null){
			
			System.err.println("Force Current");
			
			//context.makeCurrent();
		}
		
		File file = new File(fullPath+textureName);
		
		try {
			return TextureIO.newTexture(file, false);
		} catch (GLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Texture loadTexture(String textureName){
		
		return loadTexture(getPath()+folder, textureName);

	}

	public GLContext getContext() {
		return context;
	}

	public void setContext(GLContext context) {
		this.context = context;
	}
		
}
