package br.com.luvia.loader.block.serialization;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.com.abby.core.model.Model;
import br.com.luvia.graphics.ModelInstance;

public class ModelInstanceJsonSerializer implements JsonSerializer<ModelInstance>, JsonDeserializer<ModelInstance> {

	private static final String JSON_ID = "id";
	private static final String JSON_TRANSFORM = "transform";
	private static final String JSON_COLOR = "transform";
	
	@Override
	public JsonElement serialize(ModelInstance model, Type type, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		json.add(JSON_ID, new JsonPrimitive(model.getModel().getPath()));
		json.add(JSON_TRANSFORM, context.serialize(model.transform));
		json.add(JSON_COLOR, context.serialize(model.getColor()));
		
		return json;
	}

	@Override
	public ModelInstance deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		Model vbo = null;
		
		ModelInstance modelInstance = new ModelInstance(vbo);
		return modelInstance;
	}

}
