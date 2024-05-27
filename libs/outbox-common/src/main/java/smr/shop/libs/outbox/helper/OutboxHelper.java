package smr.shop.libs.outbox.helper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import smr.shop.libs.common.messaging.BaseMessageModel;

public class OutboxHelper {

    public static <T extends BaseMessageModel> String convertToString(T obj)  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to json");
        }
    }

    public static <T  extends BaseMessageModel> T convertToObject(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert json to object");
        }
    }
}
