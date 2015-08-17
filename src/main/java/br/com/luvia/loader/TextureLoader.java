package br.com.luvia.loader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;

import br.com.etyllica.loader.LoaderImpl;

import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class TextureLoader extends LoaderImpl {

	private static TextureLoader instance = null;

	private static GLContext context;

	private TextureLoader() {
		super();

		folder = "assets/images/";
	}

	public static TextureLoader getInstance() {

		if(instance == null) {
			context = GLContext.getCurrent();
			instance = new TextureLoader();
		}

		return instance;
	}

	public Texture loadTexture(String fullPath, String textureName, boolean flipVertical) {
		File file = new File(fullPath+textureName);

		try {
			//return TextureIO.newTexture(file, false);
			BufferedImage image = ImageIO.read(file);
			if(image != null && flipVertical) {
				ImageUtil.flipImageVertically(image);
			}
			return loadTexture(image);

		} catch (GLException e) {
			System.err.println("Error creating texture from: "+file);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("File not found: "+file);
			e.printStackTrace();
		}

		return null;
	}

	public Texture loadTexture(String fullPath, String textureName) {
		return loadTexture(fullPath, textureName, true);
	}

	public Texture loadTexture(String textureName) {
		return loadTexture(getPath()+folder, textureName);
	}
	
	public Texture loadTexture(String textureName, boolean flipVertical) {
		return loadTexture(getPath()+folder, textureName, flipVertical);
	}

	public Texture loadTexture(BufferedImage buffer) {
		setupContext();
		
		return AWTTextureIO.newTexture(GLProfile.getGL2GL3(), buffer, false);
	}

	private void setupContext() {
		if(context == null) {
			context = GLContext.getCurrent();
		}
		
		if(context!=null) {
			GLContext.getCurrent().makeCurrent();	
		}
	}

}