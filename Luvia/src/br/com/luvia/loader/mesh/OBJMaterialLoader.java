package br.com.luvia.loader.mesh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

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
		
			line = line.trim();
			
			String[] splitLine = line.split(" ");
			
			String prefix = splitLine[0];
			
			if("newmtl".equalsIgnoreCase(prefix)) {
				
				if(mat!=null){
					materials.add(mat);
					mat = new Material();
				}
				
				mat.setName(splitLine[1]);
								
			}
			else if ("ka".equalsIgnoreCase(prefix)) {
                
				float x = Float.valueOf(splitLine[1]);
                float y = Float.valueOf(splitLine[2]);
                float z = Float.valueOf(splitLine[3]);
                
                mat.setKa(new Vector3f(x, y, z));
                
            }else if ("kd".equalsIgnoreCase(prefix)) {
                
            	float x = Float.valueOf(splitLine[1]);
                float y = Float.valueOf(splitLine[2]);
                float z = Float.valueOf(splitLine[3]);
                
                mat.setKd(new Vector3f(x, y, z));
                
            }else if ("ks".equalsIgnoreCase(prefix)) {
            	
                float x = Float.valueOf(splitLine[1]);
                float y = Float.valueOf(splitLine[2]);
                float z = Float.valueOf(splitLine[3]);
                mat.setKs(new Vector3f(x, y, z));
                
            }else if ("illum".equalsIgnoreCase(prefix)) {
                
            	int illum = Integer.valueOf(splitLine[1]);
                
                mat.setIllum(illum);
                
            }else if ("map_d".equalsIgnoreCase(prefix)) {
            	
            	mat.setMap_d(folder+splitLine[1]);
            	
            	System.out.println("Load map_d: "+folder+splitLine[1]);
            	
            	mat.setTexture_d(TextureLoader.getInstance().loadTexture(folder,splitLine[1]));
            	
            }else if ("map_kd".equalsIgnoreCase(prefix)) {
            	
            	mat.setMap_Kd(folder+splitLine[1]);
            	
            	System.out.println("Load map_kd: "+folder+splitLine[1]);
            	
            	mat.setTexture_Kd(TextureLoader.getInstance().loadTexture(folder,splitLine[1]));
            	
            }else if ("map_ka".equalsIgnoreCase(prefix)) {
            	
            	mat.setMap_Ka(folder+splitLine[1]);
            	
            	System.out.println("Load map_ka: "+folder+splitLine[1]);
            	
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
