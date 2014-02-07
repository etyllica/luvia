package br.com.luvia.loader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.media.opengl.GLContext;
import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;

import br.com.etyllica.core.loader.LoaderImpl;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class TextureLoader extends LoaderImpl{

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
	
	public Texture loadTexture(BufferedImage buffer){
		
		return AWTTextureIO.newTexture(GLProfile.getGL2GL3(), buffer, false);

	}

	public GLContext getContext() {
		return context;
	}

	public void setContext(GLContext context) {
		this.context = context;
	}
		
}
