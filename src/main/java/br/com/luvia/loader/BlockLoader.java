package br.com.luvia.loader;

import java.io.File;
import java.io.IOException;

import br.com.etyllica.loader.LoaderImpl;
import br.com.luvia.graphics.Block;
import br.com.luvia.loader.block.BlockJsonLoader;

public class BlockLoader extends LoaderImpl {

	private static BlockLoader instance = null;

	private BlockLoader() {
		super();

		folder = "assets/images/";
	}

	public static BlockLoader getInstance() {

		if(instance==null) {
			instance = new BlockLoader();
		}

		return instance;
	}

	public Block loadInfo(String fileName) {
		return loadInfo(getPath()+folder, fileName);
	}
	
	public Block loadInfo(String fullPath, String fileName) {

			File file = new File(fullPath+fileName);

			try {
				return new BlockJsonLoader().loadBlock(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return null;
	}

}
