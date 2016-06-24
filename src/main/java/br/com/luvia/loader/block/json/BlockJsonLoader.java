package br.com.luvia.loader.block.json;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import br.com.etyllica.util.io.IOHelper;
import br.com.luvia.graphics.Block;
import br.com.luvia.graphics.ModelInstance;
import br.com.luvia.loader.BlockLoader;
import br.com.luvia.loader.block.BlockReaderLoader;
import br.com.luvia.loader.block.BlockWriter;
import br.com.luvia.loader.block.serialization.BlockJsonSerializer;
import br.com.luvia.loader.block.serialization.ModelInstanceJsonSerializer;

public class BlockJsonLoader implements BlockReaderLoader, BlockWriter {

	public Block loadBlock(String path) throws IOException {
		Gson json = createGson();
		JsonReader jsonReader = new JsonReader(new FileReader(path));

		Block block = json.fromJson(jsonReader, Block.class);
		return block;
	}

	@Override
	public void saveBlock(Block block, String filename) throws IOException {
		Gson gson = createGson();
		final String json = gson.toJson(block);           
		final String path = getPath(filename);

		IOHelper.write(path, json);
	}

	private static String getPath(String filename) {
		return BlockLoader.getInstance().fullPath()+filename;
	}

	private static Gson createGson() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Block.class, new BlockJsonSerializer());
		gsonBuilder.registerTypeAdapter(ModelInstance.class, new ModelInstanceJsonSerializer());
		gsonBuilder.setPrettyPrinting();

		return gsonBuilder.create();
	}

}
