package it.marco.marco.utils.serializer;

import java.io.IOException;
import java.util.Base64;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@Configuration
public class ByteToBase64Serializer extends JsonSerializer<byte[]> {
	
	@Override
	public void serialize(byte[] value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {

		String contentType = new Tika().detect(value).split("/")[1];
		
		jsonGenerator.writeObject("data:image/" + contentType + ";base64," + Base64.getEncoder().encodeToString(value));
	}
}