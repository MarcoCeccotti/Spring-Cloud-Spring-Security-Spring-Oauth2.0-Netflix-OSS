package it.marco.marco.utils.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class Base64ToByteDeserializer extends JsonDeserializer<byte[]> {
	
	@Override
	public byte[] deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		String image = jsonParser.getText();
		
		if(image == null || image.equals("")) {
			return null;
		}
		
		String base64 = jsonParser.getText().split(",")[1];

		return javax.xml.bind.DatatypeConverter.parseBase64Binary(base64);
	}
}