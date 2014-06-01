package br.com.luvia.material;

import com.jogamp.opengl.util.texture.Texture;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class Material extends DiffuseMaterial {
	
	private String name = "";
	
	//Ambient Color
	private Texture textureKa = null;
	
	//Diffuse Color
	private Texture textureKd = null;
	
	//Specular Color
	private Texture textureKs = null;
	
	//Specular Highlight
	private Texture textureNs = null;
	
	private Texture textureD = null;	
	
	public Material() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Texture getTextureKa() {
		return textureKa;
	}

	public void setTextureKa(Texture textureKa) {
		this.textureKa = textureKa;
	}

	public Texture getTextureKd() {
		return textureKd;
	}

	public void setTextureKd(Texture textureKd) {
		this.textureKd = textureKd;
	}

	public Texture getTextureKs() {

		return textureKs;
	}

	public void setTextureKs(Texture textureKs) {
		this.textureKs = textureKs;
	}

	public Texture getTextureNs() {
		return textureNs;
	}

	public void setTexture_Ns(Texture textureNs) {
		this.textureNs = textureNs;
	}

	public Texture getTextureD() {

		return textureD;
	}

	public void setTextureD(Texture textureD) {
		this.textureD = textureD;
	}
		
}
