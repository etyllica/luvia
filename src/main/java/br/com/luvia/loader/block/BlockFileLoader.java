package br.com.luvia.loader.block;

import java.io.File;
import java.io.IOException;

import br.com.luvia.graphics.Block;

public interface BlockFileLoader {
	public Block loadBlock(File file) throws IOException;
}
