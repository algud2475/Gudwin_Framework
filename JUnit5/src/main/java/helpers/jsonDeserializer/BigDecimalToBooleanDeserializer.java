package helpers.jsonDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalToBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        BigDecimal value = jsonParser.getDecimalValue();
        if (value.compareTo(new BigDecimal("0.0")) == 0) {
            return false;
        } else {
            return true;
        }
    }
}
