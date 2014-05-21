package br.com.luvia.material;

import org.lwjgl.util.vector.Vector3f;

import com.jogamp.opengl.util.texture.Texture;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class Material {
	
	private String name = "";
	
	//Ambient Color
	private Vector3f Ka;
	private String mapKa = "";
	private Texture textureKa = null;
	
	//Diffuse Color
	private Vector3f Kd;
	private String mapKd = "";
	private Texture textureKd = null;
	
	//Specular Color
	private Vector3f Ks;
	private String mapKs = "";
	private Texture textureKs = null;
	
	//Specular Highlight
	private float Ns = 0f;
	private String mapNs = "";
	private Texture textureNs = null;
	
	private float d;
	private String mapD = "";
	private Texture textureD = null;	
	
	private int illum = 0;
	
	
	public Material() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector3f getKa() {
		return Ka;
	}

	public void setKa(Vector3f ka) {
		Ka = ka;
	}

	public String getMapKa() {
		return mapKa;
	}

	public void setMapKa(String mapKa) {
		this.mapKa = mapKa;
	}

	public Vector3f getKd() {
		return Kd;
	}

	public void setKd(Vector3f kd) {
		Kd = kd;
	}

	public String getMapKd() {
		return mapKd;
	}

	public void setMapKd(String mapKd) {
		this.mapKd = mapKd;
	}

	public Vector3f getKs() {
		return Ks;
	}

	public void setKs(Vector3f ks) {
		Ks = ks;
	}

	public String getMapKs() {
		return mapKs;
	}

	public void setMapKs(String mapKs) {
		this.mapKs = mapKs;
	}

	public float getNs() {
		return Ns;
	}

	public void setNs(float ns) {
		Ns = ns;
	}

	public String getMapNs() {
		return mapNs;
	}

	public void setMapNs(String mapNs) {
		this.mapNs = mapNs;
	}

	public float getD() {
		return d;
	}

	public void setD(float d) {
		this.d = d;
	}

	public String getMapD() {
		return mapD;
	}

	public void setMapD(String mapD) {
		this.mapD = mapD;
	}

	public int getIllum() {
		return illum;
	}

	public void setIllum(int illum) {
		this.illum = illum;
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

	public Texture getTexture_Ks() {
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

	public Texture getTexture_d() {
		return textureD;
	}

	public void setTextureD(Texture textureD) {
		this.textureD = textureD;
	}
		
}
