package br.com.luvia.loader.block;

import java.io.IOException;

import br.com.luvia.graphics.Block;

public interface BlockWriter {
	public void saveBlock(Block block, String path) throws IOException;
}
