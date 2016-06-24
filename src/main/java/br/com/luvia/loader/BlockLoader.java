package br.com.luvia.loader;

import java.io.IOException;

import br.com.etyllica.loader.LoaderImpl;
import br.com.luvia.graphics.Block;
import br.com.luvia.loader.block.json.BlockJsonLoader;

public class BlockLoader extends LoaderImpl {

	private static BlockLoader instance = null;

	private BlockLoader() {
		super();

		folder = "assets/models/blocks/";
	}

	public static BlockLoader getInstance() {

		if(instance==null) {
			instance = new BlockLoader();
		}

		return instance;
	}

	public Block loadBlock(String fileName) throws IOException {
		return loadBlock(fullPath(), fileName);
	}

	public Block loadBlock(String fullPath, String fileName) throws IOException {
		String filePath = fullPath+fileName;
		return new BlockJsonLoader().loadBlock(filePath);
	}

	public void saveBlock(Block block, String fileName) throws IOException {
		new BlockJsonLoader().saveBlock(block, fileName);       
	}

}
