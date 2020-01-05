package it.marco.marco.utils.serializer;

import java.io.IOException;
import java.util.Date;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@Configuration
public class DateAndTimeSerializer extends JsonSerializer<Date> {
	
    @Override
    public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
    	
    	jsonGenerator.writeNumber(value.getTime());
    }
}