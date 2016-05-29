package br.com.luvia.loader.block.serialization;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Matrix4;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.com.luvia.graphics.Block;
import br.com.luvia.graphics.ModelInstance;

public class BlockJsonSerializer implements JsonSerializer<Block>, JsonDeserializer<Block> {

	private static final String BLOCK_VERSION = "1.0";
	
	private static final String JSON_VERSION = "v";
	
	private static final String JSON_ROWS = "rows";
	private static final String JSON_COLUMNS = "columns";
	private static final String JSON_HEIGHT = "height";
	private static final String JSON_OBJECTS = "objects";
	
	private static final String JSON_PATH = "path";
	private static final String JSON_TRANSFORM = "transform";
	
	@Override
	public JsonElement serialize(Block block, Type type, JsonSerializationContext context) {
		JsonObject element = new JsonObject();

		element.add(JSON_VERSION, new JsonPrimitive(BLOCK_VERSION));
		element.add(JSON_ROWS, new JsonPrimitive(block.getRows()));
		element.add(JSON_COLUMNS, new JsonPrimitive(block.getColumns()));
		element.add(JSON_HEIGHT, new JsonPrimitive(block.getHeight()));
		
		serializeObjects(block, context, element);
		
		return element;
	}

	private void serializeObjects(Block block, JsonSerializationContext context, JsonObject element) {
		Set<String> modelPaths = new HashSet<String>();
		
		JsonArray objects = new JsonArray();
		for (ModelInstance obj: block.getInstances()) {
			modelPaths.add(obj.getModel().getPath());
			objects.add(context.serialize(obj));
		}
		
		element.add(JSON_OBJECTS, objects);
	}
	
	@Override
	public Block deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonObject json = element.getAsJsonObject();
		
		Block block = new Block();

		int version = json.get(JSON_VERSION).getAsInt();
		int columns = json.get(JSON_COLUMNS).getAsInt();
    	int rows = json.get(JSON_ROWS).getAsInt();
    	int height = json.get(JSON_HEIGHT).getAsInt();
    	
    	block.setVersion(version);
    	block.setColumns(columns);
    	block.setRows(rows);
    	block.setHeight(height);
    	
    	JsonArray modelsArray = json.get(JSON_OBJECTS).getAsJsonArray();
    	deserializeModels(block, modelsArray);
    	
		return block;
	}

	private void deserializeModels(Block block, JsonArray modelsArray) {
		for (int i = 0; i < modelsArray.size(); i++) {
    		JsonObject obj = modelsArray.get(i).getAsJsonObject();
    		
    		String path = obj.get(JSON_PATH).getAsString();
    		
    		JsonArray array = obj.get(JSON_TRANSFORM).getAsJsonArray();
    		float[] transform = deserializeTransform(array);
    		
    		ModelInstance instance = new ModelInstance(path);
    		instance.transform.set(new Matrix4(transform));
    		
    		block.getInstances().add(instance);
    	}
	}
	
	private static float[] deserializeTransform(JsonArray transformArray) {
		    	
		float[] transform = new float[16];
		
		for (int i = 0; i < transformArray.size(); i++) {
			transform[i] = transformArray.get(i).getAsFloat();
		}
		
		return transform;
	}

}
