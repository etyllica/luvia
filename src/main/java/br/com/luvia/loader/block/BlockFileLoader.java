package br.com.luvia.loader.block;

import java.io.IOException;

import br.com.luvia.graphics.Block;

public interface BlockFileLoader {
	public Block loadBlock(String path) throws IOException;
}