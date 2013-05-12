package br.com.luvia.loader.mesh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import com.jogamp.opengl.util.texture.TextureIO;

import br.com.luvia.loader.TextureLoader;
import br.com.luvia.material.Material;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class OBJMaterialLoader {

	public static List<Material> loadMaterial(String folder, String filename) throws IOException{
		
		File f = new File(folder+filename);
		
		BufferedReader reader = new BufferedReader(new FileReader(f));
		
		List<Material> materials = new ArrayList<Material>();
		
		Material mat = new Material();

		String line;
		
		while ((line = reader.readLine()) != null) {
		
			//line = line.toLowerCase();
			line = line.trim();
			
			String[] splitLine = line.split(" ");
			
			if(line.startsWith("newmtl ")) {
				
				if(mat!=null){
					materials.add(mat);
					mat = new Material();
				}
				
				mat.setName(splitLine[1]);
								
			}
			else if (line.startsWith("ka ")) {
                float x = Float.valueOf(splitLine[1]);
                float y = Float.valueOf(splitLine[2]);
                float z = Float.valueOf(splitLine[3]);
                mat.setKa(new Vector3f(x, y, z));
            }else if (line.startsWith("kd ")) {
                float x = Float.valueOf(splitLine[1]);
                float y = Float.valueOf(splitLine[2]);
                float z = Float.valueOf(splitLine[3]);
                mat.setKd(new Vector3f(x, y, z));
            }else if (line.startsWith("ks ")) {
                float x = Float.valueOf(splitLine[1]);
                float y = Float.valueOf(splitLine[2]);
                float z = Float.valueOf(splitLine[3]);
                mat.setKs(new Vector3f(x, y, z));
            }else if (line.startsWith("illum ")) {
                int illum = Integer.valueOf(splitLine[1]);
                mat.setIllum(illum);
                
            }else if (line.startsWith("map_d ")) {
            	
            	mat.setMap_d(folder+splitLine[1]);
            	
            	System.out.println("Load: "+folder+splitLine[1]);
            	
            	mat.setTexture_d(TextureLoader.getInstance().loadTexture(folder,splitLine[1]));
            	
            }else if (line.startsWith("map_Kd ")) {
            	
            	mat.setMap_Kd(folder+splitLine[1]);
            	
            	System.out.println("Load: "+folder+splitLine[1]);
            	
            	mat.setTexture_Kd(TextureLoader.getInstance().loadTexture(folder,splitLine[1]));
            	
            }else if (line.startsWith("map_Ka ")) {
            	
            	mat.setMap_Ka(folder+splitLine[1]);
            	
            	System.out.println("Load: "+folder+splitLine[1]);
            	
            	mat.setTexture_Ka(TextureLoader.getInstance().loadTexture(folder,splitLine[1]));
            	
            }
			
			
		}

		reader.close();

		if(mat!=null){
			materials.add(mat);
		}
		
		return materials;
	}

}
