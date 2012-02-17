/**
 * 
 */
package nta.engine.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * @author jihoon
 *
 */
public class ClassNameDeserializer implements JsonDeserializer<Class> {

	@Override
	public Class deserialize(JsonElement json, Type type,
			JsonDeserializationContext ctx) throws JsonParseException {
		try {
			return Class.forName(json.getAsString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}