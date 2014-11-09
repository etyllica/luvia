package br.com.luvia.loader.mesh;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import br.com.etyllica.core.loader.LoaderImpl;
import br.com.luvia.linear.Mesh;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class MeshLoader extends LoaderImpl {

	private static MeshLoader instance = null;

	private Map<String, Mesh> meshes = new HashMap<String, Mesh>();

	public static MeshLoader getInstance() {
		if(instance==null){
			instance = new MeshLoader();
		}

		return instance;
	}

	private MeshLoader() {
		super();

		folder = "assets/models/";
	}

	public Mesh loadModel(String path) {

		String diretorio = folder+path;

		URL dir = null;
		try {
			dir = new URL(url, diretorio);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		Mesh mesh = meshes.get(diretorio);
		
		if(mesh == null) {

			try {
				mesh = OBJLoader.loadModel(dir);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			meshes.put(diretorio, mesh);
		}
		
		return mesh;
	}

}
