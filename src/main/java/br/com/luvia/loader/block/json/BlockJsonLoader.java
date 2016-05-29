package br.com.luvia.loader.block.json;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import br.com.luvia.graphics.Block;
import br.com.luvia.graphics.ModelInstance;
import br.com.luvia.loader.block.BlockFileLoader;
import br.com.luvia.loader.block.BlockWriter;
import br.com.luvia.loader.block.serialization.BlockJsonSerializer;
import br.com.luvia.loader.block.serialization.ModelInstanceJsonSerializer;

public class BlockJsonLoader implements BlockFileLoader, BlockWriter {
	
	public Block loadBlock(String path) throws IOException {
		Gson json = createGson();
		JsonReader jsonReader = new JsonReader(new FileReader(path));
		
		Block block = json.fromJson(jsonReader, Block.class);
		return block;
	}
	
	@Override
	public void saveBlock(Block block, String path) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	private static Gson createGson() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(Block.class, new BlockJsonSerializer());
	    gsonBuilder.registerTypeAdapter(ModelInstance.class, new ModelInstanceJsonSerializer());
	    gsonBuilder.setPrettyPrinting();
	    
	    return gsonBuilder.create();
	}

}
