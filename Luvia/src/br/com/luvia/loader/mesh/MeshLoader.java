package br.com.luvia.loader.mesh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.luvia.linear.Modelo3D;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class MeshLoader {

	private static MeshLoader instancia = null;
	private URL url;

	private MeshLoader(){
		super();
	}

	public static MeshLoader getInstance() {
		if(instancia==null){
			instancia = new MeshLoader();
		}

		return instancia;
	}

	public void setUrl(String s){
		try {
			url = new URL(s);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	} 

	public URL getUrl(){
		return url;
	}
	
	public File getFile(String path){
		
		return new File(url.toString().substring(5)+path);
	}
	
	//private String pasta = "http://www.etyllica.com.br/fox/modelos/";
	private String pasta = "res/models/";
	
	public Modelo3D loadModel(String caminho){
		
		String diretorio = pasta+caminho;
		
		URL dir = null;
		try {
			dir = new URL(url, diretorio);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		
		try {
			return OBJLoader.loadModel(dir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public String getPasta() {
		return pasta;
	}

	public void setPasta(String pasta) {
		this.pasta = pasta;
	}
	
}
