package it.marco.marco.utils.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateAndTimeDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext) throws IOException {
    	
    	Date date = new Date(paramJsonParser.getLongValue());
        
        return date;
    }
}